package ca.purification.inventory.model;

/**
 * The {@code ShippingStatus} enum represents the possible shipping statuses 
 * of a purification unit.
 *
 * <p>This enum defines two constants:
 * <ul>
 *     <li>{@link #IN_STOCK} - Indicates that the purification unit is currently in stock 
 *     and has not been shipped.</li>
 *     <li>{@link #SHIPPED_OUT} - Indicates that the purification unit has been shipped to 
 *     its destination.</li>
 * </ul>
 * </p>
 */
public enum ShippingStatus {
    IN_STOCK,
    SHIPPED_OUT
}
