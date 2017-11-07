package gabek.ai.neuralnet

import gabek.ai.neuralnet.executions.imagematch
import gabek.ai.neuralnet.executions.and
import gabek.ai.neuralnet.monitor.MetaMonitor


/**
 * @author Gabriel Keith
 * @date 8/27/2017
 */


fun main(args: Array<String>){
    imagematch().build().execute()
}



/*
fun main(args: Array<String>){
    val eb = imagematch()
    val monitor = MetaMonitor(10)
    eb.monitors.add(monitor)

    monitor.begin()
    for(i in 1 .. 5){
        val learningRate = i / 25.0
        eb.networkBuilder!!.learningRate = learningRate
        //monitor.next("$learningRate")
        for(j in 1 .. 5) {
            val layerSize = 7 + j * 3
            monitor.next("$learningRate\t$layerSize")
            eb.networkBuilder!!.hidden = listOf(layerSize)
            println("Testing with learing rate of $learningRate, $layerSize")
            repeat(monitor.reps) {
                eb.build().execute()
            }
        }
    }
    monitor.end()
}
*/