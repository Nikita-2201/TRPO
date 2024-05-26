package testing

import cycle_list.CycleList
import cycle_list.Iterator
import factory.UserFactory
import types.users.UserType
import java.io.IOException

class Testing {
    private var userFactory: UserFactory? = null
    private var userType: UserType? = null
    private var cycleList: CycleList? = null

    @Throws(IOException::class, InstantiationException::class)
    fun testIntegerType() {
        test("Integer", INTEGER_FILE_SAVE)
    }

    @Throws(IOException::class, InstantiationException::class)
    fun testFractionType() {
        test("Fraction", FRACTION_FILE_SAVE)
    }

    @Throws(InstantiationException::class, IOException::class)
    private fun test(typeUser: String, saveFile: String) {
        userFactory = UserFactory()
        System.out.printf("\n%s test:\n", typeUser)
        userType = userFactory!!.getBuilderByName(typeUser)
        cycleList = CycleList()
        cycleList!!.add(userType!!.create())
        cycleList!!.add(userType!!.create())
        cycleList!!.add(userType!!.create())
        cycleList!!.add(userType!!.create())
        cycleList!!.add(userType!!.create())
        cycleList!!.add(userType!!.create())
        cycleList!!.add(userType!!.create())
        cycleList!!.add(userType!!.create())
        cycleList!!.add(userType!!.create())
        cycleList!!.add(userType!!.create())
        cycleList!!.printList()
        println("Saving file:")
        try {
            cycleList!!.save(userType!!, saveFile)
            println("Saved")
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
        println("\nGet node by index 6, 2, 4, 3:")
        println("6) = " + cycleList!!.getByIndex(6).toString())
        println("2) = " + cycleList!!.getByIndex(2).toString())
        println("4) = " + cycleList!!.getByIndex(4).toString())
        println("3) = " + cycleList!!.getByIndex(3).toString())
        println("\nDelete node by index 6, 2, 4, 3:")
        cycleList!!.remove(6)
        cycleList!!.remove(2)
        cycleList!!.remove(4)
        cycleList!!.remove(3)
        cycleList!!.printList()
        println("Sorting:")
        cycleList!!.sort(userType!!.typeComparator)
        cycleList!!.printList()
        println("Load from file:")
        cycleList!!.load(userType!!, saveFile)
        cycleList!!.printList()
        println("Iterator:")
        cycleList!!.forEach(object : cycle_list.Iterator {
            override fun toDo(data: Any?) {
                println(data)
            }
        })
//        cycleList!!.forEach(Iterator { x: Any? -> println(x) })
        println("Iterator reverse:")
        cycleList!!.forEachReverse(object : cycle_list.Iterator {
            override fun toDo(data: Any?) {
                println(data)
            }
        })
    }

    companion object {
        private const val INTEGER_FILE_SAVE = "saveInteger.dat"
        private const val FRACTION_FILE_SAVE = "saveFraction.dat"
    }
}
