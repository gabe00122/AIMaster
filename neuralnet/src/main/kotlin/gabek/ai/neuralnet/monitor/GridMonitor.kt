package gabek.ai.neuralnet.monitor

import gabek.ai.neuralnet.Execution
import gabek.ai.neuralnet.ExecutionBuilder
import gabek.ai.neuralnet.ExecutionMonitor
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.PrintWriter

class GridMonitor() : ExecutionMonitor() {
    val outputFile = File("output/grid.csv")

    override fun onCompletion(execution: Execution) {
        val inputList = arrayListOf(0.0, 0.0)

        //output
        PrintWriter(BufferedWriter(FileWriter(outputFile))).use { writer ->
            writer.println("x, y, output")

            val res = 25
            for (i in 0 until res) {
                for (j in 0 until res) {
                    val y = i / res.toDouble()
                    val x = j / res.toDouble()
                    inputList[0] = x
                    inputList[1] = y

                    val output = execution.network.process(inputList)[0]

                    writer.println("$x, $y, $output")
                }
            }
        }
    }
}
