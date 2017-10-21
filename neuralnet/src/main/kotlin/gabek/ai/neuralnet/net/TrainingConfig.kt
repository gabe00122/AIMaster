package gabek.ai.neuralnet.net

import java.util.ArrayList

/**
 * @author Gabriel Keith
 * @date 9/11/2017
 */
class TrainingConfig(
        val maxReps: Int,
        val testInterval: Int,
        val targetError: Double,
        val data: ArrayList<DataPair>
) {
    class DataPair(val X: Array<Double>, val y: Array<Double>){
        operator fun component1() = X
        operator fun component2() = y
    }
}