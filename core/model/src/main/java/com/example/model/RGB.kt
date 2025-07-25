package com.example.model

import androidx.compose.ui.graphics.Color

data class RGB(val red: Int, val green: Int, val blue: Int) {
    override fun toString(): String {
        return "RGB ($red, $green, $blue)"
    }
}

data class HSV(val hue: Int, val saturation: Int, val value: Int) {
    override fun toString(): String {
        return "HSV ($hue, $saturation%, $value%)"
    }
}

fun Color.toRgb(): RGB {
    val red = (this.red * 255).toInt()
    val green = (this.green * 255).toInt()
    val blue = (this.blue * 255).toInt()
    return RGB(red, green, blue)
}

fun Color.toHsv(): HSV {
    val red = this.red
    val green = this.green
    val blue = this.blue

    val max = Math.max(red, Math.max(green, blue))
    val min = Math.min(red, Math.min(green, blue))
    val delta = max - min

    val hue = when {
        delta == 0f -> 0f
        max == red -> 60f * (((green - blue) / delta) % 6f)
        max == green -> 60f * (((blue - red) / delta) + 2f)
        else -> 60f * (((red - green) / delta) + 4f)
    }.let { if (it < 0) it + 360f else it }

    val saturation = if (max == 0f) 0f else (delta / max)

    return HSV(hue.toInt(), (saturation * 100).toInt(), (max * 100).toInt())
}
