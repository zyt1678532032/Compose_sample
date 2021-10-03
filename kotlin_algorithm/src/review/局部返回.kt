package review

fun main() {
    test()
}

fun test(): Int {
    //
    hasInlineFunc {
        println("hasInlineFunc")
        return@hasInlineFunc
    }
    noInlineFunc {
        println("noInlineFunc")
        hasInlineFunc {

        }
    }
    // 局部返回
    func {
        if (it == 1) {
            return@func
        }
        println("action$it 执行中")
    }

    return 1
}



fun func(action: (Int) -> Unit): Int {
    println("func 开始执行")
    for (i in 0..2) {
        println("action$i 开始执行")
        action(i)
        println("action$i 执行结束")
    }
    println("func 执行结束")
    return 1
}

inline fun hasInlineFunc(action: () -> Unit) {
    action()
    // func(action) // 在action前面添加noinline
}


fun noInlineFunc(action: () -> Unit) {
    action()
}