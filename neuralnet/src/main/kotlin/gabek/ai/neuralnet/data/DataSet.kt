package gabek.ai.neuralnet.data

import java.util.*
import kotlin.collections.ArrayList

class DataSet<I, O>(
        val inputFormat: DataFormat<I>,
        val outputFormat: DataFormat<O>,
        private val data: List<Pair<I, O>>,
        prepareFormats: Boolean = true) {

    init {
        if(prepareFormats) {
            data.forEach { (input, output) ->
                inputFormat.prepare(input)
                outputFormat.prepare(output)
            }
        }
    }

    val signals: List<DataPair> =
            data.mapTo(ArrayList()) { (input, output) ->
                DataPair(inputFormat.toSignal(input), outputFormat.toSignal(output))
            }

    val inputSize get() = inputFormat.desiredWidth
    val outputSize get() = outputFormat.desiredWidth

    fun split(trainingPortion: Double, random: Random = Random()): Pair<DataSet<I, O>, DataSet<I, O>> {
        val temp = ArrayList(data)
        Collections.shuffle(temp)

        val splitAt = (trainingPortion * temp.size).toInt()
        return Pair(
                DataSet(inputFormat, outputFormat, temp.subList(0, splitAt), false),
                DataSet(inputFormat, outputFormat, temp.subList(splitAt, temp.size), false)
        )
    }
}

