package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.hardware.Servo
import kotlin.math.absoluteValue

class CachedServo(
    val servo: Servo,
) : Servo by servo {
    var cachedPosition = 0.0
        private set

    override fun setPosition(position: Double) {
        if ((position - cachedPosition).absoluteValue > 0.01) {
            cachedPosition = position
            servo.position = position
        }
    }

    override fun getPosition() = cachedPosition
}
