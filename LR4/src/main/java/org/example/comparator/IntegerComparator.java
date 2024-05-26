package org.example.comparator;

import org.example.types.IntegerType;

import java.io.Serializable;

public class IntegerComparator implements Comparator, Serializable {
    @Override
    public int compare(Object o1, Object o2) {
        return ((IntegerType) o1).getIntegerValue() - ((IntegerType) o2).getIntegerValue();
    }
}
