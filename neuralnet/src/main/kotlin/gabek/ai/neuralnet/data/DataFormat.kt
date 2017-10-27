package gabek.ai.neuralnet.data

interface DataFormat<T>{
    val desiredWidth: Int

    fun prepare(data: T)
    fun toSignal(data: T): List<Double>
    fun toFormat(signal: List<Double>): T
}
