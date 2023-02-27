import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

object Tests {

    fun testAll(args: Array<String>){
        //runTest(args, TooSimple::test)
        //runTest(args, BinaryTrees::test)
        runTest(args, NBody::test)
    }

    @OptIn(ExperimentalTime::class)
    private fun runTest(args: Array<String>, test : (Array<String>) -> Unit ){
        val time = measureTimedValue {
            test(args)
        }
        println("time = ${time.duration.inWholeMilliseconds} ms.")
    }
}