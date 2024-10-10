package ca.purification.inventory.viewmodel;

import ca.purification.inventory.model.PurificationUnit;
import ca.purification.inventory.model.PurificationUnitManager;

import java.util.*;
import java.util.function.Supplier;

/**
 * The {@code PrintReportViewModel} class serves as a view model 
 * for generating reports on {@code PurificationUnit} instances. 
 * It utilizes a {@code PurificationUnitManager} to manage the 
 * units and provides functionality to filter and sort the units 
 * based on a specified {@code ReportOption} and {@code UnitSortOrder}.
 *
 * <p>This class allows users to retrieve a collection of units 
 * that meet specific reporting criteria. The reported units can 
 * be sorted according to the specified sort order, which defaults 
 * to sorting by serial number.</p>
 *
 * @see PurificationUnit
 * @see PurificationUnitManager
 * @see ReportOption
 * @see UnitSortOrder
 * @see ViewModel
 */
public class PrintReportViewModel implements ViewModel {
    private PurificationUnitManager unitManager;
    private final Supplier<UnitSortOrder> unitSortOrder;
    private ReportOption reportOption;

    public PrintReportViewModel(PurificationUnitManager unitManager) {
        this(unitManager, () -> UnitSortOrder.SERIAL_NUMBER);
    }

    public PrintReportViewModel(PurificationUnitManager unitManager, Supplier<UnitSortOrder> unitSortOrder) {
        this.unitManager = unitManager;
        this.unitSortOrder = unitSortOrder;
    }

    public Collection<? extends PurificationUnit> getReportedUnits() {
        if (reportOption == null) {
            return Collections.emptyList();
        }
        
        List<PurificationUnit> units = new ArrayList<>();

        for (PurificationUnit unit : unitManager.getUnits()) {
            if (reportOption.getProductResolver().test(unit)) {
                units.add(unit);
            }
        }

        if (unitSortOrder != null) {
            units.sort(getUnitSortOrder().getSorter());
        }

        return units;
    }

    public void setReportOption(ReportOption reportOption) {
        this.reportOption = reportOption;
    }

    public ReportOption getReportOption() {
        return reportOption;
    }

    public UnitSortOrder getUnitSortOrder() {
        return unitSortOrder.get();
    }

    public void setUnitManager(PurificationUnitManager unitManager) {
        this.unitManager = unitManager;
    }

    public PurificationUnitManager getUnitManager() {
        return unitManager;
    }
}
