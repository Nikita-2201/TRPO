package types.users

import comparator.Comparator
import java.io.IOException
import java.io.InputStreamReader

interface UserType {
    fun typeName(): String

    @Throws(InstantiationException::class)
    fun create(): Any

    @Throws(InstantiationException::class)
    fun clone(`object`: Any): Any

    @Throws(IOException::class)
    fun readValue(`in`: InputStreamReader?): Any

    @Throws(InstantiationException::class)
    fun parseValue(ss: String): Any?
    val typeComparator: Comparator
    fun toString(`object`: Any): String
}
