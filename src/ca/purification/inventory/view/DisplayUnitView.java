package ca.purification.inventory.view;

import ca.purification.inventory.model.PurificationUnit;
import ca.purification.inventory.model.PurificationUnitTest;
import ca.purification.inventory.model.SerialNumber;
import ca.purification.inventory.ui.element.ElementAlignment;
import ca.purification.inventory.ui.element.ElementStyle;
import ca.purification.inventory.ui.element.ParagraphElement;
import ca.purification.inventory.ui.element.TableElement;
import ca.purification.inventory.ui.element.text.RepeatedTextBorder;
import ca.purification.inventory.ui.text.TextElementPresenter;
import ca.purification.inventory.util.LazyDependency;
import ca.purification.inventory.util.StringUtils;
import ca.purification.inventory.view.helper.SerialNumberPrompt;
import ca.purification.inventory.viewmodel.DisplayUnitViewModel;

import java.util.Optional;

/**
 * The {@code DisplayUnitView} class is responsible for displaying detailed 
 * information about a purification unit, including its serial number, model, 
 * ship date, and test results. It allows users to view and select a unit 
 * based on its serial number and presents the relevant details in a structured 
 * format, including headers and a table for test results.
 *
 * <p>This view utilizes a {@code SerialNumberPrompt} to facilitate user input 
 * for selecting a purification unit. Upon selection, it retrieves the unit's 
 * details and associated tests, displaying them in a clear and organized manner 
 * using {@code ParagraphElement} and {@code TableElement} for formatting.</p>
 *
 * @see View
 * @see DisplayUnitViewModel
 * @see SerialNumberPrompt
 * @see PurificationUnit
 * @see PurificationUnitTest
 * @see TableElement
 */
public class DisplayUnitView extends View {
    private final DisplayUnitViewModel viewModel;
    private final LazyDependency<MenuView> mainMenu;

    private final TextElementPresenter elementPresenter;
    private final SerialNumberPrompt serialNumberPrompt;
    private final ParagraphElement noTestsText = new ParagraphElement("No tests found.");

    public DisplayUnitView(DisplayUnitViewModel viewModel, 
                           TextElementPresenter presenter,
                           LazyDependency<MenuView> menuView) {
        super(viewModel);
        this.viewModel = viewModel;
        this.elementPresenter = presenter;
        this.mainMenu = menuView;
        
        this.serialNumberPrompt = new SerialNumberPrompt(viewModel.getUnitManager());
        this.serialNumberPrompt.setUnitSortOrder(viewModel.getUnitSortOrder());
    }

    @Override
    public Optional<View> show() {
        Optional<SerialNumber> serialNumber = serialNumberPrompt.show(elementPresenter);

        if (serialNumber.isPresent()) {
            viewModel.setSelectedUnit(serialNumber.orElseThrow());
            showUnitDetails(viewModel.getSelectedUnit());
            showUnitTests(viewModel.getSelectedUnit());
        }

        return Optional.of(mainMenu.resolve());
    }

    private void showUnitDetails(PurificationUnit unit) {
        String unitDetailsText = String.format("Unit details: %n" +
                        "    Serial: %s%n" +
                        "     Model: %s%n" +
                        " Ship Date: %s",
                unit.getSerialNumber(), unit.getModel(), StringUtils.formatDate(unit.getDateShipped()));


        ParagraphElement unitDetailsElement = new ParagraphElement(unitDetailsText);
        unitDetailsElement.getStyle().getPadding().setTop(1);

        elementPresenter.push(unitDetailsElement);
    }

    private void showUnitTests(PurificationUnit unit) {
        ParagraphElement unitTestsHeader = new ParagraphElement("Tests");
        unitTestsHeader.getStyle().getBorder().setBottom(1);
        elementPresenter.push(unitTestsHeader);

        if (viewModel.getSelectedUnit().getTests().isEmpty()) {
            elementPresenter.push(noTestsText);
        } else {
            TableElement<ParagraphElement> table = new TableElement<>();

            addUnitTestHeaders(table);
            addUnitTests(table, unit);

            elementPresenter.push(table);
        }
    }

    private void addUnitTests(TableElement<ParagraphElement> table, PurificationUnit unit) {
        for (PurificationUnitTest test : unit.getTests()) {
            ParagraphElement date = createUnitTestField(StringUtils.formatDate(test.getDate()));
            ParagraphElement passed = createUnitTestField(test.hasPassed() ? "Passed" : "FAILED");
            ParagraphElement comments = new ParagraphElement(test.getComment());

            table.addRow(date, passed, comments);
        }
    }

    private ParagraphElement createUnitTestField(String value) {
        ParagraphElement dateElement = new ParagraphElement(value);
        dateElement.getStyle().setAlignment(ElementAlignment.RIGHT);

        return dateElement;
    }

    private void addUnitTestHeaders(TableElement<ParagraphElement> table) {
        ParagraphElement dateHeader = createTableHeader("Date");
        dateHeader.getStyle().setMinimumWidth(10);

        ParagraphElement passedHeader = createTableHeader("Passed?");
        passedHeader.getStyle().setMinimumWidth(8);

        ParagraphElement commentsHeader = createTableHeader("Test comments");
        commentsHeader.getStyle().setAlignment(ElementAlignment.LEFT);

        table.addRow(dateHeader, passedHeader, commentsHeader);
    }

    private ParagraphElement createTableHeader(String header) {
        ParagraphElement tableHeader = new ParagraphElement(header);
        ElementStyle headerStyle = tableHeader.getStyle();

        headerStyle.setBorder(new RepeatedTextBorder('-'));
        headerStyle.getBorder().setBottom(1);
        headerStyle.setAlignment(ElementAlignment.RIGHT);

        return tableHeader;
    }
}
