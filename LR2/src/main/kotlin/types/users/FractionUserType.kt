package types.users

import comparator.Comparator
import comparator.FractionComparator
import types.FractionType
import java.io.IOException
import java.io.InputStreamReader
import java.util.*

class FractionUserType : UserType {
    override fun typeName(): String {
        return "Fraction"
    }

    @Throws(InstantiationException::class)
    override fun create(): Any {
        val random = Random()
        val isNegative = random.nextBoolean()
        val partInteger = random.nextInt(MAX_INTEGER_PART - MIN_INTEGER_PART) + MIN_INTEGER_PART
        val numerator = random.nextInt(MAX_NUMERATOR - MIN_NUMERATOR) + MIN_NUMERATOR
        val denominator = random.nextInt(MAX_DENOMINATOR - MIN_DENOMINATOR) + MIN_DENOMINATOR
        return FractionType(isNegative, partInteger, numerator, denominator)
    }

    @Throws(InstantiationException::class)
    override fun clone(`object`: Any): Any {
        return FractionType(
            (`object` as FractionType).getIsNegative(),
            `object`.getIntegerPartValue(),
            `object`.getNumeratorValue(),
            `object`.getDenominatorValue()
        )
    }

    override fun readValue(`in`: InputStreamReader?): Any {
        return try {
            `in`!!.read()
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    @Throws(InstantiationException::class)
    override fun parseValue(ss: String): Any? {
        val parts = ss.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        var left: Int? = null
        var mid: Int? = null
        var right: Int? = null
        var isNegative = false
        try {
            val leftMid = parts[0].split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            left = leftMid[0].toInt()
            mid = leftMid[1].toInt()
            right = parts[1].toInt()
        } catch (e: NumberFormatException) {
            println("Error: Unable to parse the input.")
        } catch (e: ArrayIndexOutOfBoundsException) {
            println("Error: Unable to parse the input.")
        }
        return if (left != null && mid != null && right != null) {
            if (left < 0) {
                isNegative = true
                left = -left
            }
            FractionType(isNegative, left, mid, right)
        } else {
            null
        }
    }

    override val typeComparator: Comparator
        get() = FractionComparator()

    override fun toString(`object`: Any): String {
        return `object`.toString()
    }

    companion object {
        private const val MIN_INTEGER_PART = 0
        private const val MAX_INTEGER_PART = 10
        private const val MIN_NUMERATOR = 0
        private const val MAX_NUMERATOR = 1000
        private const val MIN_DENOMINATOR = 1
        private const val MAX_DENOMINATOR = 1000
    }
}
