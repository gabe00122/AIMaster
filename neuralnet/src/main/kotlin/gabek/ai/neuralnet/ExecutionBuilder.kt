package gabek.ai.neuralnet

import gabek.ai.neuralnet.network.NetworkBuilder
import gabek.ai.neuralnet.network.TrainingBuilder

class ExecutionBuilder {
    var networkBuilder: NetworkBuilder? = null
    var trainingBuilder: TrainingBuilder? = null
    var monitors = ArrayList<ExecutionMonitor>()

    fun build(): Execution {
        val networkBuilder = networkBuilder
        val trainingBuilder = trainingBuilder

        if(networkBuilder != null && trainingBuilder != null) {
            val training = trainingBuilder.build()

            if(networkBuilder.input == -1){
                networkBuilder.input = training.dataSet.inputSize
            }
            if(networkBuilder.output == -1){
                networkBuilder.output = training.dataSet.outputSize
            }
            val network = networkBuilder.build()


            return Execution(network, training, monitors)
        } else {
            throw IllegalStateException("Booth the network builder and the training builder must be assigned.")
        }
    }

    @NetworkDsl
    inline fun network(block: NetworkBuilder.() -> Unit){
        val builder = NetworkBuilder()
        builder.block()
        networkBuilder = builder
    }

    @NetworkDsl
    inline fun training(block: TrainingBuilder.() -> Unit){
        val builder = TrainingBuilder()
        builder.block()
        trainingBuilder = builder
    }
}

@NetworkDsl
inline fun execution(block: ExecutionBuilder.() -> Unit): ExecutionBuilder {
    val builder = ExecutionBuilder()
    builder.block()
    return builder
}
