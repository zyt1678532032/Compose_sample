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

