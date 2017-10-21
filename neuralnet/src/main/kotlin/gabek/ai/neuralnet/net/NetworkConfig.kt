package gabek.ai.neuralnet.net

import java.util.*
import kotlin.collections.ArrayList

/**
 * @author Gabriel Keith
 * @date 8/29/2017
 */
class NetworkConfig (
    val inputSize: Int,
    val hiddenSizes: ArrayList<Int>,
    val outputSize: Int,
    val learningRate: Double,
    val bias: Double = 1.0,
    val random: Random = Random()
)