import kotlin.math.max

fun main() {
//    println("babab".subSequence(0, 2))
}

// 最长公共子串: 一个字符串数组中的最长公共子串
fun longestCommonPrefix(strs: Array<String>): String {
    var s = strs[0]
    for (i in strs.indices){
        while(!strs[i].startsWith(s)){
            if (s.isEmpty()) return ""
            s = s.substring(0,s.length - 1)
        }
    }
    return s
}