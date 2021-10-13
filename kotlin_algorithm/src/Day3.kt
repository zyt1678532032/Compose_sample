import kotlin.math.max

fun main() {
//    println(TestJvmField().name)
//    func1(arrayOf("zyt", "shxx"))
//    testSealedClass(MyColor.Red)
//    IntRange(0, 2).forEach {
//        println(it)
//    }


}

class TestJvmField {
    @JvmField
    val name = "zyt"
}

// Array ---> vararg
fun func1(args: Array<String>) {
    funcVararg(*args)
}

fun funcVararg(vararg args: String) {
    args.forEach {
        println(it)
    }
}

fun testSealedClass(color: MyColor) {
    when (color) {
        is MyColor.Yellow -> println("MyColor.Yellow")
        is MyColor.Red -> println("MyColor.Red")
        is MyColor.Black -> println("MyColor.Black")
    }
}

// 枚举类也可以
sealed class MyColor {
    object Yellow : MyColor()

    object Red : MyColor()

    object Black : MyColor()
}

// 判断数组中是否存在重复元素
fun containsDuplicate(nums: IntArray): Boolean {
    if (nums.size == 1) {
        return false
    }
    for (i in nums.indices) {
        val flag = nums[i]
        for (j in i + 1 until nums.size) {
            if (nums[j] == flag) {
                return true
            }
        }
    }
    return false
}

val ints = intArrayOf(7, 3, 2, 1, 2)

/* 最大子序和
输入：nums = [-2,1,-3,4,-1,2,1,-5,4]
输出：6
解释：连续子数组 [4,-1,2,1] 的和最大，为 6 。
* */
val nums = intArrayOf(-2, 1, -3, 4, -1, 2, 1, -5, 4)

fun maxSubArray(nums: IntArray): Int {
    for (i in IntRange(1, nums.size - 1)) {
        nums[i] = nums[i] + max(nums[i - 1], 0)
    }
    return nums.maxOrNull()!!
}


/**
 *  二分查找
给定一个n个元素有序的（升序）整型数组 nums 和一个目标值target,
写一个函数搜索nums中的 target，如果目标值存在返回下标，否则返回 -1。

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/binary-search
 */
val numsSearch = intArrayOf(-1, 0, 3, 5, 9, 12)

fun search(nums: IntArray, target: Int): Int {
    if (nums.isEmpty()) {
        return -1
    }
    var left = 0
    var right = nums.size - 1

    while (left <= right) {
        val mid = (right + left) / 2
        when {
            nums[mid] == target -> {
                return mid
            }
            nums[mid] < target -> {
                left = mid + 1
            }
            nums[mid] > target -> {
                right = mid - 1
            }
        }
    }
    return -1
}

val searchInsert = intArrayOf(1, 3, 5, 6)
fun searchInsert(nums: IntArray, target: Int): Int {
    nums.filterIndexed { index, i ->
        if (nums[index] >= target) {
            return index
        }
        true //后面的(^filterIndexed)就是表示局部返回
    }
    return nums.size
}



val merge = intArrayOf(1, 2, 3, 0, 0, 0)
val merge2 = intArrayOf(2, 5, 6)
fun merge(nums1: IntArray, m: Int, nums2: IntArray, n: Int) {
    if (n == 0) {
        return
    }
    var i2 = 0
    for (i in m until nums1.size) {
        nums1[i] = nums2[i2++]
    }
    nums1.sort()
}

val test = intArrayOf(-2, 5, 6)
fun sortedSquares(nums: IntArray): IntArray {
    nums.forEachIndexed { index, element ->
        nums[index] = element.times(element)
    }
    nums.sort()
    return nums
}

fun rotate(nums: IntArray, k: Int): Unit {
    if (k == nums.size) {
        return
    }
    val result = IntArray(nums.size)
    nums.forEachIndexed { index, element ->
        result[(index + k) % nums.size] = nums[index]
    }
    result.copyInto(nums)
}

val n1 = intArrayOf(1, 2, 2, 1)
val n2 = intArrayOf(2, 4)
fun intersect(nums1: IntArray, nums2: IntArray): IntArray {
    val list1 = mutableListOf<Int>()
    nums1.forEach {
        list1.add(it)
    }
    val list2 = mutableListOf<Int>()
    nums2.forEach {
        if (list1.contains(it)) {
            list2.add(it)
            list1.remove(it)
        }
    }
    val result = IntArray(list2.size)
    var index = 0
    list2.forEach {
        result[index++] = it
    }
    return result

}
