import java.lang.RuntimeException
import java.util.*

fun main() {
    val s1 = "ab"
    val s2 = "eidbaooo"
    println(checkInclusion(s1, s2))

}

// 567. 字符串的排列
fun checkInclusion(s1: String, s2: String): Boolean {
    if (s1.length > s2.length) {
        return false
    }
    s2.forEachIndexed { index, c ->
        if (index + s1.length <= s2.length) {
            if (checkInclusion2(s1, s2.slice(index until index + s1.length))) {
                return true
            }
            return@forEachIndexed
        }
        return false
    }
    return false
}

fun checkInclusion2(s1: String, s2: String): Boolean {
    val indexArray = IntArray(s2.length) {
        1
    }
    s1.forEach {
        s2.forEachIndexed { index, c ->
            if (c == it && indexArray[index] == 1) {
                indexArray[index] = 0
                return@forEach
            }
        }
        return false
    }
    return true
}

// 232. 用栈实现队列
class MyQueue() {

    private val stackPush: Stack<Int> = Stack()
    private val stackPop: Stack<Int> = Stack()

    // 入队
    fun push(x: Int) {
        stackPush.push(x)
    }

    // 出队
    fun pop(): Int {
        if (stackPop.size != 0) {
           return stackPop.pop()
        }
        // 将stackPush中的元素倒入stackPop
        while (stackPush.size != 0) {
            stackPop.push(stackPush.pop())
        }
        if (stackPop.size == 0) {
            throw RuntimeException("队列为空!")
        }
        return stackPop.pop()
    }

    // 返回队头元素
    fun peek(): Int {
        if (stackPop.size != 0) {
            return stackPop.peek()
        }
        while (stackPush.size != 0) {
            stackPop.push(stackPush.pop())
        }
        if (stackPop.size == 0) {
            throw RuntimeException("队列为空!")
        }
        return stackPop.peek()
    }

    // 队列是否为空
    fun empty(): Boolean {
        if (stackPop.size == 0 && stackPush.size == 0) {
            return true
        }
        return false
    }

}


