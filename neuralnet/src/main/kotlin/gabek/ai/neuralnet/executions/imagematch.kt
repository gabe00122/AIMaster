package gabek.ai.neuralnet.executions

import gabek.ai.neuralnet.data.CategoryFormat
import gabek.ai.neuralnet.data.DataSet
import gabek.ai.neuralnet.execution
import gabek.ai.neuralnet.image.ImageFormat
import gabek.ai.neuralnet.image.Region
import gabek.ai.neuralnet.monitor.LearningMonitor
import java.io.File
import javax.imageio.ImageIO

fun imagematch() = execution {

    network {
        learningRate = 0.4
        hidden = listOf(10, 10)
    }

    training {
        maxReps = 10000
        testInterval = 50
        targetError = 0.05
        splitData = false
        trainingTestSplit = 0.8

        val image = ImageIO.read(File("nn5x5.png"))
        val data = ArrayList<Pair<Region, String>>()
        val categories = listOf("+", "-", "\\", "/", "x")
        val epocs = 10; val width = 5; val height = 5

        repeat(epocs){ i -> repeat(categories.size){ j ->
            data.add(Pair(Region(image, j * width, i * height, width, height), categories[j]))
        }}

        dataSet = DataSet(ImageFormat(), CategoryFormat(), data)
    }

    monitors.add(LearningMonitor())
}