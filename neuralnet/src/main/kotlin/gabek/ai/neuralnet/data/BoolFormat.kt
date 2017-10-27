package gabek.ai.neuralnet.data

class BoolFormat(
        private val falseValue: Double = 0.1,
        private val trueValue: Double = 0.9
): DataFormat<List<Boolean>> {

    override var desiredWidth = -1
        private set

    override fun prepare(data: List<Boolean>) {
        desiredWidth = data.size
    }

    override fun toFormat(signal: List<Double>): List<Boolean> =
            signal.mapTo(ArrayList()){it <= 0.5 }

    override fun toSignal(data: List<Boolean>): List<Double> =
            data.mapTo(ArrayList()) { if(it) trueValue else falseValue }
}
