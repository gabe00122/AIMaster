package gabek.ai.neuralnet.data

class DataSet<I, O>(
        inputFormat: DataFormat<I>,
        outputFormat: DataFormat<O>,
        data: List<Pair<List<I>, List<O>>>){

    val signals: List<DataPoint<List<Double>, List<Double>>> =
            data.mapTo(ArrayList()) { (input, output) ->
                BasicDataPoint(inputFormat.toSignal(input), outputFormat.toSignal(output))
            }

    val inputSize = data[0].first.size * inputFormat.desiredWidth
    val outputSize = data[0].second.size * outputFormat.desiredWidth
}

