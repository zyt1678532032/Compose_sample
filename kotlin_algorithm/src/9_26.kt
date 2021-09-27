fun firstUniqChar(s: String): Int {
    s.toCharArray().forEachIndexed loop1@{ index, c ->
        s.toCharArray().forEachIndexed loop2@{ index2, c2 ->
            if (index != index2 && c == c2) {
                return@loop1
            }
        }
        return index
    }
    return -1
}

fun canConstruct(ransomNote: String, magazine: String): Boolean {
    val magazineList = magazine.toCharArray().toMutableList()
    ransomNote.toCharArray().forEach {
        if (magazineList.contains(it)) {
            magazineList.remove(it)
        }
    }
    if (magazineList.size == 0) {
        return true
    }
    return false
}

// 杨辉三角:递归思路
fun generate(numRows: Int): List<List<Int>> {
    val result: MutableList<List<Int>> = mutableListOf()
    if (numRows == 1) {
        result += listOf(1)
    } else if (numRows == 2) {
        result += listOf(1) // 添加元素
        result += listOf(1, 1)
    } else {
        val back = mutableListOf(1)
        var i = 0
        //
        val element: List<List<Int>> = generate(numRows - 1)
        val list = element[numRows - 2]
        while (i < list.size - 1) {
            back += list[i] + list[i + 1]
            i++
        }
        back.add(1)
        result += element
        result += back
    }
    return result
}

fun main() {
    generate(5).forEach { list ->
        println(list)
    }
}