package gabek.ai.kmeans

/**
 * @author Gabriel Keith
 * @date 10/2/2017
 */
class Frame {
    val groupX = ArrayList<Double>()
    val groupY = ArrayList<Double>()
    val pointGroups = ArrayList<Int>()

    val numGroups get() = groupX.size
}
