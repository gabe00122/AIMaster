package gabek.ai.neuralnet.monitor

import gabek.ai.neuralnet.Execution
import gabek.ai.neuralnet.ExecutionMonitor
import java.io.*

class MetaMonitor(val reps: Int) : ExecutionMonitor() {
    val outputFile = File("output/meta.csv")

    val labels = ArrayList<String>()
    val iterations = ArrayList<Int>()
    val time = ArrayList<Double>()
    val scores = ArrayList<Double>()

    fun begin(){}

    fun end(){
        OutputStreamWriter(BufferedOutputStream(FileOutputStream(outputFile))).use { writer ->
            for(i in 0 until labels.size){
                writer.write("${labels[i]}\t${iterations[i] / reps}\t${time[i] / reps}\t${scores[i] / reps}\n")
            }
        }
    }

    fun next(label: String){
        labels.add(label)
        iterations.add(0)
        time.add(0.0)
        scores.add(0.0)
    }

    override fun onCompletion(execution: Execution) {
        iterations[iterations.size-1] += execution.iteration
        time[time.size-1] += execution.timeTaken
        scores[scores.size-1] += execution.error
    }
}
