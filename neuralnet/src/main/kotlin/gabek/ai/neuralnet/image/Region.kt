package gabek.ai.neuralnet.image

import java.awt.Color
import java.awt.image.BufferedImage

class Region(
        val image: BufferedImage,
        val x: Int,
        val y: Int,
        val width: Int,
        val height: Int){

    fun getColor(x: Int, y: Int): Color{
        return Color(image.getRGB(this.x + x, this.y + y))
    }

    fun getColorSum(x: Int, y: Int): Double{
        val color = getColor(x, y)

        return (color.red + color.green + color.blue) / 765.0
    }
}