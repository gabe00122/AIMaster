package gabek.ai.neuralnet.monitor

import gabek.ai.neuralnet.Execution
import gabek.ai.neuralnet.ExecutionMonitor
import java.io.*

class AccuracyMonitor: ExecutionMonitor(){
    val outputFile = File("./output/accuracy.txt")

    override fun onCompletion(execution: Execution) {
        OutputStreamWriter(BufferedOutputStream(FileOutputStream(outputFile))).use { writer ->
            for(point in execution.testSet){
                val correct = compare(execution.network.process(point.x), point.y, 0.8)

            }
        }
    }


    private fun compare(a: List<Double>, b: List<Double>, threshold: Double): Boolean{
        repeat(a.size){ i ->
            if(Math.abs(a[i] - b[i]) > threshold){
                return false
            }
        }
        return true
    }
}