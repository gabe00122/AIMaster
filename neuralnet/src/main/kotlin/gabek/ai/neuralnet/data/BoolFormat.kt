package gabek.ai.neuralnet.data

class BoolFormat(
        private val falseValue: Double = 0.1,
        private val trueValue: Double = 0.9
): DataFormat<Boolean> {

    override val desiredWidth = 1

    override fun toFormat(signal: List<Double>): List<Boolean> =
            signal.mapTo(ArrayList()){it <= 0.5 }

    override fun toSignal(data: List<Boolean>): List<Double> =
            data.mapTo(ArrayList()) { if(it) trueValue else falseValue }
}
