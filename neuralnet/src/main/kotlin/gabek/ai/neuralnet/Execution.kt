package gabek.ai.neuralnet

import gabek.ai.neuralnet.data.DataPoint
import gabek.ai.neuralnet.data.DataSet
import gabek.ai.neuralnet.network.Network
import gabek.ai.neuralnet.network.Training
import java.util.*
import kotlin.collections.ArrayList

class Execution(val network: Network, val training: Training, private val monitors: List<ExecutionMonitor>) {
    val trainingSet: DataSet<*, *>
    val testSet: DataSet<*, *>

    var iteration: Int = 0
        private set
    var error: Double = 0.0
        private set
    var timeTaken: Double = 0.0
        private set

    init {
        if(training.splitData >= 0) {
            val p = training.dataSet.split(training.splitData)
            trainingSet = p.first
            testSet = p.second
        } else {
            trainingSet = training.dataSet
            testSet = training.dataSet
        }
    }

    fun execute(){
        val random = Random()
        val trainingSignals = ArrayList(trainingSet.signals)
        val testSignals = testSet.signals

        monitors.forEach { it.onExecutionBegin(this) }

        val startTime = System.currentTimeMillis()

        for(i in 0 until training.maxReps){
            Collections.shuffle(trainingSignals, random)

            for((train, target) in trainingSignals){
                network.train(train, target)
            }

            if(i.rem(training.testInterval) == 0){
                val rmse = test(network, trainingSignals)
                iteration = i

                monitors.forEach { it.onTest(this, i, rmse) }

                if(rmse <= training.targetError){
                    break
                }
            }
        }

        timeTaken = (System.currentTimeMillis() - startTime) / 1000.0

        error = test(network, testSignals)
        //println("Done at $iteration - $timeTaken, with error of: $rmse")

        monitors.forEach { it.onCompletion(this) }
    }


    fun test(network: Network, testSignals: List<DataPoint>): Double{
        val pattersByOutputs = testSignals.size / testSignals[0].y.size

        var tsse = 0.0
        for((train, target) in testSignals){
            tsse += network.test(train, target)
        }
        return Math.sqrt(tsse / pattersByOutputs)
    }
}