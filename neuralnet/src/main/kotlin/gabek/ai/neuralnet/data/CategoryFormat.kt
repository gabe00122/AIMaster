package gabek.ai.neuralnet.data

class CategoryFormat : DataFormat<String> {
    val high = 0.9
    val low = 0.1

    private val categories = ArrayList<String>()
    private val categoryMap = HashMap<String, Int>()

    override val desiredWidth
        get() = categories.size

    override fun prepare(data: String) {
        if(!categories.contains(data)) {
            categoryMap[data] = categories.size
            categories.add(data)
        }
    }

    override fun toSignal(data: String): List<Double> {
        val out = ArrayList<Double>(desiredWidth)
        repeat(desiredWidth){
            out.add(0.0)
        }
        out[categoryMap[data]!!] = high

        return out
    }

    override fun toFormat(signal: List<Double>): String {
        var maxIndex = 0
        var maxValue = signal[0]

        for(i in 1 until signal.size){
            if(signal[i] > maxValue){
                maxIndex = i
                maxValue = signal[i]
            }
        }

        return categories[maxIndex]
    }
}