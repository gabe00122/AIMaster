package gabek.ai.neuralnet.monitor

import gabek.ai.neuralnet.Execution
import gabek.ai.neuralnet.ExecutionMonitor
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.io.PrintWriter

class LearningMonitor(val console: Boolean = false) : ExecutionMonitor(){
    val outputFile = File("./output/learning.csv")

    private var printer: PrintWriter? = null

    override fun onExecutionBegin(execution: Execution) {
        printer = PrintWriter(OutputStreamWriter(FileOutputStream(outputFile)))
        printer?.println("epoc, error")
    }

    override fun onTest(execution: Execution, epoc: Int, rmse: Double) {
        printer?.println("$epoc, $rmse")

        val testSet = execution.testSet.signals
        val format = execution.testSet.outputFormat
        val score = testSet.count { format.toFormat(execution.network.process(it.x)) != it.formatY }

        if(console) { println("$epoc, $rmse, ${testSet.size - score}/${testSet.size}") }
    }

    override fun onCompletion(execution: Execution) {
        printer?.flush()
        printer?.close()
        printer = null
    }
}