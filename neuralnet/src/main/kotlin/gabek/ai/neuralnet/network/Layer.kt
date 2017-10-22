package gabek.ai.neuralnet.network

import java.util.*

/**
 * @author Gabriel Keith
 * @date 8/28/2017
 */
class Layer(val size: Int, random: Random){

    val nodes = Array(size, { Node(random) })

    fun connect(layer: Layer){
        for(node in nodes){
            node.connect(layer)
        }
    }

    fun setInputs(inputs: List<Double>){
        for(i in 0 until nodes.size){
            nodes[i].signal = inputs[i]
        }
    }

    fun retrieveOutput(outputs: Array<Double>){
        for(i in 0 until nodes.size){
            outputs[i] = nodes[i].signal
        }
    }

    fun calculateError(target: List<Double>){
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

    fun updateWeights(learningRate: Double){
        for(node in nodes){
            node.updateWeights(learningRate)
        }
    }

    fun resetSignal(){
        for(node in nodes){
            node.resetSignals()
        }
    }
}