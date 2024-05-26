package types

import java.io.Serializable

class IntegerType(private var integerValue: Int) : Serializable {

    fun getIntegerValue(): Int {
        return integerValue
    }

    fun setIntegerValue(integerValue: Int) {
        this.integerValue = integerValue
    }

    override fun toString(): String {
        return integerValue.toString()
    }
}
