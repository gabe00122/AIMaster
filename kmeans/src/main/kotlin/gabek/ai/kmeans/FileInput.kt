package gabek.ai.kmeans

import java.io.*


fun readXY(file: File): PointField {
    val points = PointField()
    val regex = Regex("\\s+")

    open(file) { lines ->
        for(line in lines) {
            val split = line.trim().split(regex)
            if (split.size == 2) {
                val x = split[0].toDouble()
                val y = split[1].toDouble()

                points.xAxis.add(x)
                points.yAxis.add(y)
            }
        }
    }

    return points
}

inline fun <T> open(file: File, block: (Sequence<String>) -> T): T {
    return InputStreamReader(BufferedInputStream(FileInputStream(file))).useLines(block)
}

