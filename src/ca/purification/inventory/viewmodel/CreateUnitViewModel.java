package ca.purification.inventory.viewmodel;

import ca.purification.inventory.model.ModelId;
import ca.purification.inventory.model.PurificationUnit;
import ca.purification.inventory.model.PurificationUnitManager;
import ca.purification.inventory.model.SerialNumber;

/**
 * ViewModel class responsible for creating and managing purification units.
 * <p>
 * This class interacts with the {@link PurificationUnitManager} to create new
 * instances of {@link PurificationUnit} and store them. It provides methods to 
 * set a newly created unit either by its model ID and serial number or directly 
 * by a {@link PurificationUnit} instance.
 * </p>
 *
 * @see PurificationUnitManager
 * @see PurificationUnit
 */
public class CreateUnitViewModel implements ViewModel {
    private final PurificationUnitManager unitManager;

    public CreateUnitViewModel(PurificationUnitManager unitManager) {
        this.unitManager = unitManager;
    }

    public void setCreatedUnit(ModelId modelId, SerialNumber serialNumber) {
        PurificationUnit newUnit = new PurificationUnit(modelId, serialNumber);
        setCreatedUnit(newUnit);
    }

    public void setCreatedUnit(PurificationUnit unit) {
        unitManager.putUnit(unit);
    }
}
