package Types;

import java.io.Serializable;

public class FractionType implements Serializable {
    private Integer integerPartValue;
    private Integer numeratorValue;
    private Integer denominatorValue;

    private Boolean isNegative;

    public FractionType(Boolean isNegative, Integer integerPartValue, Integer numeratorValue, Integer denominatorValue) throws InstantiationException {
        if (integerPartValue < 0 || numeratorValue < 0 || denominatorValue < 1) {
            throw new InstantiationException("Cannot be instantiated from this values!");
        }
        this.isNegative = isNegative;
        this.integerPartValue = integerPartValue;
        this.numeratorValue = numeratorValue;
        this.denominatorValue = denominatorValue;

        simplify();
    }

    private void simplify() {
        int gcd = gcd(numeratorValue, denominatorValue);
        numeratorValue /= gcd;
        denominatorValue /= gcd;

        if (numeratorValue >= denominatorValue) {
            integerPartValue += numeratorValue / denominatorValue;
            numeratorValue %= denominatorValue;
        }
    }

    public Integer getIntegerPartValue() {
        return this.integerPartValue;
    }

    public Integer getNumeratorValue() {
        return this.numeratorValue;
    }

    public Integer getDenominatorValue() {
        return this.denominatorValue;
    }

    public Boolean getIsNegative() {
        return this.isNegative;
    }

    public void setIntegerPartValue(Integer integerPartValue) throws InstantiationException {
        if (integerPartValue < 0) {
            throw new InstantiationException("Cannot be instantiated from this values!");
        }
        this.integerPartValue = integerPartValue;
        simplify();
    }

    public void setNumeratorValue(Integer numeratorValue) throws InstantiationException {
        if (numeratorValue < 0) {
            throw new InstantiationException("Cannot be instantiated from this values!");
        }
        this.numeratorValue = numeratorValue;
        simplify();
    }

    public void setDenominatorValue(Integer denominatorValue) throws InstantiationException {
        if (denominatorValue < 1) {
            throw new InstantiationException("Cannot be instantiated from this values!");
        }
        this.denominatorValue = denominatorValue;
        simplify();
    }

    public void setIsNegative(Boolean isNegative) {
        this.isNegative = isNegative;
    }

    public static Integer gcd(Integer a, Integer b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    public static Integer lcm(Integer a, Integer b) {
        return (a * b) / gcd(a, b);
    }

    @Override
    public String toString() {
        return (this.isNegative ? "-" : "") + this.integerPartValue + "." + this.numeratorValue + "/" + this.denominatorValue;
    }

}
