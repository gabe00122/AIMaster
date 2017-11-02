package gabek.ai.neuralnet.image

import gabek.ai.neuralnet.data.DataFormat

class ImageFormat : DataFormat<Region> {
    override var desiredWidth = -1
        private set

    override fun prepare(data: Region) {
        desiredWidth = data.width * data.height
    }

    override fun toFormat(signal: List<Double>): Region {
        throw NotImplementedError()
    }

    override fun toSignal(data: Region): List<Double> {
        val out = ArrayList<Double>(desiredWidth)

        for(y in 0 until data.height){
            for(x in 0 until data.width){
                out.add(0.9 - data.getColorSum(x, y) * 0.8)
            }
        }

        return out
    }
}