package gabek.ai.neuralnet.executions

import gabek.ai.neuralnet.data.BoolFormat
import gabek.ai.neuralnet.data.DataSet
import gabek.ai.neuralnet.execution
import gabek.ai.neuralnet.monitor.GridMonitor
import gabek.ai.neuralnet.monitor.LearningMonitor

fun xor() = execution {
    network {
        learningRate = 0.4

        hidden = listOf(2, 2)
    }

    training {
        maxReps = 100000
        testInterval = 50
        targetError = 0.05
        splitData = false

        dataSet = DataSet(BoolFormat(), BoolFormat(), listOf(
                Pair(listOf(false, false), listOf(false)),
                Pair(listOf(false, true), listOf(true)),
                Pair(listOf(true, false), listOf(true)),
                Pair(listOf(true, true), listOf(false))
        ))
    }

    monitors.add(LearningMonitor())
    monitors.add(GridMonitor())
}