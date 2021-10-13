import kotlin.math.sqrt

// 441. 排列硬币
fun arrangeCoins(n: Int): Int {
    // 结果为:(根号下1+8n - 1) / 2
    // 8 * n + 1 有可能越界Int
    val double: Double = (8 * n.toDouble()) + 1
    val result = (sqrt(double) - 1) / 2

    return result.toInt()
}

// 迭代
fun arrangeCoins2(n: Int): Int {
    var n1 = n
    var i = 1
    while (n1 >= i) {
        n1 -= i
        i++
    }
    return i - 1
}

//228. 汇总区间
fun summaryRanges(nums: IntArray): List<String> {
    if (nums.isEmpty()) {
        return listOf()
    }
    // 1.以第一个元素开始,当下标没有越界时,逐渐递增
    val result = mutableListOf<String>()
    var start = nums[0]
    var index = start // 对数组中的数据对比的(递增)变量
    // 2. if 当前元素和递增的不相同,则将前一个元素进行标记,到此第一个有序区间已经找到,重复上面过程
    for (i in 1 until nums.size) {
        // 循环里面的操作应该是什么?
        // 找到有序区间的终点end，继续进行此操作
        index++
        if (nums[i] == index) { // 往下遍历
            continue
        } else {
            // 添加到result
            if (start != nums[i - 1]) {
                result += "$start->${nums[i - 1]}"
                // start 和 end 全部重新开始
                start = nums[i]
                index = start
                continue
            }
            // 相等
            result += "$start"
            start = nums[i] // 改变区间起点
            index = start
        }
    }
    if (index == start) { // 区间只有一个
        result += "$start"
    }
    if (index > start) {
        result += "$start->$index"
    }
    return result

}

fun intersection(nums1: IntArray, nums2: IntArray): IntArray {
    val set1 = mutableSetOf<Int>(*nums1.toTypedArray())
    val set2 = mutableSetOf<Int>()
    nums1.forEach {
        set1 += it
    }
    nums2.forEach {
        if (set1.contains(it)) {
            set2 += it
        }
    }
    return set2.toIntArray()
}

