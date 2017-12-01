package gabek.ai.final

import gabek.ai.neuralnet.network.Network
import java.util.*
import kotlin.collections.ArrayList

class NetworkSolver(
        val bitsize: Int,
        var problem: Problem,
        hiddenLayers: List<Int>,
        learningRate: Double,
        val incremental: Boolean = true
){
    private val random = Random()

    val network: Network

    private val inputSize = bitsize*2
    private val outputSize = bitsize*2

    private val xLowerBound: Int = 0
    private val xUpperBound: Int = domain(bitsize)
    private val yLowerBound: Int = 0
    private val yUpperBound: Int = domain(bitsize)

    private val tempInput = ArrayList<Double>()
    private val tempOutput = ArrayList<Double>()

    var iter = 0
    var numRight = 0

    private val outputGrid = Array(yUpperBound - yLowerBound){
        Array(xUpperBound - xLowerBound){ 1.0 }
    }

    init {
        repeat(inputSize) { tempInput.add(0.1) }
        repeat(outputSize) { tempOutput.add(0.1) }

        network = Network(inputSize, hiddenLayers, outputSize, learningRate, random)
    }

    fun train(reps: Int) {
        /*
        iter = outputGrid.size
        val js = ArrayList<Int>()
        repeat(iter) { js.add(it) }
        val iis = ArrayList<Int>()
        repeat(iter) { iis.add(it) }
        repeat(10) {
            Collections.shuffle(js, random)
            for (j in js) {
                Collections.shuffle(iis, random)
                for (i in iis) {
                    val x = j
                    val y = i

                    val z = problem.apply(x, y, bitsize)

                    toBinSignals(x, bitsize, tempInput, 0)
                    toBinSignals(y, bitsize, tempInput, bitsize)
                    toBinSignals(z, outputSize, tempOutput, 0)

                    network.train(tempInput, tempOutput)
                }
            }
        }
        */
       // if (iter < outputGrid.size) {
        //    iter++
        //}
        iter += reps
        for (i in 0 until reps) {
            val x = random.nextInt(xUpperBound - xLowerBound) + xLowerBound
            val y = random.nextInt(yUpperBound - yLowerBound) + yLowerBound

            val z = problem.apply(x, y, bitsize)

            toBinSignals(x, bitsize, tempInput, 0)
            toBinSignals(y, bitsize, tempInput, bitsize)
            toBinSignals(z, outputSize, tempOutput, 0)
            outputGrid[y][x] = network.test(tempInput, tempOutput)
            network.train(tempInput, tempOutput)
        }
    }

    fun testGrid(): Array<Array<Double>> {
        numRight = 0

        for(j in 0 until outputGrid.size){
            for(i in 0 until outputGrid[j].size){
                val x = i + xLowerBound
                val y = j + yLowerBound
                val z = problem.apply(x, y, bitsize)


                toBinSignals(x, bitsize, tempInput, 0)
                toBinSignals(y, bitsize, tempInput, bitsize)
                toBinSignals(z, outputSize, tempOutput, 0)

                outputGrid[j][i] = network.test(tempInput, tempOutput)

                if(toNumber(network.outputList) == z){
                    numRight++
                }
            }
        }

        return outputGrid
    }
}
