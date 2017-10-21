package gabek.ai.neuralnet.net

import java.util.*

/**
 * @author Gabriel Keith
 * @date 8/27/2017
 */
class Node(
        private val config: NetworkConfig,
        private val nodeType: Type){

    private var biasWeight = config.random.nextDouble() - 0.5

    private val forwardConnections = ArrayList<Connection>()
    private val backwardConnections = ArrayList<Connection>()

    var signal = 0.0
    var errorSignal = 0.0

    fun connect(layer: Layer){
        for(node in layer.nodes){
            val connection = Connection(node, this)
            connection.weight = config.random.nextDouble() - 0.5
            forwardConnections.add(connection)
            node.backwardConnections.add(connection)
        }
    }

    fun propagate(){
        signal = config.bias * biasWeight

        for(con in backwardConnections){
            signal += con.weight * con.backwardNode.signal
        }

        this.signal = sigmoid(signal)
    }

    fun backPropagate(){
        var sum = 0.0
        for(con in forwardConnections){
            sum += con.weight * con.forwardNode.errorSignal
        }
        errorSignal = signal * (1 - signal) * sum
    }

    fun updateWeights(){
        biasWeight += config.learningRate * errorSignal * config.bias
        for(con in backwardConnections){
            con.weight += config.learningRate * errorSignal * con.backwardNode.signal
        }
    }

    fun calculateError(target: Double){
        errorSignal = (target - signal) * signal * (1.0 - signal)
    }

    fun resetSignals(){
        signal = 0.0
        errorSignal = 0.0
    }

    private fun sigmoid(value: Double): Double {
        return 1.0 / (1.0 + Math.exp(-value))
    }

    enum class Type {
        INPUT, OUTPUT, HIDDEN
    }
}