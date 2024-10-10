package ca.purification.inventory.view.helper;

import ca.purification.inventory.model.ModelId;
import ca.purification.inventory.model.PurificationUnit;
import ca.purification.inventory.model.SerialNumber;
import ca.purification.inventory.ui.element.ElementAlignment;
import ca.purification.inventory.ui.element.ElementStyle;
import ca.purification.inventory.ui.element.ParagraphElement;
import ca.purification.inventory.ui.element.TableElement;
import ca.purification.inventory.ui.element.text.RepeatedTextBorder;
import ca.purification.inventory.ui.text.TextElementPresenter;
import ca.purification.inventory.util.StringUtils;

import java.util.*;

/**
 * The {@code PurificationUnitList} class manages a collection of 
 * {@code PurificationUnit} objects, providing functionality for adding, 
 * removing, sorting, and displaying purification units in a tabular format. 
 * This class is responsible for rendering the list of units, including 
 * handling cases where no units are present. It provides methods to 
 * customize the list header and the message displayed when no units are found.
 *
 * <p>The units can be displayed using a {@code TextElementPresenter}, 
 * which pushes the list header and a table of units or a message if no 
 * units are available. The table consists of columns for model, serial 
 * number, number of tests, and ship date.</p>
 *
 * @see PurificationUnit
 * @see ModelId
 * @see SerialNumber
 * @see TextElementPresenter
 * @see TableElement
 * @see ElementAlignment
 */
public class PurificationUnitList {
    protected final List<PurificationUnit> units;

    private ParagraphElement noUnitsText = new ParagraphElement("No units found.");
    private ParagraphElement listHeader;

    public PurificationUnitList() {
        this(new ArrayList<>());
    }

    public PurificationUnitList(Collection<? extends PurificationUnit> units) {
        this(new ArrayList<>(units));
    }

    public PurificationUnitList(List<PurificationUnit> units) {
        this.units = units;
        
        listHeader = new ParagraphElement("List of Water Purification Units:");
        listHeader.getStyle().getMargin().setTop(1);
        listHeader.getStyle().getBorder().setBottom(1);
    }

    public void addUnit(PurificationUnit unit) {
        units.add(unit);
    }

    public void sortUnits(Comparator<PurificationUnit> comparator) {
        units.sort(comparator);
    }

    public boolean removeUnit(PurificationUnit unit) {
        return units.remove(unit);
    }

    public void show(TextElementPresenter presenter) {
        presenter.push(listHeader);
        presenter.push(units.isEmpty() ? noUnitsText : getUnitTable());
    }

    protected TableElement<ParagraphElement> getUnitTable() {
        TableElement<ParagraphElement> table = new TableElement<>();

        addUnitHeaders(table);
        addUnits(table);

        return table;
    }

    protected void addUnits(TableElement<ParagraphElement> table) {
        for (PurificationUnit unit : units) {
            ParagraphElement model = createUnitField(unit.getModel().getValue());
            ParagraphElement serial = createUnitField(unit.getSerialNumber().toString());
            ParagraphElement testCount = createUnitField(String.valueOf(unit.getTests().size()));
            ParagraphElement dateShipped = createUnitField(StringUtils.formatDate(unit.getDateShipped()));

            table.addRow(model, serial, testCount, dateShipped);
        }
    }

    protected ParagraphElement createUnitField(String value) {
        ParagraphElement field = new ParagraphElement(value);
        field.getStyle().setAlignment(ElementAlignment.RIGHT);

        return field;
    }

    protected void addUnitHeaders(TableElement<ParagraphElement> table) {
        ParagraphElement modelHeader = createTableHeader("Model");
        modelHeader.getStyle().setMinimumWidth(ModelId.MAX_CHAR_COUNT);

        ParagraphElement serialHeader = createTableHeader("Serial");
        serialHeader.getStyle().setMinimumWidth(SerialNumber.MAX_DIGIT_COUNT);

        ParagraphElement testCountHeader = createTableHeader("# Tests");
        testCountHeader.getStyle().setMinimumWidth(10);

        ParagraphElement shipDateHeader = createTableHeader("Ship Date");
        shipDateHeader.getStyle().setMinimumWidth(10);

        table.addRow(modelHeader, serialHeader, testCountHeader, shipDateHeader);
    }

    protected ParagraphElement createTableHeader(String header) {
        ParagraphElement tableHeader = new ParagraphElement(header);
        ElementStyle headerStyle = tableHeader.getStyle();

        headerStyle.setBorder(new RepeatedTextBorder('-'));
        headerStyle.getBorder().setBottom(1);
        headerStyle.setAlignment(ElementAlignment.RIGHT);

        return tableHeader;
    }

    public void setListHeader(ParagraphElement listHeader) {
        this.listHeader = listHeader;
    }

    public void setListHeader(String headerText) {
        this.listHeader.setContent(headerText);
    }

    public ParagraphElement getListHeader() {
        return listHeader;
    }

    public ParagraphElement getNoUnitsText() {
        return noUnitsText;
    }

    public void setNoUnitsText(ParagraphElement noUnitsText) {
        this.noUnitsText = noUnitsText;
    }
}
