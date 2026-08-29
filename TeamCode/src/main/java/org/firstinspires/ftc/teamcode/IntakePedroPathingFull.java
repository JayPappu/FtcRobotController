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
import com.pedropathing.util.Timer;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@Autonomous(name = "IntakePedroPathingFull", group = "Autonomous")
@Configurable
public class IntakePedroPathingFull extends OpMode {
    private TelemetryManager panelsTelemetry;
    public Follower follower;
    private int pathState;
    private Paths paths;
    private Timer actionTimer;

    // motors
    private DcMotor intakeFront;

    private final Pose startPose = new Pose(58.3, 8.5, Math.toRadians(90));

    @Override
    public void init() {
        panelsTelemetry = PanelsTelemetry.INSTANCE.getTelemetry();
        follower = Constants.createFollower(hardwareMap);
        actionTimer = new Timer();

        follower.setStartingPose(startPose);

        paths = new Paths(follower);
        pathState = 0;

        intakeFront = hardwareMap.get(DcMotor.class, "intakeFront");
        intakeFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        panelsTelemetry.debug("Status", "Initialized");
        panelsTelemetry.update(telemetry);
    }

    @Override
    public void start() {
        setPathState(0);
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
        public PathChain intakeSecondBall;
        public PathChain moveToScoringPosition2;
        public PathChain thirdArtifactMove;
        public PathChain intakeThirdBall;
        public PathChain moveToScoringPosition3;
        public PathChain moveToGate;
        public PathChain openGate;

        public Paths(Follower follower) {
            moveToFirstPickupApproach = follower.pathBuilder()
                    .addPath(
                            new BezierCurve(
                                    new Pose(58.3, 8.5),
                                    new Pose(58.6, 47),
                                    new Pose(32.1, 46.8)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(180))
                    .build();

            intakeFirstBall = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(32.1, 46.8),
                                    new Pose(16.9, 47)
                            )
                    )
                    .setConstantHeadingInterpolation(Math.toRadians(180))
                    .build();

            moveToScoringPosition = follower.pathBuilder()
                    .addPath(
                            new BezierCurve(
                                    new Pose(16.9, 47),
                                    new Pose(58.6, 47),
                                    new Pose(70, 70)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
                    .build();

            scoreOrDepositFirstElement = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(70, 70),
                                    new Pose(35, 105)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(135))
                    .build();

            secondArtifactMove = follower.pathBuilder()
                    .addPath(
                            new BezierCurve(
                                    new Pose(35, 105),
                                    new Pose(70.6, 70),
                                    new Pose(33.3, 58.4)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(135), Math.toRadians(180))
                    .build();

            intakeSecondBall = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(33.3, 58.4),
                                    new Pose(14.2, 58.6)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
                    .build();

            moveToScoringPosition2 = follower.pathBuilder()
                    .addPath(
                            new BezierCurve(
                                    new Pose(14.2, 58.6),
                                    new Pose(70, 70),
                                    new Pose(34.5, 104.7)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(135))
                    .build();

            thirdArtifactMove = follower.pathBuilder()
                    .addPath(
                            new BezierCurve(
                                    new Pose(34.5, 104.7),
                                    new Pose(51.2, 90),
                                    new Pose(34.5, 82)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(135), Math.toRadians(180))
                    .build();

            intakeThirdBall = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(34.5, 82),
                                    new Pose(14, 82.2)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
                    .build();

            moveToScoringPosition3 = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(14, 82.2),
                                    new Pose(35, 104.1)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(135))
                    .build();

            moveToGate = follower.pathBuilder()
                    .addPath(
                            new BezierCurve(
                                    new Pose(35, 104.1),
                                    new Pose(36, 71),
                                    new Pose(14, 70)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(135), Math.toRadians(180))
                    .build();

            openGate = follower.pathBuilder()
                    .addPath(
                            new BezierCurve(
                                    new Pose(14, 70),
                                    new Pose(36, 71),
                                    new Pose(8.2, 70)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
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
                    intakeFront.setPower(1.0);
                    follower.setMaxPower(0.25);
                    follower.followPath(paths.intakeFirstBall);
                    setPathState(2);
                }
                break;

            case 2:
                if (!follower.isBusy()) {
                    intakeFront.setPower(0.0);
                    follower.setMaxPower(1.0);
                    follower.followPath(paths.moveToScoringPosition);
                    setPathState(3);
                }
                break;

            case 3:
                if (!follower.isBusy()) {
                    follower.followPath(paths.scoreOrDepositFirstElement);
                    setPathState(4);
                }
                break;

            case 4: // Wait until robot reaches the end of scoreOrDepositFirstElement
                if (!follower.isBusy()) {
                    intakeFront.setPower(-1.0); // Start outtaking ONLY after path reaches target
                    if (actionTimer.getElapsedTimeSeconds() > 0.75) { // Adjust score duration here
                        intakeFront.setPower(0.0);
                        follower.followPath(paths.secondArtifactMove);
                        setPathState(5);
                    }
                } else {
                    actionTimer.resetTimer();
                }
                break;

            case 5:
                if (!follower.isBusy()) {
                    intakeFront.setPower(1.0);
                    follower.setMaxPower(0.25);
                    follower.followPath(paths.intakeSecondBall);
                    setPathState(6);
                }
                break;

            case 6:
                if (!follower.isBusy()) {
                    intakeFront.setPower(0.0);
                    follower.setMaxPower(1.0);
                    follower.followPath(paths.moveToScoringPosition2);
                    setPathState(7);
                }
                break;

            case 7: // Wait until robot reaches scoring position 2
                if (!follower.isBusy()) {
                    intakeFront.setPower(-1.0);
                    if (actionTimer.getElapsedTimeSeconds() > 0.75) {
                        intakeFront.setPower(0.0);
                        follower.followPath(paths.thirdArtifactMove);
                        setPathState(8);
                    }
                } else {
                    actionTimer.resetTimer();
                }
                break;

            case 8:
                if (!follower.isBusy()) {
                    intakeFront.setPower(1.0);
                    follower.setMaxPower(0.25);
                    follower.followPath(paths.intakeThirdBall);
                    setPathState(9);
                }
                break;

            case 9:
                if (!follower.isBusy()) {
                    intakeFront.setPower(0.0);
                    follower.setMaxPower(1.0);
                    follower.followPath(paths.moveToScoringPosition3);
                    setPathState(10);
                }
                break;

            case 10: // Wait until robot reaches scoring position 3
                if (!follower.isBusy()) {
                    intakeFront.setPower(-1.0);
                    if (actionTimer.getElapsedTimeSeconds() > 0.75) {
                        intakeFront.setPower(0.0);
                        follower.followPath(paths.moveToGate);
                        setPathState(11);
                    }
                } else {
                    actionTimer.resetTimer();
                }
                break;

            case 11:
                if (!follower.isBusy()) {
                    follower.followPath(paths.openGate);
                    setPathState(12);
                }
                break;

            case 12:
                if (!follower.isBusy()) {
                    intakeFront.setPower(0.0);
                }
                break;
        }
    }

    public void setPathState(int state) {
        pathState = state;
        actionTimer.resetTimer();
    }
}