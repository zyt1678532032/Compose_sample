import java.util.*
import kotlin.math.max

// 20. 有效的括号(栈
fun isValid(s: String): Boolean {
    val stack = CharArray(s.length)
    var top = -1
    // 左括号入栈
    // 有括号和栈顶对比，如果是一类的，出栈，否则，入栈
    s.forEach {
        if (it == '(' || it == '[' || it == '{') {
            stack[++top] = it
            return@forEach
        }
        if (top >= 0) {
            if (it == ')' && stack[top] == '(') {
                top--
                return@forEach
            }
            if (it == ']' && stack[top] == '[') {
                top--
                return@forEach
            }
            if (it == '}' && stack[top] == '{') {
                top--
                return@forEach
            }
        }
        // 下面的情况就是: 栈空和右括号
        return false
    }
    if (top == -1) {
        return true
    }
    return false
}

class TreeNode(var `val`: Int) {
    var left: TreeNode? = null
    var right: TreeNode? = null
}

// 144. 二叉树的前序遍历
fun preorderTraversal(root: TreeNode?): List<Int> {
    return if (root == null) {
        listOf()
    } else if (root.left == null && root.right == null) {
        listOf(root.`val`)
    } else  {
        val result = mutableListOf<Int>(root.`val`)
        result += preorderTraversal(root.left)
        result += preorderTraversal(root.right)
        result
    }
}

