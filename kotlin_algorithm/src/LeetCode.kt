import kotlin.math.pow
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun main() {
    val listNode = ListNode(2)
    val listNode1 = ListNode(4)
    val listNode2 = ListNode(3)
    listNode.next = listNode1
    listNode1.next = listNode2

    val listNode3 = ListNode(5)
    val listNode4 = ListNode(6)
    val listNode5 = ListNode(4)
    listNode3.next = listNode4
    listNode4.next = listNode5

//    var node = addTwoNumbers(listNode, listNode3)
//    while (node != null) {
//        println(node.`val`)
//        node = node.next
//    }
    val genNode = genNode(9, 9, 9, 9, 9, 9, 9)
    val genNode2 = genNode(9, 9, 9, 9)
    var node = addTwoNumbers(genNode, genNode2)
    while (node != null) {
        println(node.`val`)
        node = node.next
    }

}


// kotlin 中所有参数都为final(不可变)
fun addTwoNumbers(l1: ListNode?, l2: ListNode?): ListNode? {
    var l1_ = l1
    var l2_ = l2
    val root = ListNode(0)
    var cursor = root
    var carry = 0
    while (l1_ != null || l2_ != null || carry != 0) {
        val l1Val = l1_?.`val` ?: 0
        val l2Val = l2_?.`val` ?: 0
        val sumVal = l1Val + l2Val + carry
        carry = sumVal / 10
        val sumNode = ListNode(sumVal % 10)
        cursor.next = sumNode
        cursor = sumNode
        if (l1_ != null) l1_ = l1_.next
        if (l2_ != null) l2_ = l2_.next
    }
    return root.next
}


fun genNode(vararg param: Int): ListNode {
    val firstNode = ListNode(param[0])
    var node = firstNode
    val iterator = param.iterator()
    while (iterator.hasNext()) {
        val element = ListNode(iterator.next())
        node.next = element
        node = element
    }
    val a = 2
    val b = 3
    return firstNode
}

