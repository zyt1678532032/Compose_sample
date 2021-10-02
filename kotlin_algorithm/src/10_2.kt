import kotlin.math.max

fun main() {
    val s = "abcdhua"
    println(s.slice(0..0))
    println(checkIsRepeat(s))
    println(lengthOfLongestSubstring(s))
}

// 102. 二叉树的高度
fun maxDepth(root: TreeNode?): Int {

    return if (root == null) {
        0
    } else {
        max(maxDepth(root.left), maxDepth(root.right)) + 1
    }
}

// 3. 无重复字符的最长子串(滑动窗口)  复杂度必须为O(n)
fun lengthOfLongestSubstring(s: String): Int {

    // 1.窗口大小从2开始,判断窗口内的字符是否重复,不重复窗口大小加以一
    // 在从当前字符判断窗口内的字符是否重复，重复往下遍历，不重复则重复上面步骤
    var windowSize = 0
    s.forEachIndexed { index, c ->
        if (index + windowSize != s.length) {
            // 当窗口内的元素存在重复元素时跳出循环
            while (!checkIsRepeat(s.slice(index..index + windowSize))) {
                windowSize++
                if (index + windowSize == s.length) {
                    return windowSize
                }
            }
            return@forEachIndexed
        }
        return windowSize
    }
    return windowSize
}

fun checkIsRepeat(string: String): Boolean {// 判断当前字符串中是否含有重复字符
    string.forEach { c1 ->
        var index = 0
        string.forEach { c2 ->
            if (c1 == c2) {
                index++
            }
            if (index == 2) {
                return true
            }
        }
    }
    return false
}
