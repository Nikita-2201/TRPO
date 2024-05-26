package types

object FractionType {
  def gcd(a: Integer, b: Integer): Integer = {
    if (b == 0) a else gcd(b, a % b)
  }

  def lcm(a: Integer, b: Integer): Integer = (a * b) / gcd(a, b)
}

class FractionType @throws[InstantiationException]
(private var isNegative: Boolean, private var integerPartValue: Integer, private var numeratorValue: Integer, private var denominatorValue: Integer) extends Serializable {
  if (integerPartValue < 0 || numeratorValue < 0 || denominatorValue < 1) throw new InstantiationException("Cannot be instantiated from this values!")
  simplify()

  private def simplify(): Unit = {
    val gcd = FractionType.gcd(numeratorValue, denominatorValue)
    numeratorValue /= gcd
    denominatorValue /= gcd
    if (numeratorValue >= denominatorValue) {
      integerPartValue += numeratorValue / denominatorValue
      numeratorValue %= denominatorValue
    }
  }

  def getIntegerPartValue: Integer = this.integerPartValue

  def getNumeratorValue: Integer = this.numeratorValue

  def getDenominatorValue: Integer = this.denominatorValue

  def getIsNegative: Boolean = this.isNegative

  @throws[InstantiationException]
  def setIntegerPartValue(integerPartValue: Integer): Unit = {
    if (integerPartValue < 0) throw new InstantiationException("Cannot be instantiated from this values!")
    this.integerPartValue = integerPartValue
    simplify()
  }

  @throws[InstantiationException]
  def setNumeratorValue(numeratorValue: Integer): Unit = {
    if (numeratorValue < 0) throw new InstantiationException("Cannot be instantiated from this values!")
    this.numeratorValue = numeratorValue
    simplify()
  }

  @throws[InstantiationException]
  def setDenominatorValue(denominatorValue: Integer): Unit = {
    if (denominatorValue < 1) throw new InstantiationException("Cannot be instantiated from this values!")
    this.denominatorValue = denominatorValue
    simplify()
  }

  def setIsNegative(isNegative: Boolean): Unit = {
    this.isNegative = isNegative
  }

  override def toString: String = (if (this.isNegative) "-"
  else "") + this.integerPartValue + "." + this.numeratorValue + "/" + this.denominatorValue
}