// 206. 反转链表
fun reverseList(head: ListNode?): ListNode? {
    // 把值记下来
    val values = mutableListOf<Int>()
    var p = head
    while (p != null) {
        values += p.`val`
        p = p.next
    }
    p = null
    var result: ListNode? = null
    values.apply {
        reverse()
        forEachIndexed { index, ele ->
            if (index == 0) {
                result = ListNode(ele)
                p = result
                return@forEachIndexed
            }
            p?.next = ListNode(ele)
            p = p?.next
        }
    }
    return result
}