package org.example.types.users;

import org.example.comparator.Comparator;
import org.example.comparator.IntegerComparator;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

import org.example.types.IntegerType;

public class IntegerUserType implements UserType {

    private final int MAX = 1000;
    private final int MIN = -1000;

    @Override
    public String typeName() {
        return "Integer";
    }

    @Override
    public Object create() throws InstantiationException {
        Random random = new Random();
        Integer value = random.nextInt((MAX - MIN)) + MIN;
        return new IntegerType(value);
    }

    @Override
    public Object clone(Object object) throws InstantiationException {
        return new IntegerType(((IntegerType) object).getIntegerValue());
    }

    @Override
    public Object readValue(InputStreamReader in) {
        try {
            return in.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object parseValue(String ss) throws InstantiationException {
        try {
            return new IntegerType(Integer.parseInt(ss));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public Comparator getTypeComparator() {
        return new IntegerComparator();
    }

    @Override
    public String toString(Object object) {
        return object.toString();
    }

}
