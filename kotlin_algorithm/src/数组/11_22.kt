package 数组

import java.util.*

/**
 * 384. 打乱数组(随机，概率模型)
 */
class Solution(nums: IntArray) {

    var nums: IntArray = nums

    var original: IntArray = IntArray(nums.size)
        set(value) {
            System.arraycopy(value, 0, field, 0, value.size)
            println("original:" + field.contentToString())
        }

    init {
        original = nums
        this.nums = nums
    }

    // 重设数组到它的初始状态并返回
    fun reset(): IntArray {
        System.arraycopy(original, 0, nums, 0, original.size)
        println("reset" + nums.contentToString())
        return nums
    }

    // 返回数组随机打乱后的结果
    fun shuffle(): IntArray {
        val shuffled = IntArray(nums.size)
        val list = mutableListOf<Int>()
        for (i in nums.indices) {
            list += nums[i]
        }
        val random = Random()
        for (i in nums.indices) {
            val j = random.nextInt(list.size)
            shuffled[i] = list.removeAt(j)
        }
        System.arraycopy(shuffled, 0, nums, 0, shuffled.size);
        println("shuffle" + nums.contentToString())
        return nums
    }

}

fun main() {
    val nums = intArrayOf(1, 2, 3)
    val solution = Solution(nums)
    solution.shuffle()
    solution.reset()
}