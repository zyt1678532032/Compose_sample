fun main() {
    val people = People()
    val worker = Worker("zyt")
    people.funOfPeople(worker)
    fun1 {
        println("block")
        return
    }
    Runnable {
        return@Runnable
    }
}

open class People(
    open val name: String = "People" // 当声明为private时,就表明该属性不可被覆盖
) {

    fun funOfPeople(people: People) {
        println(people.javaClass)
        println(people.name)
    }

    fun fun2OfPeople() {
        println(this.name)
    }
}

class Worker(
    override val name: String
) : People()





inline fun fun1(block: () -> Unit) {
    block()
    // 如果需要将函数类型在其他的lambda语句块中使用,就添加crossinline
//    fun2 {
//        block()
//    }
    // 如果需要使用函数类型作为参数,就使用noinline
//    fun2(block)
    println("fun1")
}

fun fun2(block: () -> Unit) {

}
