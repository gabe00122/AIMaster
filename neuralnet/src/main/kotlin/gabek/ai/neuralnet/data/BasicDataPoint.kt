package gabek.ai.neuralnet.data

class BasicDataPoint<out I, out O>(
        override val x: I,
        override val y: O
): DataPoint<I, O>
