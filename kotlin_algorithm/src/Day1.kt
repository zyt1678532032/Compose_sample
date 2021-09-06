/**
 *
题目：写一个字符串转换函数
输入：以驼峰命名法为格式的字符串，
输出：相应的以下划线命名法为格式的字符串

要求：
1. 只遍历输入字符串一遍
2. 注意合理处理连续的大写字母如 HTTP
3. 请提供单元测试，不局限于下面的例子，尽量覆盖全。注意：想测试用例是这个题目的一部分
4. 语言不限

例子 1
输入：myFirstName
输出：my_first_name

例子 2
输入：OnlineUsers
输出：online_users

例子 3
输入：Address
输出：address

例子 4
输入：validHTTPRequest
输出：valid_http_request
 */

const val test1 = "myFirstName" // my_first_name
const val test2 = "OnlineUsers" // online_users
const val test3 = "Address" // address
const val test4 = "validHTTPRequest" // valid_http_request

const val test5 = "validHTTPRequestTEST" // valid_http_request_test
const val test6 = "ValidHTTPMyRequest" // valid_http_my_request
const val test7 = "ValidHTTPOfMyRequest" // valid_http_of_my_request
const val test8 = "ValidHTTPOfMyRequestT" // valid_http_of_my_request_t
const val test9 = "AgeHaE" // age_ha_e

fun changeString(string: String) {
    if (string.isNotEmpty()) {

        var stringChanged = string[0].lowercase() // 直接将第一个转化为小写
        /**
         * 转换规则:
         *  1.当遍历到的字母为大写时: l:表示小写, u:表示大写
         *      1.需要添加"_":
         *          lul,luu;
         *          lul,uul
         *      2.其他直接转化为小写
         */
        for (i in 1 until string.length) {
            // 大写时进行判断
            if (string[i].isUpperCase()) {
                if (i + 1 >= string.length && string[i - 1].isLowerCase()) { // 最后一个,它的前一个如果为小写,则添加_
                    stringChanged += "_${string[i].lowercase()}"
                } else if (i + 1 >= string.length && string[i - 1].isUpperCase()) {// 最后一个
                    stringChanged += string[i].lowercase()
                } else {
                    // 不是最后一个
                    when {
                        //lu: lul,luu
                        string[i - 1].isLowerCase() -> {
                            stringChanged += "_${string[i].lowercase()}"
                        }
                        //ul: lul,uul
                        string[i + 1].isLowerCase() -> {
                            stringChanged += "_${string[i].lowercase()}"
                        }
                        else -> {
                            // uuu
                            stringChanged += string[i].lowercase()
                        }
                    }
                }
            } else {
                stringChanged += string[i]
            }
        }
        println(stringChanged)
    } else {
        println("输入的字符串不能为空")
    }

}

fun main() {
    changeString(test1)
    changeString(test2)
    changeString(test3)
    changeString(test4)
    changeString(test5)
    changeString(test6)
    changeString(test7)
    changeString(test8)
    changeString(test9)
    changeString("")
}