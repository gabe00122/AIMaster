package gabek.ai.neuralnet

import gabek.ai.neuralnet.input.config.Config
import gabek.ai.neuralnet.input.config.readConfig
import gabek.ai.neuralnet.net.Network
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.PrintWriter
import java.util.*


/**
 * @author Gabriel Keith
 * @date 8/27/2017
 */


fun main(args: Array<String>){
    if(args.isNotEmpty()){
        learn(readConfig(File(args[0])))
    } else {
        learn(readConfig(File("xor.yaml")))
    }
}

fun learn(config: Config){
    println("Running: ${config.title}")

    val outputFile = File("./output/grid.csv")
    val trainingLogFile = File("./output/learning.csv")

    val random = config.network.random

    val network = Network(config.network)
    val data = config.training.data

    val pattersByOutputs = data.size * data[0].y.size

    val outputLog = PrintWriter(BufferedWriter(FileWriter(trainingLogFile)))
    outputLog.println("epoch, error")

    for(i in 0 until config.training.maxReps){
        Collections.shuffle(data, random)

        for((train, target) in data){
            network.train(train, target)
        }

        if(i.rem(config.training.testInterval) == 0){
            var tsse = 0.0
            for((train, target) in data){
                tsse += network.test(train, target)
            }
            val rmse = Math.sqrt(tsse / pattersByOutputs)

            outputLog.println("$i, $rmse")

            if(rmse <= config.training.targetError){
                println("Done at $i")
                break
            }
        }
    }

    outputLog.close()

    val inputArray = Array(2, {0.0})

    //output
    PrintWriter(BufferedWriter(FileWriter(outputFile))).use { writer ->
        writer.println("x, y, output")

        val res = 25
        for(i in 0 until res){
            for(j in 0 until res){
                val y = i / res.toDouble()
                val x = j / res.toDouble()

                inputArray[0] = x
                inputArray[1] = y

                val output = network.process(inputArray)[0]

                writer.println("$x, $y, $output")
            }
        }
    }
}
