package types

import java.io.Serializable

class FractionType(isNegative: Boolean, integerPartValue: Int, numeratorValue: Int, denominatorValue: Int) :
    Serializable {
    private var integerPartValue: Int
    private var numeratorValue: Int
    private var denominatorValue: Int
    var isNegative: Boolean

    init {
        if (integerPartValue < 0 || numeratorValue < 0 || denominatorValue < 1) {
            throw InstantiationException("Cannot be instantiated from this values!")
        }
        this.isNegative = isNegative
        this.integerPartValue = integerPartValue
        this.numeratorValue = numeratorValue
        this.denominatorValue = denominatorValue
        simplify()
    }

    private fun simplify() {
        val gcd = gcd(numeratorValue, denominatorValue)
        numeratorValue /= gcd
        denominatorValue /= gcd
        if (numeratorValue >= denominatorValue) {
            integerPartValue += numeratorValue / denominatorValue
            numeratorValue %= denominatorValue
        }
    }

    fun getIntegerPartValue(): Int {
        return integerPartValue
    }

    fun getNumeratorValue(): Int {
        return numeratorValue
    }

    fun getDenominatorValue(): Int {
        return denominatorValue
    }

    fun getIsNegative(): Boolean {
        return isNegative
    }

    @Throws(InstantiationException::class)
    fun setIntegerPartValue(integerPartValue: Int) {
        if (integerPartValue < 0) {
            throw InstantiationException("Cannot be instantiated from this values!")
        }
        this.integerPartValue = integerPartValue
        simplify()
    }

    @Throws(InstantiationException::class)
    fun setNumeratorValue(numeratorValue: Int) {
        if (numeratorValue < 0) {
            throw InstantiationException("Cannot be instantiated from this values!")
        }
        this.numeratorValue = numeratorValue
        simplify()
    }

    @Throws(InstantiationException::class)
    fun setDenominatorValue(denominatorValue: Int) {
        if (denominatorValue < 1) {
            throw InstantiationException("Cannot be instantiated from this values!")
        }
        this.denominatorValue = denominatorValue
        simplify()
    }

    override fun toString(): String {
        return (if (isNegative) "-" else "") + integerPartValue + "." + numeratorValue + "/" + denominatorValue
    }

    companion object {
        fun gcd(a: Int, b: Int): Int {
            var a = a
            var b = b
            while (b != 0) {
                val temp = b
                b = a % b
                a = temp
            }
            return a
        }

        fun lcm(a: Int, b: Int): Int {
            return a * b / gcd(a, b)
        }
    }
}
