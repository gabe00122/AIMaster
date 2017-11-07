package gabek.ai.neuralnet.monitor

import gabek.ai.neuralnet.Execution
import gabek.ai.neuralnet.ExecutionMonitor
import java.io.*
import java.util.*
import kotlin.collections.ArrayList

class AccuracyMonitor : ExecutionMonitor() {
    val outputFile = File("./output/accuracy.txt")

    override fun onCompletion(execution: Execution) {
        val format = execution.testSet.outputFormat
        val network = execution.network

        val numCats = format.desiredWidth

        val matrix = Array<Array<Int>>(numCats, { Array(numCats, { 0 }) })
        val atlas = LinkedHashMap<Any?, Int>()

        for (point in execution.testSet.signals) {
            val x = format.toFormat(network.process(point.x))
            val y = point.formatY

            val xIndex = atlas.computeIfAbsent(x, { atlas.size })
            val yIndex = atlas.computeIfAbsent(y, { atlas.size })
            matrix[yIndex][xIndex]++
        }

        val xMiss = Array<Int>(numCats, { 0 })
        val yMiss = Array<Int>(numCats, { 0 })
        for (i in 0 until xMiss.size){
            for(j in 0 until xMiss.size){
                if (i != j){
                    xMiss[j] += matrix[i][j]
                    yMiss[i] += matrix[i][j]
                }
            }
        }

        val sortedAtlas = ArrayList<Pair<String, Int>>()
        atlas.mapTo(sortedAtlas) { (key, value) -> Pair(key.toString(), value) }
        sortedAtlas.sortBy(Pair<String, Int>::first)


        print("    ")
        for ((c, _) in sortedAtlas){
            print("$c   ")
        }
        println()
        for ((y, i) in sortedAtlas){
            print("$y   ")
            for ((_, j) in sortedAtlas) {
                print("${matrix[i][j]}   ")
            }
            println("${yMiss[i]}")
        }
        print("    ")
        for ((_, i) in sortedAtlas){
            print("${xMiss[i]}   ")
        }
        println()
    }
}