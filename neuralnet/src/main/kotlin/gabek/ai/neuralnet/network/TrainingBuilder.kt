package gabek.ai.neuralnet.network

import gabek.ai.neuralnet.data.DataSet

class TrainingBuilder {
    var maxReps = 0
    var testInterval = 0
    var targetError = 0.0

    var trainingSet: DataSet<*, *>? = null
    var testSet: DataSet<*, *>? = null

    fun build() = Training(maxReps, testInterval, targetError, trainingSet!!, testSet!!)
}

