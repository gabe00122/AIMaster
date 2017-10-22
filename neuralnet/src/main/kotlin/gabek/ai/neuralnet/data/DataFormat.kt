package gabek.ai.neuralnet.data

interface DataFormat<T>{
    val desiredWidth: Int

    fun toSignal(data: List<T>): List<Double>
    fun toFormat(signal: List<Double>): List<T>
}
