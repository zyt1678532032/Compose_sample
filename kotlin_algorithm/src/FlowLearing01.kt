fun main() {
    // println(People_("zyt") + People_("shx"))
    // Map: key值唯一,下面最后一个Apple会覆盖前面key相同的value
    // val mapOf = mapOf("Apple" to 1, "Orange" to 2, "Apple" to 3)
    val mapOf = mapOf("Apple" to 3, "Orange" to 2, "Apple2" to 3)
    val maxOfWithOrNull = mapOf.maxOfOrNull {
        it.key.length
    }
    mapOf.forEach{
        println(it.value)
    }
    println(mapOf.count {
        // 输出value等于3的元素有多少
        if (it.value == 3) {
            return@count true
        }
        return@count false
    })
    println(maxOfWithOrNull)
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

class A {
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