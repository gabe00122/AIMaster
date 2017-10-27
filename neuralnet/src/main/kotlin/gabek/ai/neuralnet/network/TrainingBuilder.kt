package gabek.ai.neuralnet.network

import gabek.ai.neuralnet.data.DataSet

class TrainingBuilder {
    var maxReps = 0
    var testInterval = 0
    var targetError = 0.0

    var splitData: Boolean = true
    var trainingTestSplit: Double = 0.8

    var dataSet: DataSet<*, *>? = null

    fun build() = Training(maxReps, testInterval, targetError, splitData, trainingTestSplit, dataSet!!)
}

