package 位运算

// 318.最大单词长度乘积
fun maxProduct(words: Array<String>): Int {
    val masks = IntArray(words.size)
    words.forEachIndexed { index, word ->
        word.forEach {
            // todo: 状态压缩 利用int 32位来进行对每个字符串中出现a-z的字符进行存储
            masks[index] = masks[index] or (1 shl (it - 'a'))
        }
    }
    var result = 0
    for (i in masks.indices) {
        for (j in 1 until masks.size) {
            if (masks[i] and masks[j] == 0) {
                val length = words[i].length * words[j].length
                result = if (result < length) length else result
            }
        }
    }
    return result
}