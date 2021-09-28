//141. 环形链表(快慢指针)
fun hasCycle(head: ListNode?): Boolean {
    var fast = head // 每次走两部
    var slow = head // 每次走一步
    while (fast?.next?.next != null){
        slow = slow?.next
        fast = fast.next?.next
        if (slow == fast) {
            return true
        }
    }
    return false


}
