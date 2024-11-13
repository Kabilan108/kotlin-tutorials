import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

// (val ...) -> primary constructor
// -> can be default or parameterized.
// -> primary constructor has no body
// -> only one primary constructor
// -> seems to just be for initializing properties
//
// class SmartDevice(val name: String, val category: String = "entertainment") {

// "open" makes the class extendable
open class SmartDeviceBase(val name: String, val category: String) {
    var deviceStatus = "online"
        // only allow class & subclasses to set this
        protected set
        // this is also valid:
        // protected set(value) {
        //     field = value
        // }

    // secondary constructors
    // -> multipl possble
    // -> has a body -> can include initialization logic
    // -> can have multiple; each needs to initialize the primary constructor
    // -> should always include the same args as primary constructor
    constructor(name: String, category: String = "entertainment", statusCode: Int) : this(name, category) {
        deviceStatus = when (statusCode) {
            0 -> "offline"
            1 -> "online"
            else -> "unknown"
        }
    }

    // props can also be overrided
    open val deviceType = "unknown"

    val protocol = "BLE" // immutable -> getter defined implicityly; no setter
    var type = "tv" // mutable -> getter & setter defined implicitly

    var status = "off"
        get() = field

    // "open" here allows subclasses to override this method
    open fun turnOn() {
        this.status = "on"
    }

    open fun turnOff() {
        this.status = "off"
    }

    fun info() {
        println("----------------------------")
        println("name: ${this.name} [${this.category} - ${this.type}] [status: ${this.deviceStatus}]")
        println("protocol: ${this.protocol}")
        println("status: ${this.status}")
        println("----------------------------\n")
    }
}

// create a class that implements the 'ReadWriteProperty' interface for `var types`
// Any & Int -> generic types
class RangeValidator(
    initialValue: Int,
    private val minValue: Int,
    private val maxValue: Int
) : ReadWriteProperty<Any?, Int> {
    // override the getValue and setValue methods
    // KProperty -> interface that represents a declared property and lets you access
    //              metadata on the delegated property.
    var fieldData = initialValue
    override fun getValue(thisRef: Any?, property: KProperty<*>): Int {
        return fieldData
    }
    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Int) {
        if (value in minValue..maxValue) {
            fieldData = value
        }
    }
}
    
// if `var` or `val` is not included in the constructor properties, then the arguments
// are just treated as construcotr args. they won't assigned as object props
class SmartTV(deviceName: String) : 
    SmartDeviceBase(name = deviceName, category = "entertainment") {
    override val deviceType = "TV"

    var speakerVolume = 2
        set(value) {
            if (value in 0..100) {
                field = value
            }
        }

    // delegates -> instead of defining getter and setter, use RangeValidator to manage it
    // property delegate
    private var channel by RangeValidator(2, 0, 200)
    // Delegation in Kotlin:
    // - Allows class to implement interface by delegating to another object
    // - Property delegation: delegates property's getter/setter to another object
    // - In your example, 'channel' property uses RangeValidator as delegate
    // - RangeValidator handles getter/setter, enforces value range (0-200)
    // - 'by' keyword used to specify delegate
    // var channel = 1
    //     set(value) {
    //         if (value in 0..200) {
    //             field = value
    //         }
    //     }

    fun increaseVolume() {
        speakerVolume++
        println("vol++ = $speakerVolume")
    }

    fun nextChannel() {
        channel++
        println("channel++ = $channel")
    }

    override fun turnOn() {
        super.turnOn()
        println("$name TV on")
    }

    override fun turnOff() {
        super.turnOff()
        println("$name TV off")
    }
}

class SmartLight(deviceName: String) :
    SmartDeviceBase(name = deviceName, category = "smart-home") {
    override val deviceType = "bulb"
    
    var brightnessLevel = 0
        set(value) {
            if (value in 0..100) {
                field = value
            }
        }

    fun increaseBrightness() {
        brightnessLevel++
        println("brightness++ = $brightnessLevel")
    }


    override fun turnOn() {
        super.turnOn()
        brightnessLevel = 2
        println("$name light on")
    }

    override fun turnOff() {
        super.turnOff()
        brightnessLevel = 0
        println("$name light off")
    }
}

// "public" -> default. props & methods that should be accessible outside the class
// "private" -> props & methods only accessible inside a class or source file
// "protected" -> props & methods that are "private" but should be accessible in subclasses
// "internal" -> accessible in the same module. 
// "module" -> container for app source code;; collection of source files and build settings
// "package" -> folder that groups related classes -> packages <is-a-subset-of> module

fun main() {
    // val fireStick = SmartDevice(name = "fire stick", statusCode = 1)
    // fireStick.info()
    //
    // fireStick.turnOn()
    //
    // fireStick.speakerVolume = 50
    // fireStick.info()
    //
    // fireStick.speakerVolume = 500
    // fireStick.info()
    //
    // fireStick.speakerVolume = -10
    // fireStick.info()
    //
    // fireStick.turnOff()
    // fireStick.info()

    var smartDevice: SmartDeviceBase = SmartTV("Android TV")
    smartDevice.turnOn()
    smartDevice = SmartLight("Google Light")
    smartDevice.turnOn()
}

