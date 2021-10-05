import java.util.*
import kotlin.collections.HashMap
import kotlin.text.Typography.times

fun singleNumber(nums: IntArray): Int {
    val dict = HashMap<Int, Int>()
    nums.forEach {
        if (dict[it] == null) {
            dict[it] = 1
            return@forEach
        }
        dict[it] = dict[it]!! + 1
    }
    dict.entries.forEach {
        if (it.value == 1) {
            return it.key
        }
    }
    return 1
}

// 26. 删除有序数组中的重复项
fun removeDuplicates(nums: IntArray): Int {
    if (nums.size == 0) {
        return 0
    }
    if (nums.size == 1) {
        return 1
    }
    var index = 0
    for (i in 1 until nums.size) {
        if (nums[i] != nums[index]) {
            nums[++index] = nums[i]
        }
    }
    // println(nums.contentToString())
    return index + 1
}

// 27. 移除元素
fun removeElement(nums: IntArray, `val`: Int): Int {
    var index = 0
    for (i in nums.indices) {
        if (nums[i] != `val`) {
            nums[index++] = nums[i]
        }
    }
    return index + 1
}

// 66. 加一
fun plusOne(digits: IntArray): IntArray {
    var sum = 0
    var index = digits.size - 1
    digits.forEach {
        sum += it * (10 `**` index)
        index--
    }
    sum += 1
    val result = IntArray(sum.toString().length)

    index = sum.toString().length - 1
    while (sum != 0) {
        result[index] = sum % 10
        sum /= 10
        index--
    }
    return result
}

infix fun Int.`**`(times: Int): Int {// 10 ** 2 == 100 ; 8 ** 2 = 64
    var result = 1
    for (i in 0 until times) {
        result *= this
    }
    return result
}

fun main() {
    println(10 / 10)
    println(plusOne(intArrayOf(1,9,9)).contentToString())
    //removeDuplicates(intArrayOf(1, 1, 2, 3, 3, 5, 7))

}