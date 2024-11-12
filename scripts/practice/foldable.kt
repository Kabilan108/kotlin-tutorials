open class Phone(var isScreenLightOn: Boolean = false){
    open fun switchOn() {
        isScreenLightOn = true
    }
    
    open fun switchOff() {
        isScreenLightOn = false
    }
    
    fun checkPhoneScreenLight() {
        val phoneScreenLight = if (isScreenLightOn) "on" else "off"
        println("The phone screen's light is $phoneScreenLight.")
    }
}

class FoldablePhone(isScreenLightOn: Boolean = false) :
    Phone(isScreenLightOn = isScreenLightOn) {
    private var folded: Boolean = true

    override fun switchOn() {
        if (!folded) super.switchOn()
    }

    fun open() {
        folded = false
    }

    fun close() {
        folded = true
        if (this.isScreenLightOn) super.switchOff()
    }
}

fun main() {
    val phone = Phone()
    val foldable = FoldablePhone()
    
    println("testing phone")
    phone.checkPhoneScreenLight()
    phone.switchOn()
    phone.checkPhoneScreenLight()
    phone.switchOff()
    phone.checkPhoneScreenLight()

    println("testing phone")
    foldable.checkPhoneScreenLight()
    foldable.switchOn()
    foldable.checkPhoneScreenLight()
    foldable.open()
    foldable.switchOn()
    foldable.checkPhoneScreenLight()
    foldable.close()
    foldable.checkPhoneScreenLight()
}
