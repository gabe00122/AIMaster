package gabek.ai.neuralnet.format

interface DataConverter<T> {
    fun dataToSignals(data: T): List<Double>
    fun signalsToData(signals: List<Double>): T

    fun fromYaml(line: Any): T
    fun dataFromYaml(line: Any) = dataToSignals(fromYaml(line))
}
