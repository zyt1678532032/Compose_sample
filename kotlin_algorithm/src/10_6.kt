// 66. 加一
fun plusOne(digits: IntArray): IntArray {

    for (i in digits.size - 1 downTo 0) {
        if (digits[i] != 9) {
            digits[i]++
            return digits
        }
        digits[i] = 0
    }
    // 全为9
    val result = IntArray(digits.size + 1) { index ->
        0
    }
    result[0] = 1
    return result
}

fun main() {
    println(plusOne(intArrayOf(1, 2, 3)).contentToString())
}

// 119. 杨辉三角 II
fun getRow(rowIndex: Int): List<Int> {
    if (rowIndex == 0) {
        return listOf(1)
    }
    if (rowIndex == 1) {
        return listOf(1, 1)
    }
    val result = mutableListOf(1)
    val list = getRow(rowIndex - 1)
    for (i in 0 until list.size - 1) {
        result += list[i] + list[i + 1]
    }
    result += 1
    return result
}

// 169. 多数元素
fun majorityElement(nums: IntArray): Int {
    // value: 次数
    val dict = mutableMapOf<Int, Int>()
    nums.forEach {
        if (dict.containsKey(it)) {
            dict[it] = dict[it]!! + 1
            return@forEach
        }
        dict[it] = 1
    }
    return dict.entries.find { it.value > nums.size / 2 }!!.key
}