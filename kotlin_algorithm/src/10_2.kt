import kotlin.math.max

// 102. 二叉树的高度
fun maxDepth(root: TreeNode?): Int {
    return if (root == null) {
        0
    }else{
        max(maxDepth(root.left),maxDepth(root.right)) + 1
    }
}