// 206. 反转链表
fun reverseList(head: ListNode?): ListNode? {
    // 把值记下来
    val values = mutableListOf<Int>()
    var p = head
    while (p != null) {
        values += p.`val`
        p = p.next
    }
    // 具体实现
    p = null
    var result: ListNode? = null
    values.apply {
        reverse() // 翻转当前List中的数据
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

// 83. 删除排序链表中的重复元素
fun deleteDuplicates(head: ListNode?): ListNode? {
    // 遍历,将不重复的元素放进新链表中
    if (head?.next == null) {
        return head
    }
    // 至少两个元素
    val result = ListNode(head.`val`)
    var p1 = head // 遍历参数链表的指针
    var p2 = result // 遍历最后结果的指针
    while (p1 != null) {
        if (p1.`val` != p2.`val`) {
            p2.next = ListNode(p1.`val`)
            p2 = p2.next!!
        }
        p1 = p1.next
    }
    return result
}