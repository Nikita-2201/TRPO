package org.example.types.users;

import org.example.comparator.Comparator;

import java.io.IOException;
import java.io.InputStreamReader;

public interface UserType {

    String typeName();

    Object create() throws InstantiationException;

    Object clone(Object object) throws InstantiationException;

    Object readValue(InputStreamReader in) throws IOException;

    Object parseValue(String ss) throws InstantiationException;

    Comparator getTypeComparator();

    String toString(Object object);
}

