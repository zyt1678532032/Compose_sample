import java.util.*
import kotlin.collections.HashMap

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

fun main() {
    removeDuplicates(intArrayOf(1,1,2,3,3,5,7))
}