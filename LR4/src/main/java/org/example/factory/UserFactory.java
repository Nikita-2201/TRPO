package org.example.factory;

import org.example.types.users.FractionUserType;
import org.example.types.users.IntegerUserType;
import org.example.types.users.UserType;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class UserFactory {
    private final static ArrayList<UserType> typeList = new ArrayList<>();

    static {
        ArrayList<UserType> buildersClasses = new ArrayList<>(Arrays.asList(new FractionUserType(), new IntegerUserType()));
        typeList.addAll(buildersClasses);
    }

    public static Set<String> getTypeNameList() {
        return typeList.stream().map(UserType::typeName).collect(Collectors.toSet());
    }

    public static UserType getBuilderByName(String name) {
        if (name == null) {
            throw new RuntimeException("Error! Name of type is empty!");
        }
        for (UserType userType : typeList) {
            if (name.equals(userType.typeName())) {
                return userType;
            }
        }
        return null;
    }
}
