package org.firstinspires.ftc.teamcode;


import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
@TeleOp(name = "MoveWithGamePad")
public class MoveWithGamePad extends LinearOpMode{

    private DcMotor frontLeft;
    private DcMotor backLeft;
    private DcMotor frontRight;
    private DcMotor backRight;

    @Override
    public void runOpMode() {

        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");

        waitForStart();

        if (opModeIsActive()) {
            while (opModeIsActive()) {

                float vertical;
                float horizontal;
                float pivot;
                double frontRight;
                double backRight;
                double frontLeft;
                double backLeft;
                vertical = gamepad1.right_stick_y;
                horizontal = -gamepad1.right_stick_x;
                pivot = -gamepad1.left_stick_x;
                frontRight = 0.8 * (-pivot + (vertical - horizontal));
                backRight = 0.8 * (-pivot + vertical + horizontal);
                frontLeft = 0.8 * (pivot + vertical + horizontal);
                backLeft = 0.8 * (pivot + (vertical - horizontal));

            }


        }


    }
}

