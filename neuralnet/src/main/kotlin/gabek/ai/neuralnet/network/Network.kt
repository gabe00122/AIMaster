package gabek.ai.neuralnet.network

import java.util.*

/**
 * @author Gabriel Keith
 * @date 8/28/2017
 */
class Network(input: Int, hidden: List<Int>, output: Int, private val learningRate: Double, random: Random){
    private val inputLayer = Layer(input, random)
    private val outputLayer = Layer(output, random)
    private val hiddenLayers = Array(hidden.size, { i -> Layer(hidden[i], random) })

    private val outputArray = Array(outputLayer.size, { 0.0 })

    init {
        var currentLayer = inputLayer
        for(layer in hiddenLayers){
            currentLayer.connect(layer)
            currentLayer = layer
        }
        currentLayer.connect(outputLayer)
    }

    fun train(input: List<Double>, target: List<Double>) {
        process(input)
        outputLayer.calculateError(target)

        if(hiddenLayers.isNotEmpty()) {
            for (i in hiddenLayers.size - 1..0) {
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
        return Math.pow(target[0] - process(input)[0], 2.0)
    }

    fun process(input: List<Double>): Array<Double> {
        resetSignals()
        inputLayer.setInputs(input)

        for(hiddenLayer in hiddenLayers){
            hiddenLayer.propagate()
        }
        outputLayer.propagate()

        outputLayer.retrieveOutput(outputArray)
        return outputArray
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