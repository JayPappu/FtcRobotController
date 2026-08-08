package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@Autonomous(name = "PedroPathingAutonomous", group = "Autonomous")
@Configurable
public class PedroPathingAutonomous extends OpMode {
    private TelemetryManager panelsTelemetry;
    public Follower follower;
    private int pathState;
    private Paths paths;

    @Override
    public void init() {
        panelsTelemetry = PanelsTelemetry.INSTANCE.getTelemetry();
        follower = Constants.createFollower(hardwareMap);

        follower.setStartingPose(
                new Pose(24, 24, Math.toRadians(0))
        );

        paths = new Paths(follower);

        pathState = 0;

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

        pathState = autonomousPathUpdate();

        panelsTelemetry.debug("Path State", pathState);
        panelsTelemetry.debug("X", follower.getPose().getX());
        panelsTelemetry.debug("Y", follower.getPose().getY());
        panelsTelemetry.debug(
                "Heading",
                Math.toDegrees(follower.getPose().getHeading())
        );
        panelsTelemetry.debug("Busy", follower.isBusy());

        panelsTelemetry.update(telemetry);
    }

    public static class Paths {
        public PathChain MainChain;

        public Paths(Follower follower) {
            MainChain = follower.pathBuilder()
                    .addPath(
                            new BezierCurve(
                                    new Pose(24, 24),
                                    new Pose(72, 24),
                                    new Pose(72, 72)
                            )
                    )
                    .setLinearHeadingInterpolation(
                            Math.toRadians(0),
                            Math.toRadians(90)
                    )
                    .build();
        }
    }

    public int autonomousPathUpdate() {
        switch (pathState) {

            case 0:
                follower.followPath(paths.MainChain);
                return 1;

            case 1:
                if (!follower.isBusy()) {
                    return 2;
                }
                return 1;

            case 2:
                return 2;

            default:
                return 2;
        }
    }
}