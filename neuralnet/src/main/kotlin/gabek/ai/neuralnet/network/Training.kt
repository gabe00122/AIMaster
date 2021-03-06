package gabek.ai.neuralnet.network

import gabek.ai.neuralnet.data.DataSet

class Training(val maxReps: Int,
               val testInterval: Int,
               val targetError: Double,
               val splitData: Double,
               val dataSet: DataSet<*, *>)