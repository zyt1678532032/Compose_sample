fun intToRoman(num: Int): String {
    var result = ""
    // 将字符和对应数值存入map
    val map = mapOf<String, Int>(
        "M" to 1000,
        "CM" to 900,
        "D" to 500,
        "CD" to 400,
        "C" to 100,
        "XC" to 90,
        "L" to 50,
        "XL" to 40,
        "X" to 10,
        "IX" to 9,
        "V" to 5,
        "IV" to 4,
        "I" to 1,
    )
    // 取余，便利选择最大的一个
    var num_ = num
    map.forEach { (key: String, value: Int) ->
        while (num_ >= value) {
            num_ -= value
            result += key
        }
    }
    // 多行字符串
    // val string = """
    //    dsadas
    //    fsdaf
    //    fdas
    // """.trimIndent()
    return result
}

// 203. 移除链表元素中对应的元素
fun removeElements(head: ListNode?, `val`: Int): ListNode? {
    var p1 = head
    while (p1?.next != null) {
        if (p1.next?.`val` == `val`) {
            p1.next = p1.next?.next
            continue
        }
        p1 = p1.next
    }
    // 判断head节点(因为还未进行判断)
    if (head?.`val` == `val`) {
        return head.next
    }
    return head
}
