package types.users

import comparator.IntegerComparator
import java.io.IOException
import java.io.InputStreamReader
import java.util.Random
import types.IntegerType

class IntegerUserType extends UserType {
  final private val MAX = 1000
  final private val MIN = -1000

  override def typeName = "Integer"

  @throws[InstantiationException]
  override def create: AnyRef = {
    val random = new Random
    val value = random.nextInt(MAX - MIN) + MIN
    new IntegerType(value)
  }

  @throws[InstantiationException]
  override def clone(`object`: AnyRef) = new IntegerType(`object`.asInstanceOf[IntegerType].getIntegerValue)

  override def readValue(in: InputStreamReader): Any = try in.read
  catch {
    case e: IOException =>
      throw new RuntimeException(e)
  }

  @throws[InstantiationException]
  override def parseValue(ss: String): AnyRef = try new IntegerType(ss.toInt)
  catch {
    case e: NumberFormatException =>
      null
  }

  override def getTypeComparator = new IntegerComparator

  override def toString(`object`: AnyRef): String = `object`.toString
}