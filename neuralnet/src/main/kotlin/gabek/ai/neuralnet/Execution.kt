package gabek.ai.neuralnet

import gabek.ai.neuralnet.data.DataPair
import gabek.ai.neuralnet.network.Network
import gabek.ai.neuralnet.network.Training
import java.util.*

class Execution(val network: Network, val training: Training, private val monitors: List<ExecutionMonitor>) {
    val trainingSet: List<DataPair>
    val testSet: List<DataPair>

    init {
        if(training.splitData) {
            val p = training.dataSet.split(training.trainingTestSplit)
            trainingSet = p.first
            testSet = p.second
        } else {
            trainingSet = training.dataSet.signals
            testSet = training.dataSet.signals
        }
    }

    fun execute(){
        val random = Random()
        val pattersByOutputs = trainingSet.size * trainingSet[0].y.size

        monitors.forEach { it.onExecutionBegin(this) }

        for(i in 0 until training.maxReps){
            Collections.shuffle(trainingSet, random)

            for((train, target) in trainingSet){
                network.train(train, target)
            }

            if(i.rem(training.testInterval) == 0){
                var tsse = 0.0
                for((train, target) in trainingSet){
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