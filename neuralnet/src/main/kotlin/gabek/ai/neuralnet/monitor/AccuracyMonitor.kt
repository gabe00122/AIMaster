package gabek.ai.neuralnet.monitor

import gabek.ai.neuralnet.Execution
import gabek.ai.neuralnet.ExecutionMonitor
import java.io.*

class AccuracyMonitor: ExecutionMonitor(){
    val outputFile = File("./output/accuracy.txt")

    override fun onCompletion(execution: Execution) {
        val format = execution.testSet.outputFormat
        val network = execution.network

        //OutputStreamWriter(BufferedOutputStream(FileOutputStream(outputFile))).use { writer ->
            for(point in execution.testSet.signals){
                //val x = format.toFormat(execution.network.process(point.x))
                //val y = format.toFormat(point.y)

                val x = network.process(point.x)
                val y = point.y

                val correct = x == y
                println("$y -> $x")
            }

        //}
    }
}