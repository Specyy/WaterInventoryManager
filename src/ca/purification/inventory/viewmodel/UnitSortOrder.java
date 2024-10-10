package ca.purification.inventory.viewmodel;

import ca.purification.inventory.model.PurificationUnit;
import ca.purification.inventory.model.PurificationUnitTest;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Optional;

/**
 * Enum representing the sorting orders for {@link PurificationUnit} instances.
 * <p>
 * This enum provides predefined comparators for sorting purification units based on
 * their serial number, model, or the date of their latest test. Each sorting order
 * corresponds to a specific comparator, allowing for flexible and consistent sorting
 * of purification units in various contexts.
 * </p>
 *
 * <ul>
 *     <li>{@link #SERIAL_NUMBER} - Sorts by the serial number of the purification unit.</li>
 *     <li>{@link #MODEL} - Sorts first by the model of the purification unit and, if
 *     models are equal, by serial number.</li>
 *     <li>{@link #TEST_DATE} - Sorts by the date of the latest test performed on the purification unit.
 *         If a unit has no tests, it is sorted to the end.</li>
 * </ul>
 *
 * @see PurificationUnit
 * @see PurificationUnitTest
 */
public enum UnitSortOrder {
    SERIAL_NUMBER(getSerialNumberSorter()),
    MODEL(getModelSorter()),
    TEST_DATE(getTestDateSorter());

    private final Comparator<PurificationUnit> sorter;

    UnitSortOrder(Comparator<PurificationUnit> sorter) {
        this.sorter = sorter;
    }

    private static Comparator<PurificationUnit> getSerialNumberSorter() {
        return Comparator.comparing(PurificationUnit::getSerialNumber);
    }

    private static Comparator<PurificationUnit> getModelSorter() {
        return Comparator.comparing(PurificationUnit::getModel)
                .thenComparing(PurificationUnit::getSerialNumber);
    }

    private static Comparator<PurificationUnit> getTestDateSorter() {
        return (unit1, unit2) -> {
            Optional<PurificationUnitTest> latestTest1 = unit1.getLatestTest();
            Optional<PurificationUnitTest> latestTest2 = unit2.getLatestTest();

            if (latestTest1.isEmpty() && latestTest2.isEmpty()) {
                return 0;
            }

            if (latestTest1.isEmpty()) {
                return latestTest2.orElseThrow().getDate().compareTo(LocalDate.MIN);
            } else if (latestTest2.isEmpty()) {
                return latestTest1.orElseThrow().getDate().compareTo(LocalDate.MAX);
            }

            return latestTest1.orElseThrow().compareTo(latestTest2.orElseThrow());
        };
    }

    public Comparator<PurificationUnit> getSorter() {
        return sorter;
    }
}
