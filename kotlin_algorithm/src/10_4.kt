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


fun main() {

    println(10 / 10)
    //removeDuplicates(intArrayOf(1, 1, 2, 3, 3, 5, 7))
    buildString {
        appendLine()
    }
    val numbers = listOf(1, 2, 3, 4, 5, 6)
    val invertedOddNumbers = numbers
        .filter { it % 2 != 0 }
        .joinToString{ "${-it}" }
    println(invertedOddNumbers)


}

class A{

}
