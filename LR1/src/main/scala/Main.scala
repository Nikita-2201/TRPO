import testing.Testing
import gui.GUI

object Main {
  def main(args: Array[String]): Unit = {
    val test: Testing = new Testing
    test.testIntegerType()
    test.testFractionType()
  }
}