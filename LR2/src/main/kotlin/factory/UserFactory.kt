package factory

import types.users.FractionUserType
import types.users.IntegerUserType
import types.users.UserType
import java.util.*
import java.util.stream.Collectors

class UserFactory {
    private val typeList = ArrayList<UserType>()

    init {
        val buildersClasses = ArrayList(listOf(FractionUserType(), IntegerUserType()))
        typeList.addAll(buildersClasses)
    }

    fun getTypeNameList(): List<String> {
        return typeList.stream().map { obj: UserType -> obj.typeName() }.collect(Collectors.toList())
    }

    fun getBuilderByName(name: String?): UserType? {
        if (name == null) {
            throw RuntimeException("Error! Name of type is empty!")
        }
        for (userType in typeList) {
            if (name == userType.typeName()) {
                return userType
            }
        }
        return null
    }
}
