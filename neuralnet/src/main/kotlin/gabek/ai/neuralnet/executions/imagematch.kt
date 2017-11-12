package gabek.ai.neuralnet.executions

import gabek.ai.neuralnet.data.CategoryFormat
import gabek.ai.neuralnet.data.DataSet
import gabek.ai.neuralnet.execution
import gabek.ai.neuralnet.image.ImageFormat
import gabek.ai.neuralnet.image.Region
import gabek.ai.neuralnet.image.loadImage
import gabek.ai.neuralnet.monitor.AccuracyMonitor
import gabek.ai.neuralnet.monitor.LearningMonitor

fun imagematch() = execution {

    network {
        learningRate = 0.2
        hidden = listOf(15)
    }

    training {
        maxReps = 20000
        testInterval = 75
        targetError = 0.4
        splitData = 0.8

        val image = loadImage("num25x25.png")
        val data = ArrayList<Pair<Region, String>>()
        val categories = listOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9")
        //val categories = listOf("+", "-", "\\", "/", "+")
        val epocs = 10
        val width = 25
        val height = 25

        repeat(epocs){ i ->
            repeat(categories.size){ j ->
                data.add(Pair(Region(image,j * width, i * height, width, height), categories[j]))
            }
        }

        dataSet = DataSet(ImageFormat(), CategoryFormat(), data)
    }

    monitors.add(LearningMonitor(true))
    monitors.add(AccuracyMonitor())
}