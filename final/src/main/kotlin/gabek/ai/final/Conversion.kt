package gabek.ai.final

fun toBinSignals(number: Int, bitsize: Int, signals: ArrayList<Double>, offset: Int) {
    for (i in 0 until bitsize) {
        signals[i + offset] = if (number.shr(i).and(1) == 1) 0.9 else 0.1
    }
}

fun toNumber(signals: List<Double>): Int {
    var num = 0.0

    for ((i, signal) in signals.withIndex()) {
        if (signal > 0.5) {
            num += Math.pow(2.0, i.toDouble())
        }
    }
    return num.toInt()
}

