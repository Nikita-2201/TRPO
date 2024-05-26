package com.example.lr3.comparator;

import com.example.lr3.types.FractionType;

import java.io.Serializable;

public class FractionComparator implements Comparator, Serializable {
    @Override
    public int compare(Object o1, Object o2) {
        Integer lcm = FractionType.lcm(((FractionType) o1).getDenominatorValue(), ((FractionType) o2).getDenominatorValue());
        int coef1 = ((FractionType) o1).getIsNegative() ? -1 : 1;
        int coef2 = ((FractionType) o2).getIsNegative() ? -1 : 1;

        int num1 = coef1 * (((FractionType) o1).getIntegerPartValue() * ((FractionType) o1).getDenominatorValue() +
                ((FractionType) o1).getNumeratorValue()) * (lcm / ((FractionType) o1).getDenominatorValue());
        int num2 = coef2 * (((FractionType) o2).getIntegerPartValue() * ((FractionType) o2).getDenominatorValue() +
                ((FractionType) o2).getNumeratorValue()) * (lcm / ((FractionType) o2).getDenominatorValue());

        return Integer.compare(num1, num2);
    }

}
