package org.firstinspires.ftc.teamcode;


import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous (name= "AutoStrafeTest")
public class AutoStrafeTest extends LinearOpMode{
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backRight;
    private DcMotor backLeft;

    @Override

    public void runOpMode () {


        frontLeft  = hardwareMap.get(DcMotor.class, "frontLeft" );
        frontRight  = hardwareMap.get(DcMotor.class, "frontRight" );
        backLeft  = hardwareMap.get(DcMotor.class, "backLeft" );
        backRight  = hardwareMap.get(DcMotor.class, "backRight" );

        waitForStart();

        while(opModeIsActive()) {


            frontLeft.setPower(-.10);
            backLeft.setPower(-.10);
            frontRight.setPower(.10);


        }



    }
}
