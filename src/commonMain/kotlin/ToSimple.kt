import kotlin.jvm.JvmStatic

/* The Computer Language Benchmarks Game
   https://salsa.debian.org/benchmarksgame-team/benchmarksgame/
*/
internal object toosimple {
    fun test(n : Long) : Double{
        var sum = 0.0
        var flip = -1.0
        for (i in 1..n) {
            flip *= -1.0
            sum += flip / (2 * i - 1)
        }
        return sum
    }
}