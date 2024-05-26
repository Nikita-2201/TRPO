package comparator

import types.FractionType

class FractionComparator extends Comparator with Serializable {
  override def compare(o1: AnyRef, o2: AnyRef): Int = {
    val lcm = FractionType.lcm(o1.asInstanceOf[FractionType].getDenominatorValue, o2.asInstanceOf[FractionType].getDenominatorValue)
    val coef1 = if (o1.asInstanceOf[FractionType].getIsNegative) -1
    else 1
    val coef2 = if (o2.asInstanceOf[FractionType].getIsNegative) -1
    else 1
    val num1 = coef1 * (o1.asInstanceOf[FractionType].getIntegerPartValue * o1.asInstanceOf[FractionType].getDenominatorValue + o1.asInstanceOf[FractionType].getNumeratorValue) * (lcm / o1.asInstanceOf[FractionType].getDenominatorValue)
    val num2 = coef2 * (o2.asInstanceOf[FractionType].getIntegerPartValue * o2.asInstanceOf[FractionType].getDenominatorValue + o2.asInstanceOf[FractionType].getNumeratorValue) * (lcm / o2.asInstanceOf[FractionType].getDenominatorValue)
    Integer.compare(num1, num2)
  }
}