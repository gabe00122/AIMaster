package gabek.ai.kmeans

import java.util.*
import kotlin.collections.ArrayList


fun startFrame(random: Random, points: PointField, numGroups: Int): Frame{
    val frame = Frame()
    val numPoints = points.numPoints

    frame.groupX.ensureCapacity(numGroups)
    frame.groupY.ensureCapacity(numGroups)
    frame.pointGroups.ensureCapacity(numPoints)

    repeat(numGroups){
        val point = random.nextInt(numPoints)

        frame.groupX.add(points.xAxis[point])
        frame.groupY.add(points.yAxis[point])
    }

    selectGroups(points, frame)

    return frame
}

fun uniformRandom(random: Random, numPoints: Int,
                  minX: Double = 0.0, minY: Double = 0.0,
                  maxX: Double = 1.0, maxY: Double = 1.0): PointField {
    val pointField = PointField()

    val diffX = maxX - minX
    val diffY = maxY - minY

    repeat(numPoints) {
        pointField.xAxis.add(random.nextDouble() * diffX + minX)
        pointField.yAxis.add(random.nextDouble() * diffY + minY)
    }

    return pointField
}

fun calcNextFrame(points: PointField, prevFrame: Frame): Frame {
    val frame = Frame()

    updateMeans(points, prevFrame, frame)
    selectGroups(points, frame)

    return frame
}

fun runKmeans(points: PointField, startFrame: Frame): List<Frame> {
    val out = ArrayList<Frame>()

    out.add(startFrame)

    do{
        out.add(calcNextFrame(points, out.last()))
    } while(totalChange(out[out.size - 2], out[out.size - 1]) != 0.0 && out.size < 50)

    return out
}

fun totalChange(frameA: Frame, frameB: Frame): Double{
    val numGroups = frameA.numGroups

    var diff = 0.0
    for(group in 0 until numGroups){
        diff += frameA.groupX[group] - frameB.groupX[group] + frameA.groupY[group] - frameB.groupY[group]
    }

    return diff
}

private fun selectGroups(points: PointField, frame: Frame){
    val numPoints = points.numPoints
    val numGroups = frame.numGroups

    frame.pointGroups.clear()
    frame.pointGroups.ensureCapacity(numPoints)
    repeat(numPoints) { point ->
        var smallestGroup = 0
        var smallestDistance = 0.0
        repeat(numGroups){ group ->
            val xDiff = (frame.groupX[group] - points.xAxis[point])
            val yDiff = (frame.groupY[group] - points.yAxis[point])

            val distance = xDiff * xDiff + yDiff * yDiff
            if(smallestDistance > distance || group == 0){
                smallestGroup = group
                smallestDistance = distance
            }
        }

        frame.pointGroups.add(smallestGroup)
    }
}

private fun updateMeans(points: PointField, fromFrame: Frame, toFrame: Frame){
    val numGroups = fromFrame.numGroups
    val numPoints = points.numPoints

    val totX = Array(numGroups, {0.0})
    val totY = Array(numGroups, {0.0})
    val groupCount = Array(numGroups, {0})

    repeat(numPoints){ point ->
        val group = fromFrame.pointGroups[point]
        totX[group] += points.xAxis[point]
        totY[group] += points.yAxis[point]
        groupCount[group]++
    }

    toFrame.groupX.clear()
    toFrame.groupY.clear()
    toFrame.groupX.ensureCapacity(numGroups)
    toFrame.groupY.ensureCapacity(numGroups)
    repeat(numGroups){ group ->
        toFrame.groupX.add(totX[group] / groupCount[group])
        toFrame.groupY.add(totY[group] / groupCount[group])
    }
}