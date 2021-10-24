// 638. 大礼包
val price = listOf(2, 5)
val special = listOf<List<Int>>(listOf(3, 0, 5), listOf(1, 2, 10))
val needs = listOf(3, 2)
fun shoppingOffers(price: List<Int>, special: List<List<Int>>, needs: List<Int>): Int {
    fun shopping(special: List<List<Int>>, needs: List<Int>): Int {
        // 如果needs中已经全为空
        if (needs.maxOrNull() == 0) {
            println(needs.toTypedArray().contentToString())
            return 0
        }
        // 过滤掉某礼包的某一项大于相应needs的某一项的礼包
        val filterSpecial = special.filter {
            for (i in 0 until it.size -1) { // 不包括最后一个
                if (it[i] > needs[i]) {
                    return@filter false
                }
            }
            return@filter true
        }
        // 如果过滤后为空，那么就单个买
        if (filterSpecial.isEmpty()) {
            var sum = 0
            for (i in price.indices) {
                sum += price[i] * needs[i]
            }
            return sum
        }
        val result = mutableListOf<Int>()
        for (list in filterSpecial) {
            val needs2 = needs.toMutableList()
            for (i in needs2.indices) {
                needs2[i] = needs2[i] - list[i]
            }
            result += list.last() + shopping(filterSpecial, needs2)
        }
        return result.minOrNull() ?: 0
    }
    // 过滤大礼包价格大于单个买的,也就是大礼包不合理并不优惠
    val specialAfter = special.filter {
        var price1 = 0
        for (i in price.indices) {
            price1 += it[i] * price[i]
        }
        if (it.last() > price1) {
            return@filter false
        }
        return@filter true
    }
    return shopping(specialAfter, needs)
}

fun main() {
    println(shoppingOffers(price, special, needs))
    // val listOf = listOf(1, 23, 12)
    // listOf.forEachIndexed { index, i ->
    //     i - 2
    // }
    // listOf.forEach {
    //     println(it)
    // }
}
