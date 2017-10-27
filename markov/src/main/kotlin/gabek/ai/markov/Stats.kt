package gabek.ai.markov


private val wordLength = Comparator<Pair<String, Int>> {
    (a, _), (b, _) -> when {
        a.length > b.length -> 1
        b.length > a.length -> -1
        else -> 0
    }
}

fun shortestWord(word: List<Pair<String, Int>>) = word.minWith(wordLength)
fun longestWord(words: List<Pair<String, Int>>) = words.maxWith(wordLength)
