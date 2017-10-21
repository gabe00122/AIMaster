package gabek.ai.neuralnet.net

/**
 * @author Gabriel Keith
 * @date 8/28/2017
 */
class Layer(
        private val config: NetworkConfig,
        val size: Int,
        private val nodeType: Node.Type){

    val nodes = Array(size, { Node(config, nodeType) })

    fun connect(layer: Layer){
        for(node in nodes){
            node.connect(layer)
        }
    }

    fun setInputs(inputs: Array<Double>){
        for(i in 0 until nodes.size){
            nodes[i].signal = inputs[i]
        }
    }

    fun retrieveOutput(outputs: Array<Double>){
        for(i in 0 until nodes.size){
            outputs[i] = nodes[i].signal
        }
    }

    fun calculateError(target: Array<Double>){
        for(i in 0 until nodes.size){
            nodes[i].calculateError(target[i])
        }
    }

    fun propagate(){
        for(node in nodes){
            node.propagate()
        }
    }

    fun backPropagate(){
        for(node in nodes){
            node.backPropagate()
        }
    }

    fun updateWeights(){
        for(node in nodes){
            node.updateWeights()
        }
    }

    fun resetSignal(){
        for(node in nodes){
            node.resetSignals()
        }
    }
}