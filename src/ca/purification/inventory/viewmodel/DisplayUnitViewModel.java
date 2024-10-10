package ca.purification.inventory.viewmodel;

import ca.purification.inventory.model.PurificationUnit;
import ca.purification.inventory.model.PurificationUnitManager;
import ca.purification.inventory.model.SerialNumber;

/**
 * The {@code DisplayUnitViewModel} class acts as a view model that 
 * facilitates the presentation of a selected {@code PurificationUnit}. 
 * It interacts with a {@code PurificationUnitManager} to retrieve 
 * unit data and manages the currently selected unit, allowing 
 * sorting based on a specified {@code UnitSortOrder}.
 *
 * <p>This class provides methods to set and retrieve the selected 
 * purification unit, along with the ability to update the unit 
 * manager if needed. It defaults to sorting by serial number unless 
 * a different order is specified.</p>
 *
 * @see PurificationUnit
 * @see PurificationUnitManager
 * @see SerialNumber
 * @see UnitSortOrder
 * @see ViewModel
 */
public class DisplayUnitViewModel implements ViewModel {
    private PurificationUnitManager unitManager;
    private PurificationUnit selectedUnit;
    private final UnitSortOrder unitSortOrder;

    public DisplayUnitViewModel(PurificationUnitManager unitManager) {
        this(unitManager, UnitSortOrder.SERIAL_NUMBER);
    }

    public DisplayUnitViewModel(PurificationUnitManager unitManager, UnitSortOrder unitSortOrder) {
        this.unitManager = unitManager;
        this.unitSortOrder = unitSortOrder;
    }

    public void setSelectedUnit(SerialNumber unitSerialNumber) {
        setSelectedUnit(unitManager.getUnit(unitSerialNumber));
    }

    public void setSelectedUnit(PurificationUnit selectedUnit) {
        this.selectedUnit = selectedUnit;
    }

    public PurificationUnit getSelectedUnit() {
        return selectedUnit;
    }

    public UnitSortOrder getUnitSortOrder() {
        return unitSortOrder;
    }

    public void setUnitManager(PurificationUnitManager unitManager) {
        this.unitManager = unitManager;
    }

    public PurificationUnitManager getUnitManager() {
        return unitManager;
    }
}
