fun main() {
//    println(TestJvmField().name)
//    func1(arrayOf("zyt", "shxx"))
//    testSealedClass(MyColor.Red)
    println(containsDuplicate(ints))
}

class TestJvmField {
    @JvmField
    val name = "zyt"
}

// Array ---> vararg
fun func1(args: Array<String>) {
    funcVararg(*args)
}

fun funcVararg(vararg args: String) {
    args.forEach {
        println(it)
    }
}

fun testSealedClass(color: MyColor) {
    when(color){
        is MyColor.Yellow -> println("MyColor.Yellow")
        is MyColor.Red -> println("MyColor.Red")
        is MyColor.Black -> println("MyColor.Black")
    }
}

// 枚举类也可以
sealed class MyColor {
    object Yellow : MyColor()

    object Red : MyColor()

    object Black : MyColor()
}
// 判断数组中是否存在重复元素
fun containsDuplicate(nums: IntArray): Boolean {
    if (nums.size == 1) {
        return false
    }
    for (i in nums.indices) {
        val flag = nums[i]
        for (j in i+1 until nums.size) {
            if (nums[j] == flag) {
                return true
            }
        }
    }
    return false
}
val ints = intArrayOf(7,3,2,1,2)

