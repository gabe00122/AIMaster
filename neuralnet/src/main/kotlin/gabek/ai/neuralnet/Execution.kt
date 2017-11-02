package gabek.ai.neuralnet

import gabek.ai.neuralnet.data.DataPair
import gabek.ai.neuralnet.data.DataSet
import gabek.ai.neuralnet.network.Network
import gabek.ai.neuralnet.network.Training
import java.util.*
import kotlin.collections.ArrayList

class Execution(val network: Network, val training: Training, private val monitors: List<ExecutionMonitor>) {
    val trainingSet: DataSet<*, *>
    val testSet: DataSet<*, *>

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
        val pattersByOutputs = trainingSignals.size * trainingSignals[0].y.size

        monitors.forEach { it.onExecutionBegin(this) }

        for(i in 0 until training.maxReps){
            Collections.shuffle(trainingSignals, random)

            for((train, target) in trainingSignals){
                network.train(train, target)
            }

            if(i.rem(training.testInterval) == 0){
                var tsse = 0.0
                for((train, target) in trainingSignals){
                    tsse += network.test(train, target)
                }
                val rmse = Math.sqrt(tsse / pattersByOutputs)

                monitors.forEach { it.onTest(this, i, rmse) }

                if(rmse <= training.targetError){
                    println("Done at $i")
                    break
                }
            }
        }

        monitors.forEach { it.onCompletion(this) }
    }

}