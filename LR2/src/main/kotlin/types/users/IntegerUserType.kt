package types.users

import comparator.Comparator
import comparator.IntegerComparator
import types.IntegerType
import java.io.IOException
import java.io.InputStreamReader
import java.util.*

class IntegerUserType : UserType {
    private val MAX = 1000
    private val MIN = -1000
    override fun typeName(): String {
        return "Integer"
    }

    @Throws(InstantiationException::class)
    override fun create(): Any {
        val random = Random()
        val value = random.nextInt(MAX - MIN) + MIN
        return IntegerType(value)
    }

    @Throws(InstantiationException::class)
    override fun clone(`object`: Any): Any {
        return IntegerType((`object` as IntegerType).getIntegerValue())
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
        return try {
            IntegerType(ss.toInt())
        } catch (e: NumberFormatException) {
            null
        }
    }

    override val typeComparator: Comparator
        get() = IntegerComparator()

    override fun toString(`object`: Any): String {
        return `object`.toString()
    }
}
