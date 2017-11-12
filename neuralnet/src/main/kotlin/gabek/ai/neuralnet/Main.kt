package gabek.ai.neuralnet

import gabek.ai.neuralnet.executions.imagematch
import gabek.ai.neuralnet.executions.and
import gabek.ai.neuralnet.executions.xor
import gabek.ai.neuralnet.monitor.MetaMonitor


/**
 * @author Gabriel Keith
 * @date 8/27/2017
 */

/*
fun main(args: Array<String>){
    imagematch().build().execute()
}
*/



fun main(args: Array<String>) {
    val eb = imagematch()
    val monitor = MetaMonitor(50)
    eb.monitors.add(monitor)

    monitor.begin()
    //for (i in 1..10) {
    //val learningRate = i / 25.0
    //eb.networkBuilder!!.learningRate = learningRate
    for (j in 1..10) {
        val targetError = j * 0.05
        monitor.next("$targetError")
        eb.trainingBuilder!!.targetError = targetError
        println("Testing with target error of $targetError")
        repeat(monitor.reps) {
            eb.build().execute()
        }
    }
    //}
    monitor.end()
}
