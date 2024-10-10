package ca.purification.inventory.viewmodel;

import ca.purification.inventory.model.PurificationUnitManager;

/**
 * The {@code ReorderReportsViewModel} class serves as the ViewModel 
 * for managing reorder reports in the purification inventory system. 
 * It interacts with the {@code PurificationUnitManager} to access 
 * and manage purification unit data, allowing for the organization 
 * of units based on specified sort orders.
 *
 * <p>This class provides functionality to set and retrieve the 
 * sorting order for displaying purification units, with the default 
 * sort order initialized to {@code UnitSortOrder.SERIAL_NUMBER}.</p>
 *
 * @see PurificationUnitManager
 * @see UnitSortOrder
 * @see ViewModel
 */
public class ReorderReportsViewModel implements ViewModel {
    private PurificationUnitManager unitManager;
    private UnitSortOrder sortOrder;

    public ReorderReportsViewModel(PurificationUnitManager unitManager) {
        this.unitManager = unitManager;
        this.sortOrder = UnitSortOrder.SERIAL_NUMBER;
    }
    
    public void setSortOrder(UnitSortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

    public UnitSortOrder getSortOrder() {
        return sortOrder;
    }

    public void setUnitManager(PurificationUnitManager unitManager) {
        this.unitManager = unitManager;
    }

    public PurificationUnitManager getUnitManager() {
        return unitManager;
    }
}
