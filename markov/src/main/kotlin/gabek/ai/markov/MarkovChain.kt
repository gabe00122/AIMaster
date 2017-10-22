package gabek.ai.markov

import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.*
import kotlin.collections.ArrayList

fun fromFile(file: File): List<String>{
    val out = ArrayList<String>()

    InputStreamReader(BufferedInputStream(FileInputStream(file))).useLines { lines ->
        for(line in lines){
            val split = line.split(' ')
            out.add(split[0].toLowerCase())
        }
    }

    return out
}


fun main(args: Array<String>) {
    val chain = Chain(2)

    val data = fromFile(File("dist.all.last.txt"))
    //println(data)
    chain.train(data)

    val rand = Random()

    repeat(10) {
        println(chain.generate(rand))
    }
}

