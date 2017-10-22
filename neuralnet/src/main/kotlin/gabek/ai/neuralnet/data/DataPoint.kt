package gabek.ai.neuralnet.data

interface DataPoint<out I, out O>{
    val x: I
    val y: O

    operator fun component1() = x
    operator fun component2() = y
}