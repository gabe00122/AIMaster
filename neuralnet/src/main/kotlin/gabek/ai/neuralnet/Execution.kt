package gabek.ai.neuralnet

import gabek.ai.neuralnet.network.Network
import gabek.ai.neuralnet.network.Training
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.PrintWriter
import java.util.*

class Execution(val network: Network, val training: Training) {

    fun execute(){
        val outputFile = File("./output/grid.csv")
        val trainingLogFile = File("./output/learning.csv")

        val random = Random()

        val trainingSignals = training.trainingSet.signals
        val pattersByOutputs = trainingSignals.size * trainingSignals[0].y.size

        val outputLog = PrintWriter(BufferedWriter(FileWriter(trainingLogFile)))
        outputLog.println("epoch, error")

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

                outputLog.println("$i, $rmse")

                if(rmse <= training.targetError){
                    println("Done at $i")
                    break
                }
            }
        }

        outputLog.close()

        val inputList = arrayListOf(0.0, 0.0)

        //output
        PrintWriter(BufferedWriter(FileWriter(outputFile))).use { writer ->
            writer.println("x, y, output")

            val res = 25
            for(i in 0 until res){
                for(j in 0 until res){
                    val y = i / res.toDouble()
                    val x = j / res.toDouble()

                    inputList[0] = x
                    inputList[1] = y

                    val output = network.process(inputList)[0]

                    writer.println("$x, $y, $output")
                }
            }
        }
    }

}