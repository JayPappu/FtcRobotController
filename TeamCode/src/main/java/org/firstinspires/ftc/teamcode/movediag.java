package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


@Autonomous (name= "movediag")
public class movediag extends LinearOpMode {

    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backRight;
    private DcMotor backLeft;

    @Override
    public void runOpMode() {

        frontLeft  = hardwareMap.get(DcMotor.class, "frontLeft" );
        frontRight  = hardwareMap.get(DcMotor.class, "frontRight" );
        backLeft  = hardwareMap.get(DcMotor.class, "backLeft" );
        backRight  = hardwareMap.get(DcMotor.class, "backRight" );
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        while(opModeIsActive()) {

            frontLeft.setPower(.10);
            backLeft.setPower(-.50);
            frontRight.setPower(-.50);
            backRight.setPower(.10);

            sleep(5000);



        }

    }


}