package gabek.ai.neuralnet.data

class DataPoint(
        val formatX: Any?,
        val formatY: Any?,
        val x: List<Double>,
        val y: List<Double>) {

    operator fun component1() = x
    operator fun component2() = y
}