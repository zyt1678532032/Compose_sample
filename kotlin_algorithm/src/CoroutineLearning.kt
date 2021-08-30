import kotlinx.coroutines.*
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.suspendCoroutine

fun main() {
    val start = System.currentTimeMillis()
    runBlocking {

        // coroutineScope 这里也是阻塞代码块
        coroutineScope {

//            val coroutineScope = CoroutineScope(Job())
            // 下面的代码实际上是阻塞代码块,如果它在上面的协程创建之前调用,那么协程就没有产生相应的作用
            withContext(Dispatchers.Default) {
                delay(1000)
                println("处理完成1")
                delay(1500)
                println("处理完成2")
            }
            launch {
                delay(3500)
            }
        }
        GlobalScope.launch {
            delay(7000)
        }
    }

    val end = System.currentTimeMillis()
    println("时间:${end - start}")

    (1..5).forEach{
        println(it)
    }

}

suspend fun mySuspendFunc(block: suspend CoroutineScope.() -> Unit) {
    GlobalScope.block() // 或者block(GlobalScope)
}


fun foo() {
    println(1)
    println(2)
    println(3)
}

fun bar() {
    println(4)
    println(5)
    println(6)

}