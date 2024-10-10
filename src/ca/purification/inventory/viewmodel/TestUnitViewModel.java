package ca.purification.inventory.viewmodel;

import ca.purification.inventory.model.PurificationUnit;
import ca.purification.inventory.model.PurificationUnitManager;
import ca.purification.inventory.model.PurificationUnitTest;
import ca.purification.inventory.model.SerialNumber;

import java.time.LocalDate;

/**
 * The {@code TestUnitViewModel} class acts as a view model for 
 * managing the testing process of purification units within the 
 * inventory system. It facilitates the creation and management 
 * of tests associated with purification units, enabling the 
 * tracking of test results and comments.
 *
 * <p>This view model is initialized with a {@code PurificationUnitManager}
 * to access and manipulate purification units and includes a default 
 * sorting order based on serial numbers.</p>
 *
 * <p>Key functionalities include:</p>
 * <ul>
 *   <li>Setting the selected purification unit for testing.</li>
 *   <li>Creating and adding test results to the selected unit.</li>
 *   <li>Retrieving the latest created test for the selected unit.</li>
 *   <li>Managing the sorting order of purification units.</li>
 * </ul>
 *
 * @see PurificationUnit
 * @see PurificationUnitManager
 * @see PurificationUnitTest
 * @see SerialNumber
 * @see UnitSortOrder
 */
public class TestUnitViewModel implements ViewModel {
    private PurificationUnitManager unitManager;
    private PurificationUnit selectedUnit;
    private PurificationUnitTest createdTest;
    private UnitSortOrder unitSortOrder;

    public TestUnitViewModel(PurificationUnitManager unitManager) {
        this(unitManager, UnitSortOrder.SERIAL_NUMBER);
    }

    public TestUnitViewModel(PurificationUnitManager unitManager, UnitSortOrder unitSortOrder) {
        this.unitManager = unitManager;
        this.unitSortOrder = unitSortOrder;
    }

    private PurificationUnitTest addUnitTest(boolean hasPassed, String comment) {
        return addUnitTest(LocalDate.now(), hasPassed, comment);
    }

    private PurificationUnitTest addUnitTest(LocalDate date, boolean hasPassed, String comment) {
        PurificationUnitTest unitTest = new PurificationUnitTest(date, hasPassed, comment);
        addUnitTest(unitTest);
        return unitTest;
    }

    private void addUnitTest(PurificationUnitTest unitTest) {
        selectedUnit.addTest(unitTest);
    }

    public PurificationUnitTest getCreatedUnitTest() {
        return createdTest;
    }

    public void setCreatedUnitTest(PurificationUnitTest unitTest) {
        this.createdTest = unitTest;
        addUnitTest(unitTest);
    }

    public void setCreatedUnitTest(boolean hasPassed, String comment) {
        this.createdTest = addUnitTest(hasPassed, comment);
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

    public void setUnitSortOrder(UnitSortOrder unitSortOrder) {
        this.unitSortOrder = unitSortOrder;
    }

    public UnitSortOrder getUnitSortOrder() {
        return unitSortOrder;
    }

    public PurificationUnitManager getUnitManager() {
        return unitManager;
    }

    public void setUnitManager(PurificationUnitManager unitManager) {
        this.unitManager = unitManager;
    }
}
