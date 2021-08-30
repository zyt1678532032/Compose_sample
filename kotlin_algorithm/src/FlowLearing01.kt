import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.util.HashMap


fun main() {
    println(People_("zyt") + People_("shx"))
}

fun twoSum(nums: IntArray, target: Int): IntArray {
    val intArray = IntArray(2)
    val map = mutableMapOf<Int, Int>()
    nums.forEach { element ->
        if (map.containsKey(element)) {
            intArray[0] = nums.indexOf(element)
            intArray[1] = map[element]!!
            return intArray
        }
        map[target - element] = nums.indexOf(element)
    }
    return intArray
}

class People_(val name: String) {

    operator fun plus(people: People_): String {
        return "$name + ${people.name}"
    }

}

class A{
    val name: String? = null

    fun funcOfA(block: B.() -> Unit) {
        block(B())
    }
    fun testFunc() {
        funcOfA {
            // 调用指定实例
            // 因为当前this指向的是class B
            // 为了区分,所以使用this@A
            this@A.name
        }
    }
}
class B