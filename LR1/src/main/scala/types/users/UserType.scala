package types.users

import comparator.Comparator
import java.io.IOException
import java.io.InputStreamReader

trait UserType {
  def typeName: String

  @throws[InstantiationException]
  def create: AnyRef

  @throws[InstantiationException]
  def clone(`object`: AnyRef): AnyRef

  @throws[IOException]
  def readValue(in: InputStreamReader): Any

  @throws[InstantiationException]
  def parseValue(ss: String): AnyRef

  def getTypeComparator: Comparator

  def toString(`object`: AnyRef): String
}