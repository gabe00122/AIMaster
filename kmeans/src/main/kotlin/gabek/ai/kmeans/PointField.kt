package gabek.ai.kmeans

/**
 * @author Gabriel Keith
 * @date 10/2/2017
 */
class PointField {
    val xAxis = ArrayList<Double>()
    val yAxis = ArrayList<Double>()

    val numPoints: Int get() = xAxis.size
}
