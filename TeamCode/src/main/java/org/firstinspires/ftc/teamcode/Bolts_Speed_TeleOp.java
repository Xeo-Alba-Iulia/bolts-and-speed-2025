package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Bolts Speed TeleOp", group = "Bolts")
public class Bolts_Speed_TeleOp extends OpMode {
    private Servo servo;

    @Override
    public void init() {
        servo = hardwareMap.servo.get("servo");
    }

    @Override
    public void loop() {
        if (gamepad1.cross)
            servo.setPosition(0.3);
        else if (gamepad1.circle)
            servo.setPosition(0.6);
    }
}
