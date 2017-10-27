package gabek.ai.neuralnet.monitor

import gabek.ai.neuralnet.Execution
import gabek.ai.neuralnet.ExecutionMonitor
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.io.PrintWriter

class LearningMonitor : ExecutionMonitor(){
    val outputFile = File("./output/learning.csv")

    private var printer: PrintWriter? = null

    override fun onExecutionBegin(execution: Execution) {
        printer = PrintWriter(OutputStreamWriter(FileOutputStream(outputFile)))
        printer?.println("epoc, error")
    }

    override fun onTest(execution: Execution, epoc: Int, rmse: Double) {
        printer?.println("$epoc, $rmse")
    }

    override fun onCompletion(execution: Execution) {
        printer?.flush()
        printer?.close()
        printer = null
    }
}