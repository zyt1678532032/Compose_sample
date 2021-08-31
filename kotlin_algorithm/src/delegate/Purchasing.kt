package delegate

import kotlin.reflect.KProperty

/**
 * 代购(代理)
 * 由代购帮People购买手机,People只需要提money即可
 */
class Purchasing(val money: Float) {

    operator fun getValue(people: People, property: KProperty<*>): Any? {
        var phone: Phone? = null
        val intArrayOf = intArrayOf()
        phones.forEach {
            if (money == it.price){
                phone = it
            }
        }
        return phone
    }

}

data class Phone(
    val price: Float,
    val name: String
)

val phones = listOfNotNull(
    Phone(1932.89f,"Pixel 4a"),
    Phone(3932.89f,"Pixel 5"),
    Phone(4932.89f,"Pixel 5a"),
    Phone(932.89f,"Pixel 4"),
)