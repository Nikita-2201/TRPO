package comparator

trait Comparator {
  def compare(o1: AnyRef, o2: AnyRef): Int
}