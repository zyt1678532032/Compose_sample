import java.util.*

// 102. 二叉树的层序遍历
fun levelOrder(root: TreeNode?): List<List<Int>> {
    val result = mutableListOf<List<Int>>()
    val indexs = mutableListOf<Int>()
    val queue = LinkedList<TreeNode>()
    if (root != null) {
        var index = 1
        queue += root // 入队
        indexs += index // 记录层次
        while (queue.size != 0) { // 队列不为空
            val temp = mutableListOf<Int>()
            for (i in indexs.indices) {
                // 有几个和index相等,队列就出队几次
                if (indexs[i] == index) {
                    val flag = index + 1
                    val q1 = queue.removeFirst()!!
                    temp += q1.`val`
                    if (q1.left != null) {
                        queue += q1.left!!
                        indexs += flag
                    }
                    if (q1.right != null) {
                        queue += q1.right!!
                        indexs += flag
                    }
                }
            }
            result += temp
            index++
        }
    }
    return result
}