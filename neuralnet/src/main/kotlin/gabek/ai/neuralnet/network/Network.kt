package gabek.ai.neuralnet.network

import java.util.*
import kotlin.collections.ArrayList

/**
 * @author Gabriel Keith
 * @date 8/28/2017
 */
class Network(input: Int, hidden: List<Int>, output: Int, private val learningRate: Double, random: Random){
    private val inputLayer = Layer(input, random)
    private val outputLayer = Layer(output, random)
    private val hiddenLayers = Array(hidden.size, { i -> Layer(hidden[i], random) })

    val outputList = ArrayList<Double>()

    init {
        var currentLayer = inputLayer
        for(layer in hiddenLayers){
            currentLayer.connect(layer)
            currentLayer = layer
        }
        currentLayer.connect(outputLayer)

        repeat(output) { outputList.add(0.0) }
    }

    fun train(input: List<Double>, target: List<Double>) {
        process(input)
        outputLayer.calculateError(target)

        if(hiddenLayers.isNotEmpty()) {
            for (i in hiddenLayers.size - 1 downTo 0) {
                hiddenLayers[i].backPropagate()
            }
        }
        inputLayer.backPropagate()

        inputLayer.updateWeights(learningRate)
        for(layer in hiddenLayers){
            layer.updateWeights(learningRate)
        }
        outputLayer.updateWeights(learningRate)
    }

    fun test(input: List<Double>, target: List<Double>): Double{
        var sum = 0.0
        val result = process(input)
        repeat(result.size){ i ->
            sum += Math.pow(target[i] - result[i], 2.0)
        }
        return sum
    }

    fun process(input: List<Double>): List<Double> {
        resetSignals()
        inputLayer.setInputs(input)

        for(hiddenLayer in hiddenLayers){
            hiddenLayer.propagate()
        }
        outputLayer.propagate()

        outputLayer.retrieveOutput(outputList)
        return outputList
    }

    override fun toString(): String = buildString {
        var stop = false
        var i = 0
        while(!stop){
            stop = true
            if(i < inputLayer.size){
                append(inputLayer.nodes[i].errorSignal)
                stop = false
            }
            append("\t")
            for(layer in hiddenLayers) {
                if (i < layer.size) {
                    append(layer.nodes[i].errorSignal)
                    stop = false
                }
                append("\t")
            }
            if(i < outputLayer.size){
                append(outputLayer.nodes[i].errorSignal)
                stop = false
            }
            append("\n")
            i++
        }
    }

    private fun resetSignals(){
        inputLayer.resetSignal()
        for(hidden in hiddenLayers){
            hidden.resetSignal()
        }
        outputLayer.resetSignal()
    }
}