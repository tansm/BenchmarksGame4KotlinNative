import kotlin.jvm.JvmStatic
import kotlin.math.sqrt

/* The Computer Language Benchmarks Game
   https://salsa.debian.org/benchmarksgame-team/benchmarksgame/

   contributed by Mark C. Lewis
   double[] instead of Object[] by Han Kai
*/

// https://benchmarksgame-team.pages.debian.net/benchmarksgame/program/nbody-java-5.html
object NBody {

    fun test(args: Array<String>) {
        val n = if(args.isEmpty()) 50000000 else args[0].toInt()
        val bodies = NBodySystem()
        println("${bodies.energy()}")
        for (i in 0 until n) bodies.advance(0.01)
            println("${bodies.energy()}")
    }

    internal class NBodySystem {
        private val _bodies = doubleArrayOf( //sun begin
            0.0, 0.0, 0.0, 0.0, 0.0, 0.0, SOLAR_MASS, 0.0,  //sun end
            //jupiter begin
            4.84143144246472090e+00,  //
            -1.16032004402742839e+00,  //
            -1.03622044471123109e-01,  //
            1.66007664274403694e-03 * DAYS_PER_YEAR,  //
            7.69901118419740425e-03 * DAYS_PER_YEAR,  //
            -6.90460016972063023e-05 * DAYS_PER_YEAR,  //
            9.54791938424326609e-04 * SOLAR_MASS,  //
            0.0,  //jupiter end
            //saturn begin
            8.34336671824457987e+00,  //
            4.12479856412430479e+00,  //
            -4.03523417114321381e-01,  //
            -2.76742510726862411e-03 * DAYS_PER_YEAR,  //
            4.99852801234917238e-03 * DAYS_PER_YEAR,  //
            2.30417297573763929e-05 * DAYS_PER_YEAR,  //
            2.85885980666130812e-04 * SOLAR_MASS,  //
            0.0,  //saturn end
            //uranus begin
            1.28943695621391310e+01,  //
            -1.51111514016986312e+01,  //
            -2.23307578892655734e-01,  //
            2.96460137564761618e-03 * DAYS_PER_YEAR,  //
            2.37847173959480950e-03 * DAYS_PER_YEAR,  //
            -2.96589568540237556e-05 * DAYS_PER_YEAR,  //
            4.36624404335156298e-05 * SOLAR_MASS,  //
            0.0,  //uranus end
            //neptune begin
            1.53796971148509165e+01,  //
            -2.59193146099879641e+01,  //
            1.79258772950371181e-01,  //
            2.68067772490389322e-03 * DAYS_PER_YEAR,  //
            1.62824170038242295e-03 * DAYS_PER_YEAR,  //
            -9.51592254519715870e-05 * DAYS_PER_YEAR,  //
            5.15138902046611451e-05 * SOLAR_MASS,  //
            0.0 //neptune end
        )

        init {
            var px = 0.0
            var py = 0.0
            var pz = 0.0
            for (i in 0 until BODY_COUNT) {
                val ioffset = BODY_SIZE * i
                val imass = _bodies[ioffset + mass]
                px += _bodies[ioffset + vx] * imass
                py += _bodies[ioffset + vy] * imass
                pz += _bodies[ioffset + vz] * imass
            }
            _bodies[vx] = -px / SOLAR_MASS
            _bodies[vy] = -py / SOLAR_MASS
            _bodies[vz] = -pz / SOLAR_MASS
        }

        fun advance(dt: Double) {
            val bodies = _bodies
            for (i in 0 until BODY_COUNT) {
                val offset = BODY_SIZE * i
                for (j in i + 1 until BODY_COUNT) {
                    val joffset = BODY_SIZE * j
                    val dx = bodies[offset + x] - bodies[joffset + x]
                    val dy = bodies[offset + y] - bodies[joffset + y]
                    val dz = bodies[offset + z] - bodies[joffset + z]
                    val dSquared = dx * dx + dy * dy + dz * dz
                    val distance: Double = sqrt(dSquared)
                    val mag = dt / (dSquared * distance)
                    val jmass = bodies[joffset + mass]
                    bodies[offset + vx] -= dx * jmass * mag
                    bodies[offset + vy] -= dy * jmass * mag
                    bodies[offset + vz] -= dz * jmass * mag
                    val imass = bodies[offset + mass]
                    bodies[joffset + vx] += dx * imass * mag
                    bodies[joffset + vy] += dy * imass * mag
                    bodies[joffset + vz] += dz * imass * mag
                }
            }
            for (i in 0 until BODY_COUNT) {
                val ioffset = BODY_SIZE * i
                bodies[ioffset + x] += dt * bodies[ioffset + vx]
                bodies[ioffset + y] += dt * bodies[ioffset + vy]
                bodies[ioffset + z] += dt * bodies[ioffset + vz]
            }
        }

        fun energy(): Double {
            val bodies = _bodies
            var dx: Double
            var dy: Double
            var dz: Double
            var distance: Double
            var e = 0.0
            for (i in 0 until BODY_COUNT) {
                val offset = BODY_SIZE * i
                val ivx = bodies[offset + vx]
                val ivy = bodies[offset + vy]
                val ivz = bodies[offset + vz]
                val imass = bodies[offset + mass]
                e += 0.5 * imass * (ivx * ivx + ivy * ivy + ivz * ivz)
                for (j in i + 1 until BODY_COUNT) {
                    val joffset = BODY_SIZE * j
                    val ix = bodies[offset + x]
                    val iy = bodies[offset + y]
                    val iz = bodies[offset + z]
                    dx = ix - bodies[joffset + x]
                    dy = iy - bodies[joffset + y]
                    dz = iz - bodies[joffset + z]
                    distance = sqrt(dx * dx + dy * dy + dz * dz)
                    e -= imass * bodies[joffset + mass] / distance
                }
            }
            return e
        }

        companion object {
            private const val PI = 3.141592653589793
            private const val SOLAR_MASS = 4 * PI * PI
            private const val DAYS_PER_YEAR = 365.24
            private const val BODY_SIZE = 8
            private const val BODY_COUNT = 5
            private const val x = 0
            private const val y = 1
            private const val z = 2
            private const val vx = 3
            private const val vy = 4
            private const val vz = 5
            private const val mass = 6
        }
    }
}