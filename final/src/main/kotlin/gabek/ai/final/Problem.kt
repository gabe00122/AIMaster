package gabek.ai.final

import java.util.*

interface Problem {
    fun apply(x: Int, y: Int, bitsize: Int): Int
}

fun domain(bitsize: Int) = Math.pow(2.0, bitsize.toDouble()).toInt()

object Add: Problem {
    override fun apply(x: Int, y: Int, bitsize: Int) = x + y
    override fun toString(): String  = "x + y"
}

object Subtract: Problem {
    override fun apply(x: Int, y: Int, bitsize: Int) = Math.abs(x - y)
    override fun toString(): String = "abs(x - y)"
}

object XOr: Problem {
    override fun apply(x: Int, y: Int, bitsize: Int) =  x.xor(y)
    override fun toString() = "x xor y"
}

object Divide: Problem {
    override fun apply(x: Int, y: Int, bitsize: Int) = x / y
}

object Multiply: Problem {
    override fun apply(x: Int, y: Int, bitsize: Int) = x * y
    override fun toString(): String = "x * y"
}

object Shift: Problem {
    override fun apply(x: Int, y: Int, bitsize: Int): Int = x.shr(y).or(x.shl(bitsize-y))
    override fun toString(): String = "x >> y"
}
object Greater: Problem {
    override fun apply(x: Int, y: Int, bitsize: Int): Int = if(x > y) x else y
    override fun toString(): String = "y > x"
}

object RandomPairs: Problem {
    val random = Random()
    val pairs = Array(256){
        Array(256){
            random.nextInt(256)
        }
    }

    override fun apply(x: Int, y: Int, bitsize: Int) = pairs[y][x]

    override fun toString() = "Random"
}