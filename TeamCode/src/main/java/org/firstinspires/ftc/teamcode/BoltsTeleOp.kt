package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.Servo
import com.qualcomm.robotcore.util.RobotLog

@TeleOp(name = "Bolts TeleOp", group = "Bolts")
class BoltsTeleOp : OpMode() {
    companion object {
        const val MIDDLE_POSITION = 0.53

        const val TAG = "TeleOp"
    }

    private lateinit var motorListDown: List<CachedMotor>
    private lateinit var motorListUp: List<CachedMotor>

    private lateinit var motorList: List<CachedMotor>
    private lateinit var steeringServo: CachedServo
    
    private lateinit var leftDown: CachedMotor
    private lateinit var leftUp: CachedMotor
    private lateinit var rightDown: CachedMotor
    private lateinit var rightUp: CachedMotor

    override fun init() {
        steeringServo = CachedServo(hardwareMap.servo["servo"])
        steeringServo.direction = Servo.Direction.REVERSE
        
        leftDown = CachedMotor(hardwareMap.dcMotor["left down"])
        rightDown = CachedMotor(hardwareMap.dcMotor["right down"])
        leftUp = CachedMotor(hardwareMap.dcMotor["left up"])
        rightUp = CachedMotor(hardwareMap.dcMotor["right up"])

        motorListDown =
            listOf(
                leftDown,
                rightDown,
            )
        motorListUp =
            listOf(
                leftUp,
                rightUp,
            )
        motorList =
            listOf(
                leftDown,
                rightDown,
                leftUp,
                rightUp,
            )
        motorList.forEach { it.direction = DcMotorSimple.Direction.FORWARD }

    }

    private var lastPosition = MIDDLE_POSITION

    override fun loop() {

        val motorList = when {
            gamepad1.right_bumper -> motorListDown
            gamepad1.left_bumper -> motorListUp
            else -> this.motorList
        }

        val motorPower = (gamepad1.right_trigger - gamepad1.left_trigger).toDouble()

        motorList.forEach { it.power = motorPower }

        if (!gamepad1.triangle) {

            motorList.forEach { it.direction = DcMotorSimple.Direction.FORWARD }

        }
        else {

                leftDown.direction = DcMotorSimple.Direction.FORWARD
                rightDown.direction = DcMotorSimple.Direction.REVERSE
                leftUp.direction = DcMotorSimple.Direction.REVERSE
                rightUp.direction = DcMotorSimple.Direction.FORWARD

        }

        val steeringModifier =
            if (gamepad1.cross) 0.3 else 1.2

        steeringServo.position =
            gamepad1.left_stick_x.toDouble() * (1 - MIDDLE_POSITION) * steeringModifier + MIDDLE_POSITION
        if (steeringServo.position != lastPosition) {
            RobotLog.dd(TAG, "Current position: ${steeringServo.position}")
        }
        lastPosition = steeringServo.position
    }
}
