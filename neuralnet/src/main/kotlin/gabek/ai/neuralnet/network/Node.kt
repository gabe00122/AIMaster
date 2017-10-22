package gabek.ai.neuralnet.network

import java.util.*

/**
 * @author Gabriel Keith
 * @date 8/27/2017
 */
class Node(private val random: Random){
    private val bias = 1.0
    private var biasWeight = random.nextDouble() - 0.5

    private val forwardConnections = ArrayList<Connection>()
    private val backwardConnections = ArrayList<Connection>()

    var signal = 0.0
    var errorSignal = 0.0

    fun connect(layer: Layer){
        for(node in layer.nodes){
            val connection = Connection(node, this)
            connection.weight = random.nextDouble() - 0.5
            forwardConnections.add(connection)
            node.backwardConnections.add(connection)
        }
    }

    fun propagate(){
        signal = bias * biasWeight

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

    fun updateWeights(learningRate: Double){
        biasWeight += learningRate * errorSignal * bias
        for(con in backwardConnections){
            con.weight += learningRate * errorSignal * con.backwardNode.signal
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
}