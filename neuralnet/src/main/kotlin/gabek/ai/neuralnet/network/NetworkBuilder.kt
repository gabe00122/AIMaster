package gabek.ai.neuralnet.network

import java.util.*

class NetworkBuilder {
    var input = -1
    var hidden = emptyList<Int>()
    var output = -1
    var learningRate = 0.0
    var random = Random()

    fun build() = Network(input, hidden, output, learningRate, random)
}
