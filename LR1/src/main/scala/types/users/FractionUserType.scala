package types.users

import comparator.FractionComparator
import types.FractionType
import java.io.IOException
import java.io.InputStreamReader
import java.util.Random

object FractionUserType {
  private val MIN_INTEGER_PART = 0
  private val MAX_INTEGER_PART = 10
  private val MIN_NUMERATOR = 0
  private val MAX_NUMERATOR = 1000
  private val MIN_DENOMINATOR = 1
  private val MAX_DENOMINATOR = 1000
}

class FractionUserType extends UserType {
  override def typeName = "Fraction"

  @throws[InstantiationException]
  override def create: AnyRef = {
    val random = new Random
    val isNegative = random.nextBoolean
    val partInteger = random.nextInt(FractionUserType.MAX_INTEGER_PART - FractionUserType.MIN_INTEGER_PART) + FractionUserType.MIN_INTEGER_PART
    val numerator = random.nextInt(FractionUserType.MAX_NUMERATOR - FractionUserType.MIN_NUMERATOR) + FractionUserType.MIN_NUMERATOR
    val denominator = random.nextInt(FractionUserType.MAX_DENOMINATOR - FractionUserType.MIN_DENOMINATOR) + FractionUserType.MIN_DENOMINATOR
    new FractionType(isNegative, partInteger, numerator, denominator)
  }

  @throws[InstantiationException]
  override def clone(`object`: AnyRef) = new FractionType(`object`.asInstanceOf[FractionType].getIsNegative, `object`.asInstanceOf[FractionType].getIntegerPartValue, `object`.asInstanceOf[FractionType].getNumeratorValue, `object`.asInstanceOf[FractionType].getDenominatorValue)

  override def readValue(in: InputStreamReader): Any = try in.read
  catch {
    case e: IOException =>
      throw new RuntimeException(e)
  }

  @throws[InstantiationException]
  override def parseValue(ss: String): AnyRef = {
    val parts = ss.split("/")
    var left: Integer = null
    var mid: Integer = null
    var right: Integer = null
    var isNegative = false
    try {
      val leftMid = parts(0).split("\\.")
      left = leftMid(0).toInt
      mid = leftMid(1).toInt
      right = parts(1).toInt
    } catch {
      case _: NumberFormatException | _: ArrayIndexOutOfBoundsException =>
        System.out.println("Error: Unable to parse the input.")
    }
    if (left != null && mid != null && right != null) {
      if (left < 0) {
        isNegative = true
        left = -left
      }
      new FractionType(isNegative, left, mid, right)
    }
    else null
  }

  override def getTypeComparator = new FractionComparator

  override def toString(`object`: AnyRef): String = `object`.toString
}