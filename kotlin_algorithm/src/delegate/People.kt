package delegate

// 人买国外手机
class People(
    val name: String,
    val age: Int,
    val sex: String,
){
    // 代购人
    val phone by Purchasing(1932.89f)

}

fun main() {
    println((People("张彦通", 23, "男").phone as Phone).name)
}
