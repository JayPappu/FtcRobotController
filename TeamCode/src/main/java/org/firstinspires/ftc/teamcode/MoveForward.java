package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous (name= "moreForward")
public class MoveForward extends LinearOpMode {

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

        waitForStart();

        while(opModeIsActive()) {

            frontLeft.setPower(-.10);
            backLeft.setPower(-.10);
            frontRight.setPower(.10);
            backRight.setPower(.10);

            sleep(1000);

            frontLeft.setPower(0);
            backLeft.setPower(0);
            frontRight.setPower(0);
            backRight.setPower(0);



        }

    }


}