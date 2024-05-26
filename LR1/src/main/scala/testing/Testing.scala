package testing

import cycle_list.CycleList
import factory.UserFactory
import types.users.UserType
import java.io.IOException

object Testing {
  private val INTEGER_FILE_SAVE = "saveInteger.dat"
  private val FRACTION_FILE_SAVE = "saveFraction.dat"
}

class Testing {

  @throws[IOException]
  @throws[InstantiationException]
  def testIntegerType(): Unit = {
    test("Integer", Testing.INTEGER_FILE_SAVE)
  }

  @throws[IOException]
  @throws[InstantiationException]
  def testFractionType(): Unit = {
    test("Fraction", Testing.FRACTION_FILE_SAVE)
  }

  private def test(typeUser: String, saveFile: String): Unit = {
    val userFactory = new UserFactory
    System.out.println("\n%s test:".format(typeUser))
    var userType = userFactory.getBuilderByName(typeUser)

    var cycleList = new CycleList
    cycleList.add(userType.create)
    cycleList.add(userType.create)
    cycleList.add(userType.create)
    cycleList.add(userType.create)
    cycleList.add(userType.create)
    cycleList.add(userType.create)
    cycleList.add(userType.create)
    cycleList.add(userType.create)
    cycleList.add(userType.create)
    cycleList.add(userType.create)
    cycleList.printList()
    System.out.println("Saving file:")
    try {
      cycleList.save(userType, saveFile)
      System.out.println("Saved")
    } catch {
      case e: Exception =>
        throw new RuntimeException(e)
    }
    System.out.println("\nGet node by index 6, 2, 4, 3:")
    System.out.println("6) = " + cycleList.getByIndex(6).toString)
    System.out.println("2) = " + cycleList.getByIndex(2).toString)
    System.out.println("4) = " + cycleList.getByIndex(4).toString)
    System.out.println("3) = " + cycleList.getByIndex(3).toString)
    System.out.println("\nDelete node by index 6, 2, 4, 3:")
    cycleList.remove(6)
    cycleList.remove(2)
    cycleList.remove(4)
    cycleList.remove(3)
    cycleList.printList()
    System.out.println("Sorting:")
    cycleList.sort(userType.getTypeComparator)
    cycleList.printList()

    System.out.println("Load from file:")
    cycleList.load(userType, saveFile)
    cycleList.printList()
    System.out.println("Sorting by func style:")
    cycleList = cycleList.mergeSortFuncStyle(userType.getTypeComparator)
    cycleList.printList()

    System.out.println("Iterator:")
    cycleList.forEach(System.out.println)
    System.out.println("Iterator reverse:")
    cycleList.forEachReverse(System.out.println)
  }

}