package ca.purification.inventory.model;

import ca.purification.inventory.util.StringUtils;

import java.math.BigInteger;

/**
 * The {@code SerialNumber} class represents a serial number for a purification unit,
 * encapsulating the validation, checksum calculation, and comparison functionalities
 * associated with serial numbers.
 *
 * <p>This class ensures that serial numbers meet specific criteria:
 * <ul>
 *     <li>Must be a valid unsigned integer.</li>
 *     <li>Must have a length between {@link #MIN_DIGIT_COUNT} and {@link #MAX_DIGIT_COUNT} digits.</li>
 *     <li>Includes a checksum, which is verified during construction.</li>
 * </ul>
 * </p>
 *
 * <p>Additionally, the class provides methods for retrieving the serial number's value,
 * checking equality, generating a string representation, and comparing two serial numbers.</p>
 *
 * @see InvalidSerialNumberException
 */

public class SerialNumber implements Comparable<SerialNumber> {
    private static final int CHECKSUM_DIGIT_COUNT = 2;
    public static final int MIN_DIGIT_COUNT = CHECKSUM_DIGIT_COUNT + 1;
    public static final int MAX_DIGIT_COUNT = 15;

    private final String value;

    public SerialNumber(String serialNumber) {
        if (!isValid(serialNumber)) {
            String errorMsg = getErrorMessage(serialNumber);
            throw new InvalidSerialNumberException(errorMsg, serialNumber);
        }

        this.value = serialNumber;
    }

    private static String getErrorMessage(String serialNumber) {
        if (serialNumber == null) {
            return "Serial Number Error: Checksum does not match.";
        } else if (serialNumber.length() < MIN_DIGIT_COUNT) {
            return "Serial Number Error: Length is too short.";
        } else if (serialNumber.length() > MAX_DIGIT_COUNT) {
            return "Serial Number Error: Length exceeds maximum length.";
        }
        
        return "Serial Number Error: Checksum does not match.";
    }

    private static boolean isValid(String serialNumber) {
        boolean isValidString = serialNumber != null
                && serialNumber.equals(serialNumber.strip())
                && StringUtils.isUnsignedIntegral(serialNumber)
                && serialNumber.length() >= MIN_DIGIT_COUNT
                && serialNumber.length() <= MAX_DIGIT_COUNT;

        return isValidString && computeChecksum(serialNumber) == readChecksum(serialNumber);
    }

    private static int computeChecksum(String serialNumber) {
        int digitSum = 0;

        for (int i = 0; i < serialNumber.length() - CHECKSUM_DIGIT_COUNT; i++) {
            digitSum += Character.digit(serialNumber.charAt(i), 10);
        }

        return digitSum % 100;
    }

    private static int readChecksum(String serialNumber) {
        String checksumPortion = serialNumber.substring(serialNumber.length() - CHECKSUM_DIGIT_COUNT);
        return Integer.parseUnsignedInt(checksumPortion);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (obj instanceof SerialNumber other) {
            return value.equals(other.value);
        }

        return obj instanceof String serialNumber && value.equals(serialNumber);
    }
    
    public BigInteger intValue() { return new BigInteger(value); }
    
    @Override
    public String toString() {
        return getValue();
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public int compareTo(SerialNumber o) {
        return intValue().compareTo(o.intValue());
    }
}
