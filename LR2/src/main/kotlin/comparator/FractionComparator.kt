package comparator

import types.FractionType
import types.FractionType.Companion.lcm
import java.io.Serializable

class FractionComparator : Comparator, Serializable {
    override fun compare(o1: Any, o2: Any): Int {
        val lcm = lcm((o1 as FractionType).getDenominatorValue(), (o2 as FractionType).getDenominatorValue())
        val coef1 = if (o1.getIsNegative()) -1 else 1
        val coef2 = if (o2.getIsNegative()) -1 else 1
        val num1 = coef1 * (o1.getIntegerPartValue() * o1.getDenominatorValue() +
                o1.getNumeratorValue()) * (lcm / o1.getDenominatorValue())
        val num2 = coef2 * (o2.getIntegerPartValue() * o2.getDenominatorValue() +
                o2.getNumeratorValue()) * (lcm / o2.getDenominatorValue())
        return num1.compareTo(num2)
    }
}
