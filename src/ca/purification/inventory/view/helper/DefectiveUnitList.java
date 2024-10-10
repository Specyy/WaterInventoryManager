package ca.purification.inventory.view.helper;

import ca.purification.inventory.model.ModelId;
import ca.purification.inventory.model.PurificationUnit;
import ca.purification.inventory.model.PurificationUnitTest;
import ca.purification.inventory.model.SerialNumber;
import ca.purification.inventory.ui.element.ElementAlignment;
import ca.purification.inventory.ui.element.ParagraphElement;
import ca.purification.inventory.ui.element.TableElement;
import ca.purification.inventory.util.StringUtils;
import ca.purification.inventory.viewmodel.ReportOption;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * The {@code DefectiveUnitList} class extends {@code PurificationUnitList}
 * to specifically manage a list of defective purification units. It provides
 * functionality to add units, ensuring that only defective units are included
 * based on specified criteria. The class also constructs headers and rows
 * for displaying information about the defective units in a tabular format.
 *
 * <p>This class overrides methods to add unit headers and units to a
 * {@code TableElement}, including fields for model, serial number, test
 * count, latest test date, and comments. It includes constructors to initialize
 * the list with a collection of units and ensures that units are validated as
 * defective upon addition.</p>
 *
 * @see PurificationUnitList
 * @see PurificationUnit
 * @see PurificationUnitTest
 * @see ReportOption
 * @see ModelId
 * @see SerialNumber
 */
public class DefectiveUnitList extends PurificationUnitList {
    public DefectiveUnitList() {
        super(new ArrayList<>());
    }

    public DefectiveUnitList(Collection<? extends PurificationUnit> units) {
        this(new ArrayList<>(units));
    }

    public DefectiveUnitList(List<PurificationUnit> units) {
        super(new ArrayList<>(units.size()));

        for (PurificationUnit unit : units) {
            addUnit(unit);
        }

        setListHeader(ReportOption.DEFECTIVE.getLabel() + " Water Purification Units:");
    }

    @Override
    protected void addUnits(TableElement<ParagraphElement> table) {
        for (PurificationUnit unit : this.units) {
            ParagraphElement model = createUnitField(unit.getModel().getValue());
            ParagraphElement serial = createUnitField(unit.getSerialNumber().toString());
            ParagraphElement testCount = createUnitField(String.valueOf(unit.getTests().size()));

            Optional<PurificationUnitTest> latestTest = unit.getLatestTest();
            LocalDate latestTestDate = latestTest.map(PurificationUnitTest::getDate).orElse(null);
            ParagraphElement testDate = createUnitField(StringUtils.formatDate(latestTestDate));

            String commentsText = latestTest.map(PurificationUnitTest::getComment).orElse("");
            ParagraphElement comments = new ParagraphElement(commentsText);

            table.addRow(model, serial, testCount, testDate, comments);
        }
    }

    @Override
    protected void addUnitHeaders(TableElement<ParagraphElement> table) {
        ParagraphElement modelHeader = createTableHeader("Model");
        modelHeader.getStyle().setMinimumWidth(ModelId.MAX_CHAR_COUNT);

        ParagraphElement serialHeader = createTableHeader("Serial");
        serialHeader.getStyle().setMinimumWidth(SerialNumber.MAX_DIGIT_COUNT);

        ParagraphElement testCountHeader = createTableHeader("# Tests");
        testCountHeader.getStyle().setMinimumWidth(10);

        ParagraphElement testDateHeader = createTableHeader("Test Date");
        testDateHeader.getStyle().setMinimumWidth(11);

        ParagraphElement commentsHeader = createTableHeader("Test comments");
        commentsHeader.getStyle().setAlignment(ElementAlignment.LEFT);

        table.addRow(modelHeader, serialHeader, testCountHeader, testDateHeader, commentsHeader);
    }

    @Override
    public void addUnit(PurificationUnit unit) {
        if (ReportOption.DEFECTIVE.getProductResolver().test(unit)) {
            super.addUnit(unit);
        }
    }
}
