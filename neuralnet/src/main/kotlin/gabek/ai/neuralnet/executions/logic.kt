package gabek.ai.neuralnet.executions

import gabek.ai.neuralnet.data.BoolFormat
import gabek.ai.neuralnet.data.DataSet
import gabek.ai.neuralnet.execution
import gabek.ai.neuralnet.monitor.AccuracyMonitor
import gabek.ai.neuralnet.monitor.GridMonitor
import gabek.ai.neuralnet.monitor.LearningMonitor

fun xor() = execution {
    network {
        learningRate = 0.5

        hidden = listOf(2)
    }

    training {
        maxReps = 100000
        testInterval = 1
        targetError = 0.01

        dataSet = DataSet(BoolFormat(), BoolFormat(), listOf(
                Pair(listOf(false, false), listOf(false)),
                Pair(listOf(false, true), listOf(true)),
                Pair(listOf(true, false), listOf(true)),
                Pair(listOf(true, true), listOf(false))
        ))
    }

    monitors.add(LearningMonitor(console = true))
    monitors.add(GridMonitor())
    //monitors.add(AccuracyMonitor())
}

fun and() = execution {
    network {
        learningRate = 0.2

        hidden = listOf(4)
    }

    training {
        maxReps = 100000
        testInterval = 50
        targetError = 0.05

        dataSet = DataSet(BoolFormat(), BoolFormat(), listOf(
                Pair(listOf(false, false), listOf(false)),
                Pair(listOf(false, true), listOf(false)),
                Pair(listOf(true, false), listOf(false)),
                Pair(listOf(true, true), listOf(true))
        ))
    }

    monitors.add(LearningMonitor(console = true))
    monitors.add(GridMonitor())
    //monitors.add(AccuracyMonitor())
}
