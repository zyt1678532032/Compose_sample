import java.util.*
import java.util.concurrent.Flow
import javax.lang.model.type.NullType

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

class ListNode(var `val`: Int) {
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

// 合并两个有序链表
fun mergeTwoLists(l1: ListNode?, l2: ListNode?): ListNode? {
    if (l1 == null) {
        return l2
    }
    if (l2 == null) {
        return l1
    }
    // 都不为空
    var p1: ListNode?
    var p2: ListNode?
    var result: ListNode
    val node: ListNode
    if (l1.`val` > l2.`val`) {
        p1 = l1
        p2 = l2.next
        result = ListNode(l2.`val`)
        node = result
    }else{
        p1 = l1.next
        p2 = l2
        result = ListNode(l1.`val`)
        node = result
    }
    result.next = null

    while (p1 != null && p2 != null) {
        if (p1.`val` > p2.`val`) {
            result.next = ListNode(p2.`val`)
            p2 = p2.next
            result = result.next!!
        }else{
            result.next = ListNode(p1.`val`)
            p1 = p1.next
            result = result.next!!
        }
    }
    if (p1 != null) {
        result.next = p1
    }
    if (p2 != null) {
        result.next = p2
    }
    return node
}

// 203. 移除链表元素中对应的元素
// fun removeElements(head: ListNode?, `val`: Int): ListNode? {
//     // 空间换时间
//     var p1 = head
//     var p2: ListNode? = null
//     var result: ListNode? = null
//     var flag = 0
//     while (p1 != null) {
//         if (p1.`val` != `val` ) {
//             if (flag == 0) {
//                 p2 = ListNode(p1.`val`)
//                 result = p2
//                 flag = -1
//             }else{
//                 p2?.next = ListNode(p1.`val`)
//                 p2 = p2?.next
//             }
//         }
//         p1 = p1.next
//     }
//     return result
// }
