package ca.purification.inventory.viewmodel;

import ca.purification.inventory.model.PurificationUnit;
import ca.purification.inventory.model.PurificationUnitTest;
import ca.purification.inventory.model.ShippingStatus;

import java.util.Optional;
import java.util.function.Predicate;

/**
 * The {@code ReportOption} enum defines the various options available 
 * for generating reports on purification units in the inventory system. 
 * Each option is associated with a specific condition that determines 
 * which units are included in the report based on their state or 
 * testing results.
 *
 * <p>The available report options are:</p>
 * <ul>
 *   <li>{@code ALL} - Includes all units without filtering.</li>
 *   <li>{@code DEFECTIVE} - Includes only units that have failed their 
 *       latest tests.</li>
 *   <li>{@code READY_TO_SHIP} - Includes units that are in stock and 
 *       have passed their latest tests.</li>
 * </ul>
 *
 * <p>Each enum constant has a label for display purposes and a 
 * predicate to filter purification units based on the specified 
 * conditions.</p>
 *
 * @see PurificationUnit
 * @see PurificationUnitTest
 * @see ShippingStatus
 */
public enum ReportOption {
    ALL("ALL", _ -> true),
    DEFECTIVE("DEFECTIVE", ReportOption::isDefectiveProduct),
    READY_TO_SHIP("READY-TO-SHIP", ReportOption::isProductReadyToShip);

    private final String label;
    private final Predicate<PurificationUnit> productResolver;

    ReportOption(String label, Predicate<PurificationUnit> productResolver) {
        this.label = label;
        this.productResolver = productResolver;
    }

    private static boolean isDefectiveProduct(PurificationUnit unit) {
        Optional<PurificationUnitTest> latestTest = unit.getLatestTest();
        return latestTest.isPresent() && !latestTest.orElseThrow().hasPassed();
    }

    private static boolean isProductReadyToShip(PurificationUnit unit) {
        if (unit.getShippingStatus() != ShippingStatus.IN_STOCK) {
            return false;
        }

        Optional<PurificationUnitTest> latestTest = unit.getLatestTest();
        return latestTest.isPresent() && latestTest.orElseThrow().hasPassed();
    }

    public String getLabel() {
        return label;
    }

    public Predicate<PurificationUnit> getProductResolver() {
        return productResolver;
    }
}
