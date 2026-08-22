package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@Autonomous(name = "IntakePedropathing", group = "Autonomous")
@Configurable
public class IntakePedropathing extends OpMode {
    private TelemetryManager panelsTelemetry;
    public Follower follower;
    private int pathState;
    private Paths paths;

    // motors
    private DcMotor frontIntake;

    private final Pose startPose = new Pose(58.3, 8.5, Math.toRadians(90));

    @Override
    public void init() {
        panelsTelemetry = PanelsTelemetry.INSTANCE.getTelemetry();
        follower = Constants.createFollower(hardwareMap);

        follower.setStartingPose(startPose);

        paths = new Paths(follower);
        pathState = 0;

        frontIntake = hardwareMap.get(DcMotor.class, "frontIntake");
        frontIntake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        panelsTelemetry.debug("Status", "Initialized");
        panelsTelemetry.update(telemetry);
    }

    @Override
    public void start() {
        pathState = 0;
    }

    @Override
    public void loop() {
        follower.update();

        autonomousPathUpdate();

        panelsTelemetry.debug("Path State", pathState);
        panelsTelemetry.debug("X", follower.getPose().getX());
        panelsTelemetry.debug("Y", follower.getPose().getY());
        panelsTelemetry.debug("Heading", Math.toDegrees(follower.getPose().getHeading()));
        panelsTelemetry.debug("Busy", follower.isBusy());

        panelsTelemetry.update(telemetry);
    }

    public static class Paths {
        public PathChain moveToFirstPickupApproach;
        public PathChain intakeFirstBall;
        public PathChain moveToScoringPosition;
        public PathChain scoreOrDepositFirstElement;
        public PathChain secondArtifactMove;

        public Paths(Follower follower) {
            // Curves from start position toward the first ball/intake alignment position
            moveToFirstPickupApproach = follower.pathBuilder()
                    .addPath(
                            new BezierCurve(
                                    new Pose(58.3, 8.5),
                                    new Pose(58.9, 35.4),
                                    new Pose(37.8, 35.2)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(180))
                    .build();

            // Drives straight west slowly while actively running the intake
            intakeFirstBall = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(37.8, 35.2),
                                    new Pose(18.0, 35.2)
                            )
                    )
                    .setConstantHeadingInterpolation(Math.toRadians(180))
                    .build();

            // Travels diagonally across the field toward the scoring/deposit zone approach
            moveToScoringPosition = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(18.0, 35.2),
                                    new Pose(59.6, 81.0)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
                    .build();

            // Curves toward the scoring location and aligns for outtaking/scoring
            scoreOrDepositFirstElement = follower.pathBuilder()
                    .addPath(
                            new BezierCurve(
                                    new Pose(59.6, 81.0),
                                    new Pose(51.0, 107.4),
                                    new Pose(35.5, 106.5)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(135))
                    .build();

            // Navigates away from the deposit area for the next game element/artifact movement
            secondArtifactMove = follower.pathBuilder()
                    .addPath(
                            new BezierCurve(
                                    new Pose(35.5, 106.5),
                                    new Pose(65.1, 79.5),
                                    new Pose(39.8, 59)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(135), Math.toRadians(180))
                    .build();
        }
    }

    public void autonomousPathUpdate() {
        switch (pathState) {
            case 0:
                follower.followPath(paths.moveToFirstPickupApproach);
                setPathState(1);
                break;

            case 1:
                if (!follower.isBusy()) {
                    frontIntake.setPower(1.0);
                    follower.setMaxPower(0.25);
                    follower.followPath(paths.intakeFirstBall);
                    setPathState(2);
                }
                break;

            case 2:
                if (!follower.isBusy()) {
                    frontIntake.setPower(0.0);
                    follower.setMaxPower(1.0);
                    follower.followPath(paths.moveToScoringPosition);
                    setPathState(3);
                }
                break;

            case 3:
                if (!follower.isBusy()) {
                    follower.followPath(paths.scoreOrDepositFirstElement);
                    frontIntake.setPower(-1.0);
                    setPathState(4);
                }
                break;

            case 4:
                if (!follower.isBusy()) {
                    frontIntake.setPower(0.0);
                    follower.followPath(paths.secondArtifactMove);
                    setPathState(5);
                }
                break;

            case 5:
                if (!follower.isBusy()) {
                    setPathState(6);
                }
                break;

            case 6:
                break;
        }
    }

    public void setPathState(int state) {
        pathState = state;
    }
}