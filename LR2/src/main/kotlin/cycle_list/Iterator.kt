package cycle_list

import java.io.IOException

interface Iterator {
    @Throws(IOException::class)
    fun toDo(data: Any?)
}
