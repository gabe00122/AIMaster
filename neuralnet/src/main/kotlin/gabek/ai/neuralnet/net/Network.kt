package gabek.ai.neuralnet.net

/**
 * @author Gabriel Keith
 * @date 8/28/2017
 */
class Network(private val config: NetworkConfig){
    private val inputLayer = Layer(config, config.inputSize, Node.Type.INPUT)
    private val outputLayer = Layer(config, config.outputSize, Node.Type.OUTPUT)
    private val hiddenLayers = Array(config.hiddenSizes.size, { i -> Layer(config, config.hiddenSizes[i], Node.Type.HIDDEN) })

    private val outputArray = Array(outputLayer.size, { 0.0 })

    init {
        var currentLayer = inputLayer
        for(layer in hiddenLayers){
            currentLayer.connect(layer)
            currentLayer = layer
        }
        currentLayer.connect(outputLayer)
    }

    fun train(input: Array<Double>, target: Array<Double>) {
        process(input)
        outputLayer.calculateError(target)

        if(hiddenLayers.isNotEmpty()) {
            for (i in hiddenLayers.size - 1..0) {
                hiddenLayers[i].backPropagate()
            }
        }
        //inputLayer.backPropagate()

        //inputLayer.updateWeights()
        for(layer in hiddenLayers){
            layer.updateWeights()
        }
        outputLayer.updateWeights()
    }

    fun test(input: Array<Double>, target: Array<Double>): Double{
        return Math.pow(target[0] - process(input)[0], 2.0)
    }

    fun process(input: Array<Double>): Array<Double> {
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