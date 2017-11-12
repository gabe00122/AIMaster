package gabek.ai.kmeans

import javafx.scene.canvas.Canvas
import javafx.scene.paint.Color
import javafx.scene.paint.Paint

/**
 * @author Gabriel Keith
 * @date 10/2/2017
 */
class DataRenderer(private val canvas: Canvas) {
    var pointSize = 7.0
    var groupPointSize = 10.0
    var viewportWidth = 1000000.0
    var viewportHeight = 1000000.0

    init {
        clear()
    }

    fun clear() {
        val gc = canvas.graphicsContext2D
        gc.fill = Color.WHITE
        gc.fillRect(0.0, 0.0, canvas.width, canvas.height)
    }

    fun renderPoints(points: PointField) {
        innerRenderPoints(points, null, null)
    }

    fun renderPoints(points: PointField, frame: Frame, pointStyle: List<Paint>?){
        innerRenderPoints(points, frame, pointStyle)
    }

    private fun innerRenderPoints(points: PointField, frame: Frame?, pointStyle: List<Paint>?){
        val gc = canvas.graphicsContext2D
        val width = canvas.width
        val height = canvas.height

        val numPoints = points.xAxis.size
        if (numPoints != points.yAxis.size) {
            throw IllegalArgumentException("Length of x and y axis must match")
        }

        repeat(numPoints) { point ->
            gc.fill = if(frame != null && pointStyle != null) pointStyle[frame.pointGroups[point]] else Color.BLACK
            gc.fillOval(points.xAxis[point] / viewportWidth * width - pointSize / 2.0,
                        points.yAxis[point] / viewportHeight * height - pointSize / 2.0,
                          pointSize, pointSize)
        }
    }

    fun renderGroups(frames: List<Frame>, range: IntRange, pointStyle: List<Paint>){
        val gc = canvas.graphicsContext2D
        val width = canvas.width
        val height = canvas.height

        val numGroups = frames[0].groupX.size
        if(numGroups != frames[0].groupY.size || numGroups != pointStyle.size){
            throw IllegalArgumentException("Problem with group sizes")
        }


        var currentFrame: Frame? = null
        var prevFrame: Frame?
        for(i in range) {
            prevFrame = currentFrame
            currentFrame = frames[i]

            if(prevFrame != null) {
                for (group in 0 until numGroups) {
                    gc.stroke = pointStyle[group]
                    gc.strokeLine(
                            prevFrame.groupX[group] / viewportWidth * width,
                            prevFrame.groupY[group] / viewportHeight * height,
                            currentFrame.groupX[group] / viewportWidth * width,
                            currentFrame.groupY[group] / viewportHeight * height
                    )
                }
            }

            if(i == range.last) {
                for (group in 0 until numGroups) {
                    gc.fill = pointStyle[group]
                    gc.fillRect(
                            currentFrame.groupX[group] / viewportWidth * width - groupPointSize / 2.0,
                            currentFrame.groupY[group] / viewportHeight * height - groupPointSize / 2.0,
                            groupPointSize, groupPointSize
                    )
                }
            }
        }
    }

}
