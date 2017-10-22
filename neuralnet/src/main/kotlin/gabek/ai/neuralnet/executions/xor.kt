package gabek.ai.neuralnet.executions

import gabek.ai.neuralnet.data.BoolFormat
import gabek.ai.neuralnet.data.DataSet
import gabek.ai.neuralnet.execution

fun xor() = execution {
    network {
        learningRate = 0.4

        hidden = listOf(4)
    }

    training {
        maxReps = 100000
        testInterval = 50
        targetError = 0.05

        val format = BoolFormat()
        val xor = DataSet(format, format, listOf(
                Pair(listOf(false, false), listOf(false)),
                Pair(listOf(false, true), listOf(true)),
                Pair(listOf(true, false), listOf(true)),
                Pair(listOf(true, true), listOf(false))
        ))

        trainingSet = xor
        testSet = xor
    }
}