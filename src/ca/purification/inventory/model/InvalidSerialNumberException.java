package ca.purification.inventory.model;

/**
 * The {@code InvalidSerialNumberException} class represents an exception that is thrown when an invalid 
 * {@code serialNumber} is encountered.
 *
 * <p>This exception is a specialized form of {@link IllegalArgumentException} and includes the problematic
 * {@code serialNumber} that caused the exception. It helps to provide context for errors related to serial
 * number validation in the inventory system.</p>
 *
 * <p>It is typically used in scenarios where {@link SerialNumber} validation fails.</p>
 *
 * @see SerialNumber
 */
public class InvalidSerialNumberException extends IllegalArgumentException {
    private final String serialNumber;

    public InvalidSerialNumberException(String serialNumber) {
        this("Invalid serial number", serialNumber);
    }

    public InvalidSerialNumberException(String message, String serialNumber) {
        super(message);
        this.serialNumber = serialNumber;
    }

    public String getSerialNumber() {
        return serialNumber;
    }
}
