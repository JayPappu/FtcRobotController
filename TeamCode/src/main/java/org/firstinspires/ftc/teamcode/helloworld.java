package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp //makes it so that it shows up on the driverstation either teleop or auto
public class helloworld extends OpMode {
    @Override // means that we are replacng the init into our own
    public void init() {
        telemetry.addData("hello", "world");
    }

    @Override
    public void loop() {
        
    }
}
