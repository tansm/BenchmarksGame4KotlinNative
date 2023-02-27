import kotlin.math.max

/**
 * The Computer Language Benchmarks Game
 * https://salsa.debian.org/benchmarksgame-team/benchmarksgame/
 *
 * Loosely based on Jarkko Miettinen's implementation. Requires Java 8.
 *
 * contributed by Heikki Salokanto.
 * modified by Chandra Sekar
 * modified by Mike Krüger
 * *reset*
 */
object BinaryTrees {
    fun test(args: Array<String>) {
        val n = if (args.isNotEmpty()) args[0].toInt() else 21
        val minDepth = 4
        val maxDepth: Int = max(minDepth + 2, n)
        val stretchDepth = maxDepth + 1
        var check = TreeNode.create(stretchDepth).check()
        println("stretch tree of depth " + (maxDepth + 1) + "\t check: " + check)
        val longLivedTree = TreeNode.create(maxDepth)
        var depth = minDepth
        while (depth <= maxDepth) {
            val iterations = 1 shl maxDepth - depth + minDepth
            check = 0
            for (i in 1..iterations) {
                check += TreeNode.create(depth).check()
            }
            println("$iterations\t trees of depth $depth\t check: $check")
            depth += 2
        }
        println("long lived tree of depth " + maxDepth + "\t check: " + longLivedTree.check())
    }

    internal class TreeNode(
        private val left: TreeNode?,
        private val right: TreeNode?
    ) {

        fun check(): Int {
            return if (left != null && right != null) left.check() + right.check() + 1 else 1
        }

        companion object {
            fun create(depth: Int): TreeNode {
                return childTreeNodes(depth)
            }

            private fun childTreeNodes(depth: Int): TreeNode {
                return if (depth > 0) {
                    TreeNode(
                        childTreeNodes(depth - 1),
                    childTreeNodes(depth - 1))
                }else{
                    TreeNode(null,null)
                }
            }
        }
    }
}