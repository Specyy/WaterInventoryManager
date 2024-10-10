package ca.purification.inventory.view.helper;

import ca.purification.inventory.model.ModelId;
import ca.purification.inventory.model.PurificationUnit;
import ca.purification.inventory.model.PurificationUnitTest;
import ca.purification.inventory.model.SerialNumber;
import ca.purification.inventory.ui.element.ParagraphElement;
import ca.purification.inventory.ui.element.TableElement;
import ca.purification.inventory.util.StringUtils;
import ca.purification.inventory.viewmodel.ReportOption;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The {@code ReadyToShipUnitList} class manages a collection of
 * {@code PurificationUnit} objects that are marked as ready to be
 * shipped. It extends the {@code PurificationUnitList} class, providing
 * functionality specific to units that have passed quality checks and
 * are prepared for dispatch.
 *
 * <p>This class initializes with a header indicating that the units are
 * ready for shipping and includes methods to display the units in a
 * tabular format. Each unit's details include the model, serial number,
 * and the date of the latest test, which is presented in a table when
 * shown through a {@code TextElementPresenter}.</p>
 *
 * @see PurificationUnit
 * @see PurificationUnitTest
 * @see ModelId
 * @see SerialNumber
 * @see PurificationUnitList
 * @see ReportOption
 * @see TableElement
 * @see ParagraphElement
 */
public class ReadyToShipUnitList extends PurificationUnitList {

    public ReadyToShipUnitList() {
        super(new ArrayList<>());
    }

    public ReadyToShipUnitList(Collection<? extends PurificationUnit> units) {
        this(new ArrayList<>(units));
    }

    public ReadyToShipUnitList(List<PurificationUnit> units) {
        super(new ArrayList<>(units.size()));

        for (PurificationUnit unit : units) {
            addUnit(unit);
        }

        setListHeader(ReportOption.READY_TO_SHIP.getLabel() + " Water Purification Units:");
    }

    @Override
    protected void addUnits(TableElement<ParagraphElement> table) {
        for (PurificationUnit unit : this.units) {
            ParagraphElement model = createUnitField(unit.getModel().getValue());
            ParagraphElement serial = createUnitField(unit.getSerialNumber().toString());

            LocalDate latestTestDate = unit.getLatestTest().map(PurificationUnitTest::getDate).orElse(null);
            ParagraphElement testDate = createUnitField(StringUtils.formatDate(latestTestDate));

            table.addRow(model, serial, testDate);
        }
    }

    @Override
    protected void addUnitHeaders(TableElement<ParagraphElement> table) {
        ParagraphElement modelHeader = createTableHeader("Model");
        modelHeader.getStyle().setMinimumWidth(ModelId.MAX_CHAR_COUNT);

        ParagraphElement serialHeader = createTableHeader("Serial");
        serialHeader.getStyle().setMinimumWidth(SerialNumber.MAX_DIGIT_COUNT);

        ParagraphElement testDateHeader = createTableHeader("Test Date");
        testDateHeader.getStyle().setMinimumWidth(11);

        table.addRow(modelHeader, serialHeader, testDateHeader);
    }

    @Override
    public void addUnit(PurificationUnit unit) {
        if (ReportOption.READY_TO_SHIP.getProductResolver().test(unit)) {
            super.addUnit(unit);
        }
    }
}
