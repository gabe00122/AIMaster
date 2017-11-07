package gabek.ai.markov

import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList
import kotlin.collections.LinkedHashMap

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
    val chain = Chain(1)

    val data = fromFile(File("dist.male.first.txt"))
    //println(data)
    chain.train(data)

    val rand = Random()
    val wordFreqMap = LinkedHashMap<String, Int>()
    val wordFreqList = ArrayList<Pair<String, Int>>()

    repeat(100) {
        val word = chain.generate(rand).toString()
        wordFreqMap.compute(word) { _, count ->
            if(count == null) 1 else count + 1
        }
    }
    wordFreqMap.mapTo(wordFreqList) { (word, count) -> Pair(word, count) }
    wordFreqList.sortWith(Comparator { (_, a), (_, b) ->
        when {
            a > b -> -1
            b > a -> 1
            else -> 0
        }
    })

    println("Shortest: ${shortestWord(wordFreqList)?.first}")
    println("Longest: ${longestWord(wordFreqList)?.first}")

    wordFreqList.take(25).forEach { (word, count) ->
        println("word: $word, count: $count")
    }
}

