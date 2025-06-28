package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.Servo
import com.qualcomm.robotcore.util.RobotLog

@TeleOp(name = "Bolts TeleOp", group = "Bolts")
class BoltsTeleOp : OpMode() {
    companion object {
        const val MIDDLE_POSITION = 0.755

        const val TAG = "TeleOp"
    }

    private lateinit var motorList: List<CachedMotor>
    private lateinit var steeringServo: CachedServo

    override fun init() {
        steeringServo = CachedServo(hardwareMap.servo["servo"])
        steeringServo.direction = Servo.Direction.REVERSE

        motorList =
            listOf(
                CachedMotor(hardwareMap.dcMotor["left"]),
                CachedMotor(hardwareMap.dcMotor["right"]),
            )

        motorList.first().direction = DcMotorSimple.Direction.REVERSE
    }

    private var lastPosition = MIDDLE_POSITION

    override fun loop() {
        val motorPower = (gamepad1.right_trigger - gamepad1.left_trigger).toDouble()

        for (motor in motorList) {
            motor.power = motorPower
        }

        val steeringModifier =
            if (gamepad1.cross) 0.5 else 1.0

        steeringServo.position =
            gamepad1.left_stick_x.toDouble() * (1 - MIDDLE_POSITION) * steeringModifier + MIDDLE_POSITION
        if (steeringServo.position != lastPosition) {
            RobotLog.dd(TAG, "Current position: ${steeringServo.position}")
        }
        lastPosition = steeringServo.position
    }
}
