package 深度优先遍历

import java.util.*
import kotlin.collections.HashMap

// 397. 整数替换
class Solution {

    val map = HashMap<Int, Int>() // 添加哈希表，减少重复数值的计算

    fun integerReplacement(n: Int): Int {
        if (n == 1) return 0
        if (map[n] == null) {
            if (n % 2 == 0) {
                map[n] = 1 + integerReplacement(n / 2)
            } else {
                map[n] = 2 + Math.min(integerReplacement(n / 2), integerReplacement(n / 2 + 1))
            }
        }
        return map[n]!!
    }
}

class Node(var `val`: Int) {
    var children: List<Node?> = listOf()
}

// 559. N 叉树的最大深度
class Solution2 {

    fun maxDepth(root: Node?): Int {
        var result = 0
        val queue: Queue<Node> = LinkedList()
        // TODO: 2021/11/21 错误答案
        // queue.offer(root) // 为什么null也可以添加进去,而Queue<Node>按道理不能添加null啊
        // FIXME: 2021/11/21 正确处理
        root?.let { queue.add(it) }
        while(queue.size != 0){
            var size = queue.size // 当前层的节点数目
            while(size != 0){
                val node: Node = queue.poll()
                for(children in node.children){
                    queue.offer(children)
                }
                size--
            }
            result++
        }
        return result
    }
}
// 594. 最长和谐子序列
fun findLHS(nums: IntArray): Int {
    // 排序
    nums.sort()
    // 将数值放进map
    val map = mutableMapOf<Int, Int>() // <value,count>
    nums.forEach {
        if (map[it] == null) {
            map[it] = 1
        } else {
            map[it] = map[it]!! + 1
        }
    }
    var result = 0
    val keys = map.keys.toList()
    for (i in 0 until keys.size - 1) {
        if (keys[i + 1] - keys[i] == 1) {
            val num = map[keys[i + 1]]!! + map[keys[i]]!!
            if (num > result) {
                result = num
            }
        }
    }
    return result
}

fun main() {
    // val test = intArrayOf(1, 3, 2, 2, 5, 2, 3, 7)
    // findLHS(test)
    // val root =
    val solution2 = Solution2()

}