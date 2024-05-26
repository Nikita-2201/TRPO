package cycle_list

import java.io.IOException

trait Iterator {
  @throws[IOException]
  def toDo(data: AnyRef): Unit
}