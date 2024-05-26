package comparator

import types.IntegerType

class IntegerComparator extends Comparator with Serializable {
  override def compare(o1: AnyRef, o2: AnyRef): Int = o1.asInstanceOf[IntegerType].getIntegerValue - o2.asInstanceOf[IntegerType].getIntegerValue
}