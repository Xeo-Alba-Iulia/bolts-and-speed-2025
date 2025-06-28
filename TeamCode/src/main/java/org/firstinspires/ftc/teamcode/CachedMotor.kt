package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.hardware.DcMotor
import kotlin.math.absoluteValue

class CachedMotor(
    val motor: DcMotor,
) : DcMotor by motor {
    var cachedPower = 0.0
        private set

    override fun setPower(power: Double) {
        if ((power - cachedPower).absoluteValue > 0.01 || (power == 0.0 && cachedPower != 0.0)) {
            cachedPower = power
            motor.power = power
        }
    }

    override fun getPower() = cachedPower
}
