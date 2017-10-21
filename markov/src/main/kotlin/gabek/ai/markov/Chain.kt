package gabek.ai.markov

import java.util.*
import kotlin.collections.LinkedHashMap

class Chain(val windowSize: Int){
    private val matrix = LinkedHashMap<String, MatrixRow>()
    private var totalWords = 0

    private val defer: Chain? = if(windowSize > 1) Chain(windowSize - 1) else null

    fun train(words: List<CharSequence>){
        for(word in words){
            train(word)
        }
    }

    fun train(word: CharSequence){
        defer?.train(word)
        totalWords++

        val modWord = StringBuilder()
        modWord.append('<')
        modWord.append(word)
        modWord.append('>')

        if(modWord.length + 1 >= windowSize) {
            val end = modWord.length - windowSize

            for (i in 0 until end) {
                val slice = modWord.slice(i until i + windowSize)
                train(slice, modWord[i + windowSize])
            }
        }
    }

    private fun train(f: CharSequence, c: Char){
        val row = matrix.getOrPut(f.toString(), { MatrixRow() })
        row.tot++
        row.symbols[c] = row.symbols.getOrDefault(c, 0) + 1
    }

    fun generate(rand: Random): CharSequence {
        val builder = StringBuilder()
        builder.append('<')

        var c = guess(rand, builder)

        while(c != '>' && c != '?'){
            builder.append(c)
            c = guess(rand, builder.slice(maxOf(0, builder.length - windowSize) until builder.length))
        }

        return builder.slice(1 until builder.length)
    }


    private fun guess(rand: Random, f: CharSequence): Char {
        if(f.isEmpty()){
            return '?'
        }

        if(f.length < windowSize){
            return defer?.guess(rand, f) ?: '?'
        }

        val row = matrix[f.toString()]
        if(row != null){
            val r = rand.nextDouble()
            var accum = 0.0

            for((s, count) in row.symbols){
                accum += count.toDouble() / row.tot

                if(accum >= r){
                    return s
                }
            }

            throw IllegalStateException()
        } else {
            return guess(rand, f.slice(1 until f.length))
        }
    }

    private class MatrixRow {
        var tot = 0
        var symbols = LinkedHashMap<Char, Int>()

        override fun toString(): String {
            return "{tot=$tot, symbols=$symbols}"
        }
    }
}

