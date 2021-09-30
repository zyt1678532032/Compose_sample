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
        return false
    }
    if (top == -1) {
        return true
    }
    return false
}