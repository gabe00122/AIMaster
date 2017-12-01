package gabek.ai.final

import javafx.scene.canvas.Canvas
import javafx.scene.paint.Color

fun drawTo(canvas: Canvas, data: Array<Array<Double>>){
    val g2d = canvas.graphicsContext2D
    val scaleX = canvas.width / data[0].size
    val scaleY = canvas.height / data.size

    for(j in 0 until data.size){
        for(i in 0 until data[j].size){
            val x = scaleX * i
            val y = scaleY * j

            g2d.fill = Color.hsb(data[j][i] * 180.0, 0.8, 0.8)
            g2d.fillRect(x, y, x + scaleX, y + scaleY)
        }
    }

}