// 566. 重塑矩阵
fun matrixReshape(mat: Array<IntArray>, r: Int, c: Int): Array<IntArray> {
    val matR = mat.size
    val matC = mat[0].size
    // 操作不合理
    if (matR * matC != r * c) {
        return mat
    }
    // 操作合理
    // TODO: 2021/9/24 初始化数组
    val result: Array<IntArray> = Array(r) {
        IntArray(c)
    }
    val backIntArray = IntArray(matR * matC) // 将二维数据放到一维数组中
    var backIndex = 0
    mat.forEach { intArray ->
        intArray.forEach {
            backIntArray[backIndex++] = it
        }
    }
    backIndex = 0
    for (i in 0 until r) {
        for (j in 0 until c) {
            result[i][j] = backIntArray[backIndex++]
        }
    }
    return result
}

val mat = arrayOf(intArrayOf(1, 2), intArrayOf(3, 4))
fun main() {
    matrixReshape(mat, 1, 4).forEach { intArray ->
        intArray.forEach {
            println(it)
        }
    }
}

// 167:两数之和II - 输入有序数组
fun twoSum(numbers: IntArray, target: Int): IntArray {
    val result = IntArray(2)
    val map = mutableMapOf<Int, Int>()
    numbers.forEachIndexed { index, element ->
        val temp = target - element
        if (map.containsKey(temp)) {
            result[0] = index + 1
            result[1] = map[temp]!!
            result.sort()
            return result
        }
        map[element] = index + 1
    }
    return result
}

fun reverseString(s: CharArray): Unit {
    var left = 0
    var right = s.size - 1
    while (left < right) {
        val temp = s[left]
        s[left] = s[right]
        s[right] = temp
        left++
        right--
    }
}

fun reverseWords(s: String): String {
    val list = s.split(' ').toMutableList()
    for (i in list.indices) {
        list[i] = list[i].reversed()
    }
    var result = ""
    list.forEachIndexed { index, element ->
        if (index != list.size - 1) {
            result += "$element "
        }
        result += element
    }
    return result
}
