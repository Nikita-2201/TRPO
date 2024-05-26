package Types.Users;

import Comparator.Comparator;
import Comparator.FractionComparator;
import Types.FractionType;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class FractionUserType implements UserType {

    private static final int MIN_INTEGER_PART = 0;
    private static final int MAX_INTEGER_PART = 10;
    private static final int MIN_NUMERATOR = 0;
    private static final int MAX_NUMERATOR = 1000;
    private static final int MIN_DENOMINATOR = 1;
    private static final int MAX_DENOMINATOR = 1000;

    @Override
    public String typeName() {
        return "Fraction";
    }

    @Override
    public Object create() throws InstantiationException {
        Random random = new Random();
        Boolean isNegative = random.nextBoolean();
        Integer partInteger = random.nextInt(MAX_INTEGER_PART - MIN_INTEGER_PART) + MIN_INTEGER_PART;
        Integer numerator = random.nextInt(MAX_NUMERATOR - MIN_NUMERATOR) + MIN_NUMERATOR;
        Integer denominator = random.nextInt(MAX_DENOMINATOR - MIN_DENOMINATOR) + MIN_DENOMINATOR;
        return new FractionType(isNegative, partInteger, numerator, denominator);
    }

    @Override
    public Object clone(Object object) throws InstantiationException {
        return new FractionType(((FractionType) object).getIsNegative(), ((FractionType) object).getIntegerPartValue(), ((FractionType) object).getNumeratorValue(), ((FractionType) object).getDenominatorValue());
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
        String[] parts = ss.split("/");
        Integer left = null;
        Integer mid = null;
        Integer right = null;
        boolean isNegative = false;

        try {
            String[] leftMid = parts[0].split("\\.");
            left = Integer.parseInt(leftMid[0]);
            mid = Integer.parseInt(leftMid[1]);
            right = Integer.parseInt(parts[1]);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Error: Unable to parse the input.");
        }

        if (left != null && mid != null && right != null) {
            if (left < 0) {
                isNegative = true;
                left = -left;
            }
            return new FractionType(isNegative, left, mid, right);
        } else {
            return null;
        }
    }

    @Override
    public Comparator getTypeComparator() {
        return new FractionComparator();
    }

    @Override
    public String toString(Object object) {
        return object.toString();
    }

}
