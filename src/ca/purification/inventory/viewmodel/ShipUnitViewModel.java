package ca.purification.inventory.viewmodel;

import ca.purification.inventory.model.PurificationUnit;
import ca.purification.inventory.model.PurificationUnitManager;
import ca.purification.inventory.model.SerialNumber;

import java.time.LocalDate;

/**
 * The {@code ShipUnitViewModel} class serves as a view model for 
 * managing the shipping process of purification units within the 
 * inventory system. It provides functionality to set and retrieve 
 * the shipped unit, as well as to update its shipping status.
 *
 * <p>This view model is initialized with a {@code PurificationUnitManager}
 * to access and manipulate purification units and includes a default 
 * sorting order based on serial numbers.</p>
 *
 * <p>Key functionalities include:</p>
 * <ul>
 *   <li>Setting the unit to be shipped using its serial number.</li>
 *   <li>Updating the shipping date for the selected unit.</li>
 *   <li>Retrieving the shipped unit and its associated data.</li>
 * </ul>
 *
 * @see PurificationUnit
 * @see PurificationUnitManager
 * @see SerialNumber
 * @see UnitSortOrder
 */
public class ShipUnitViewModel implements ViewModel {
    private PurificationUnitManager unitManager;
    private PurificationUnit shippedUnit;
    private final UnitSortOrder unitSortOrder;

    public ShipUnitViewModel(PurificationUnitManager unitManager) {
        this(unitManager, UnitSortOrder.SERIAL_NUMBER);
    }

    public ShipUnitViewModel(PurificationUnitManager unitManager, UnitSortOrder unitSortOrder) {
        this.unitManager = unitManager;
        this.unitSortOrder = unitSortOrder;
    }

    public void setShippedUnit(SerialNumber unitSerialNumber) {
        setShippedUnit(unitManager.getUnit(unitSerialNumber));
    }

    public void setShippedUnit(PurificationUnit unit) {
        this.shippedUnit = unit;
        unit.setDateShipped(LocalDate.now());
    }

    public PurificationUnit getShippedUnit() {
        return shippedUnit;
    }

    public void setUnitManager(PurificationUnitManager unitManager) {
        this.unitManager = unitManager;
    }

    public PurificationUnitManager getUnitManager() {
        return unitManager;
    }

    public UnitSortOrder getUnitSortOrder() {
        return unitSortOrder;
    }
}
