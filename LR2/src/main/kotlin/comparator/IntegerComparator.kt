package comparator

import types.IntegerType
import java.io.Serializable

class IntegerComparator : Comparator, Serializable {
    override fun compare(o1: Any, o2: Any): Int {
        return (o1 as IntegerType).getIntegerValue() - (o2 as IntegerType).getIntegerValue()
    }
}
