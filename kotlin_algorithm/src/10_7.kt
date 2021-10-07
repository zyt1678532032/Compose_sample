import java.util.*

// 102. 二叉树的层序遍历
fun levelOrder(root: TreeNode?): List<List<Int>> {
    val result = mutableListOf<List<Int>>()

    val indexs = mutableListOf<Int>() // 队列中元素对应的层次下标
    val queue = LinkedList<TreeNode>() // 队列
    if (root != null) {
        var index = 1
        queue += root // 入队
        indexs += index // 记录层次
        var startIndex = 0 // 下标的启动位置(和出队同步)
        while (queue.size != 0) { // 队列不为空
            val temp = mutableListOf<Int>() // 每层的集合结果
            for (i in startIndex until indexs.size) {
                // 有几个和index相等,队列就出队几次
                // if (indexs[i] == index) { 添加startIndex后，就不需要这个判断了
                val q1 = queue.removeFirst()!! // 出队
                temp += q1.`val`
                if (q1.left != null) {
                    queue += q1.left!! // 入队
                    indexs += index + 1 // 同时记录相应的层次
                }
                if (q1.right != null) {
                    queue += q1.right!!
                    indexs += index + 1
                }
                startIndex = i + 1
                // }
            }
            result += temp
            index++
        }
    }
    return result
}

//434. 字符串中的单词数
fun countSegments(s: String): Int {
    var num = 0
    var flag = false
    s.forEachIndexed { index, c ->
        if (c != ' ' && !flag) {
            flag = true
        }
        if ((c == ' ' && flag) || (flag && index == s.length - 1 && s[index] != ' ')) {
            flag = false
            num++
        }
    }
    return num
}