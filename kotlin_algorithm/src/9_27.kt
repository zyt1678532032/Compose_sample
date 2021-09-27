import java.util.*
import java.util.concurrent.Flow

// 1.双层循环
fun isAnagram(s: String, t: String): Boolean {
    val sArray = s.toCharArray()
    val tArray = t.toCharArray()
    for (i in sArray.indices) {
        for (j in tArray.indices) {
            if (sArray[i] == tArray[j]) {
                sArray[i] = '0'
                tArray[j] = '0'
                break
            }
        }
        if (sArray[i] != '0') {
            return false
        }
    }
    tArray.forEach {
        if (it != '0') {
            return false
        }
    }
    return true
}

// 2.建立索引
fun isAnagram2(s: String, t: String): Boolean {
    val sArray = s.toCharArray()
    val tArray = t.toCharArray()
    sArray.sort()
    tArray.sort()
    if (sArray.contentEquals(tArray)) {
        return true
    }
    return false
}

class ListNode(var value: Int?) {
    var next: ListNode? = null
}

// 删除链表的中间节点
fun middleNode(head: ListNode?): ListNode? {
    var p = head
    var length = 0
    while (p != null) {
        length++
        p = p.next
    }
    var i = 0
    var q = head
    while (i < length / 2) {
        i++
        q = q!!.next
    }
    return q

}

// 19. 删除链表的倒数第 N 个结点,并返回头节点
fun removeNthFromEnd(head: ListNode?, n: Int): ListNode? {
    var p = head
    // Int:第几个节点,ListNode:指针
    val dict = mutableMapOf<Int, ListNode>()
    var index = 0
    while (p != null) {
        index++
        // 建立索引
        dict[index] = p
        p = p.next
    }
    if (index == 1) { // 长度为一
        return null
    }
    // 下面就是长度不为一
    if (n == 1) { // 删除最后一个
        dict[index - n]!!.next = null
        return head
    }
    if (index - n + 1 == 1) { // 删除第一个
        dict[1]!!.next = null
        return dict[2]
    }
    dict[index - n]!!.next = dict[index - n + 1]!!.next
    dict[index - n + 1]!!.next = null
    return head
}


fun mergeTwoLists(l1: ListNode?, l2: ListNode?): ListNode? {
    if (l1 == null) {
        return l2
    }
    if (l2 == null) {
        return l1
    }
    // 以l1为基准
    var p1 = l1
    var p2 = l2
    // 选择最小的值
    var result = ListNode(0)
    var p: ListNode = result
    if (p1.value!! > p2.value!!){
        result = ListNode(p2.value)
        p2 = p2.next
    }else{
        result = ListNode(p1.value)
        p1 = p1.next
    }
    while (p1 != null && p2 != null) {
        if (p1.value!! > p2.value!!) {
            p.next = ListNode(p2.value)
            p = p.next!!
            p2 = p2.next
        }else{
            p.next = ListNode(p1.value)
            p = p.next!!
            p1 = p1.next
        }
    }
    if (p1 != null) {
        p.next = p1
    }
    if (p2 != null) {
        p.next = p2
    }
    return result


}