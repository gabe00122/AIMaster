package gabek.ai.markov

import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.*

fun fromFile(file: File): List<String>{
    InputStreamReader(BufferedInputStream(FileInputStream(file))).use { reader ->
        return reader.readLines()
    }
}


fun main(args: Array<String>) {
    val chain = Chain(5)

    val data = fromFile(File("dist.male.first.txt"))
    println(data)
    chain.train(data)

    val rand = Random()

    repeat(10) {
        println(chain.generate(rand))
    }
}

