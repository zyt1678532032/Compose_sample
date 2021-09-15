fun reverse(x: Int): Int {
    val string = x.toString()
    var string2 = ""
    //1.正数
    if (x > 0) {
        val list = string.reversed()

        for (s in list.indices) {
            if (list[s] != '0') {
                for (i in s until list.length) {
                    string2 += list[i]
                }
                break
            }
        }
        // string2 += list[list.length -1]
        return string2.toInt()
    } else if (x < 0) {
        string2 += "-"
        val list = string.filter {
            if (it != '-') {
                return@filter true
            }
            false
        }.reversed()
        for (s in list.indices) {
            if (list[s] != '0') {
                for (i in s until list.length) {
                    string2 += list[i]
                }
                break
            }
        }
        return string2.toInt()
    }
    return 0

}

fun longestCommonPrefix(strs: Array<String>): Unit {
//    if (strs.size == 1) {
//        return ""
//    }
    // 以第一个字符串为便利对象
    var resultString = ""
    val maxStr = strs.maxOfWith({ p0, p1 ->
        if (p0.length <= p1.length) {
            -1
        } else {
            1
        }
    }
    ) {
        it
    }
    println(maxStr)
    val minStr = strs.minOfWith({ p0, p1 ->
        if (p0.length >= p1.length) {
            1
        } else {
            -1
        }
    }){
        it
    }
    println(minStr)
    // 从第一个字符开始往后面的字符串匹配
    // 如果后面和第一个字符不匹配,则匹配第二个字符,如果匹配,则以两个字符为单位继续此遍历
}
