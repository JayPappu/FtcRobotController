package org.firstinspires.ftc.teamcode;

import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.pedropathing.follower.Follower;
import com.pedropathing.util.Timer;


import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

import java.util.Arrays;

@Autonomous
public class SampleAutoCurvePathing extends OpMode {

    private Follower follower;
    private Timer pathtimer, opModeTimer;

    public enum PathState {
        //START POSITION_END POSITION
        // DRIVE > MOVEMENT STATE
        //SCORE > ATTEMPT TO SCORE THE ARTIFACT
        DRIVE_STARTPOS_SHOOT_POS,
        SHOOT_PRELOAD
    }

    PathState pathState;

    private final Pose startpose = new Pose(70.8224155578301, 70.5747185261003, Math.toRadians(138) );
    private final Pose shootpose = new Pose(94.45496417604914, 94.90, Math.toRadians(138));

    private PathChain driveStartPosShootPos;

    public void buildpaths() {
        //put in coordinates for starting pose > ending pose

        driveStartPosShootPos = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                Arrays.asList(
                                        startpose,
                                        new Pose(94, 70, 0),
                                        shootpose
                                )
                        )
                        //just replaced the normal start pose and shoot pose with same thing, except different coords, and added a new pose, which is the control point thingy
                )
                .setLinearHeadingInterpolation(startpose.getHeading(), shootpose.getHeading()) // <--- If using standard PathBuilder methods
                .build();

    }

    public void statePathUpdate() {
        switch(pathState) {
            case DRIVE_STARTPOS_SHOOT_POS:
                follower.followPath(driveStartPosShootPos, true);
                setPathState(PathState.SHOOT_PRELOAD); // reset the timer & make new state
                pathState = PathState.SHOOT_PRELOAD ;
                break;
            case SHOOT_PRELOAD:
                // TODO add logic to fluwheel shooter
                // check is follower done it's path?
                if (!follower.isBusy()) {
                    // TODO add logic to flywheel shooter
                    telemetry.addLine ( "Done Path 1");

                }
                break;
            default:
                telemetry.addLine( "No State Commanded");
                break;
        }
    }

    public void setPathState(PathState newState) {
        pathState = newState;
        pathtimer.resetTimer();
    }

    @Override
    public void init() {
        pathState = PathState.DRIVE_STARTPOS_SHOOT_POS;
        pathtimer = new Timer();
        opModeTimer = new Timer();
        follower = Constants.createFollower((hardwareMap));
        // TODO add in any other init mechanisms

        buildpaths();
        follower.setPose(startpose);
    }

    public void start() {
        opModeTimer.resetTimer();
        setPathState(pathState);
    }


    @Override
    public void loop(){
        follower.update();
        statePathUpdate();
        telemetry.addData("path state", pathState.toString());
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.addData("Path time", pathtimer.getElapsedTimeSeconds());






    }

}
