package types

class IntegerType(private var integerValue: Integer) extends Serializable {
  def getIntegerValue: Integer = this.integerValue

  def setIntegerValue(integerValue: Integer): Unit = {
    this.integerValue = integerValue
  }

  override def toString: String = String.valueOf(this.integerValue)
}