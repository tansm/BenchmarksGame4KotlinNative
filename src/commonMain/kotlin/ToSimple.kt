/* The Computer Language Benchmarks Game
   https://salsa.debian.org/benchmarksgame-team/benchmarksgame/
*/

// https://benchmarksgame-team.pages.debian.net/benchmarksgame/program/toosimple-java-1.html
internal object TooSimple {
    fun test(args: Array<String>){
        val n = if (args.isEmpty()) 10000000000 else args[0].toLong()
        var sum = 0.0
        var flip = -1.0
        for (i in 1..n) {
            flip *= -1.0
            sum += flip / (2 * i - 1)
        }
        println("${sum * 4.0}\n")
    }
}