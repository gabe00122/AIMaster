package gabek.ai.neuralnet.data

import java.util.*

class DataSet<I, O>(
        private val inputFormat: DataFormat<I>,
        private val outputFormat: DataFormat<O>,
        private val data: List<Pair<I, O>>){

    init {
        data.forEach { (input, output) ->
            inputFormat.prepare(input)
            outputFormat.prepare(output)
        }
    }

    val signals: List<DataPair> =
            data.mapTo(ArrayList()) { (input, output) ->
                DataPair(inputFormat.toSignal(input), outputFormat.toSignal(output))
            }

    val inputSize get() = inputFormat.desiredWidth
    val outputSize get() = outputFormat.desiredWidth

    fun split(trainingPortion: Double, random: Random = Random()): Pair<List<DataPair>, List<DataPair>> {
        Collections.shuffle(signals, random)
        val splitAt = (trainingPortion * signals.size).toInt()
        return Pair(signals.subList(0, splitAt), signals.subList(splitAt, signals.size))
    }
}

