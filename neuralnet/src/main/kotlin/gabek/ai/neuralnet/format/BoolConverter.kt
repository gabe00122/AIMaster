package gabek.ai.neuralnet.format

class BoolConverter : DataConverter<List<BoolConverter.Bool>> {
    override fun dataToSignals(data: List<Bool>): List<Double> {
        return List(data.size){ i ->
            when(data[i]){
                Bool.TRUE -> 0.9
                Bool.FALSE -> 0.1

                Bool.UNDEFINED -> 0.5
            }
        }

    }

    override fun signalsToData(signals: List<Double>): List<Bool> {
        return List(signals.size){ i ->
            val s = signals[i]
            when {
                s < 0.2 -> Bool.FALSE
                s > 0.8 -> Bool.TRUE
                else -> Bool.UNDEFINED
            }
        }
    }

    override fun fromYaml(line: Any): List<Bool> {
        val lin = line as List<*>
        return List(lin.size){ i ->
            when(lin[i] as Boolean){
                true -> Bool.TRUE
                false -> Bool.FALSE
            }
        }
    }

    enum class Bool {
        TRUE, FALSE, UNDEFINED
    }
}
