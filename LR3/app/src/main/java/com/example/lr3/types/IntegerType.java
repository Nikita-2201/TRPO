package com.example.lr3.types;

import java.io.Serializable;

public class IntegerType implements Serializable {
    private Integer integerValue;

    public IntegerType(Integer integerValue) {
        this.integerValue = integerValue;
    }

    public Integer getIntegerValue() {
        return this.integerValue;
    }

    public void setIntegerValue(Integer integerValue) {
        this.integerValue = integerValue;
    }

    @Override
    public String toString() {
        return String.valueOf(this.integerValue);
    }
}
