package ca.purification.inventory.model;

import java.util.*;
import java.util.function.Predicate;

/**
 * The {@code PurificationUnitManager} class manages a collection of {@link PurificationUnit} instances,
 * allowing for the addition, removal, and retrieval of purification units by their {@link SerialNumber}.
 *
 * <p>This class supports various operations, including:
 * <ul>
 *     <li>Adding individual or multiple purification units.</li>
 *     <li>Retrieving a purification unit based on its serial number.</li>
 *     <li>Checking for the existence of a unit.</li>
 *     <li>Removing units based on their serial number or by a specified condition.</li>
 *     <li>Counting the total number of units.</li>
 *     <li>Retrieving all units or sorted units based on a provided comparator.</li>
 * </ul>
 * </p>
 *
 * @see PurificationUnit
 */
public class PurificationUnitManager {
    private final Map<SerialNumber, PurificationUnit> units = new LinkedHashMap<>();

    public PurificationUnit putUnit(PurificationUnit unit) {
        return units.put(unit.getSerialNumber(), unit);
    }

    public void putUnits(Iterable<? extends PurificationUnit> units) {
        for (PurificationUnit unit : units) {
            putUnit(unit);
        }
    }

    public void putUnits(PurificationUnit... units) {
        for (PurificationUnit unit : units) {
            putUnit(unit);
        }
    }

    public PurificationUnit getUnit(SerialNumber serialNumber) {
        return units.get(serialNumber);
    }

    public boolean containsUnit(SerialNumber serialNumber) {
        return units.containsKey(serialNumber);
    }

    public boolean removeUnit(SerialNumber serialNumber) {
        return units.remove(serialNumber) != null;
    }

    public boolean removeUnit(PurificationUnit unit) {
        return units.remove(unit.getSerialNumber(), unit);
    }

    public void removeUnits(Predicate<PurificationUnit> predicate) {
        for (PurificationUnit unit : units.values()) {
            if (predicate.test(unit)) {
                removeUnit(unit);
            }
        }
    }

    public void removeAllUnits() {
        units.clear();
    }

    public int getUnitCount() {
        return units.size();
    }

    public boolean hasUnit(PurificationUnit unit) {
        return hasUnit(unit.getSerialNumber());
    }

    public boolean hasUnit(SerialNumber unitSerialNumber) {
        return units.containsKey(unitSerialNumber);
    }

    public boolean hasUnits() {
        return !units.isEmpty();
    }

    public Collection<? extends PurificationUnit> getUnits() {
        return units.values();
    }

    public Collection<? extends PurificationUnit> getSortedUnits(Comparator<? super PurificationUnit> comparator) {
        List<PurificationUnit> units = new ArrayList<>(getUnits());
        units.sort(comparator);
        return units;
    }
}
