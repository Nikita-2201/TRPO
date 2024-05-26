package factory

import types.users.FractionUserType
import types.users.IntegerUserType
import types.users.UserType


class UserFactory {
  private val typeList: List[UserType] = List(new FractionUserType, new IntegerUserType)

  def getTypeNameList: List[String] = typeList.flatMap(t => List(t.typeName))

  def getBuilderByName(name: String): UserType = {
    if (name == null) throw new RuntimeException("Error! Name of type is empty!")
    for (userType <- typeList) {
      if (name == userType.typeName) return userType
    }
    null
  }

}