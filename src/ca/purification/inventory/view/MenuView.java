package ca.purification.inventory.view;

import ca.purification.inventory.ui.element.ParagraphElement;
import ca.purification.inventory.ui.element.SelectionElement;
import ca.purification.inventory.ui.element.UIElement;
import ca.purification.inventory.ui.text.TextElementPresenter;
import ca.purification.inventory.util.LazyDependency;

import java.util.Optional;

/**
 * The {@code MenuView} class presents a main menu interface to the user, 
 * allowing them to navigate various functionalities of the application. 
 * Users can choose from options such as reading a JSON input file, displaying 
 * information on a unit, creating a new unit, testing a unit, shipping a unit, 
 * printing reports, setting the report sort order, and exiting the application.
 *
 * <p>This class utilizes a {@code SelectionElement} to manage the menu options 
 * and interacts with the corresponding view classes through {@code LazyDependency}. 
 * The selected option resolves to the appropriate view for execution.</p>
 *
 * @see ReadFileView
 * @see CreateUnitView
 * @see DisplayUnitView
 * @see TestUnitView
 * @see ShipUnitView
 * @see PrintReportView
 * @see ReorderReportsView
 * @see LazyDependency
 * @see SelectionElement
 * @see ParagraphElement
 */
public class MenuView extends View {
    private final TextElementPresenter elementPresenter = new TextElementPresenter();

    private final SelectionElement<LazyDependency<View>> selectionMenu;

    public MenuView(LazyDependency<ReadFileView> readFileView,
                    LazyDependency<CreateUnitView> createUnitView,
                    LazyDependency<DisplayUnitView> displayUnitView,
                    LazyDependency<TestUnitView> testUnitView,
                    LazyDependency<ShipUnitView> shipUnitView,
                    LazyDependency<PrintReportView> printReportView,
                    LazyDependency<ReorderReportsView> reorderReportsView) {
        selectionMenu = new SelectionElement<>();

        selectionMenu.addOption("Read JSON input file.", readFileView.cast())
                .addOption("Display info on a unit.", displayUnitView.cast())
                .addOption("Create new unit.", createUnitView.cast())
                .addOption("Test a unit.", testUnitView.cast())
                .addOption("Ship a unit.", shipUnitView.cast())
                .addOption("Print report.", printReportView.cast())
                .addOption("Set report sort order.", reorderReportsView.cast())
                .addOption("Exit.", null);

        selectionMenu.setLabel(getSelectionHeader());
    }

    private UIElement getSelectionHeader() {
        ParagraphElement selectionHeader = new ParagraphElement("Main Menu");
        selectionHeader.getStyle().getPadding().setHorizontal(1);
        selectionHeader.getStyle().getMargin().setTop(1);
        selectionHeader.getStyle().setBorder(1);
        return selectionHeader;
    }

    @Override
    public Optional<View> show() {
        elementPresenter.push(selectionMenu);

        assert selectionMenu.hasSelectedOption();
        LazyDependency<View> selectedView = selectionMenu.getSelectedOption().getValue();

        return selectedView == null ? Optional.empty() : Optional.of(selectedView.resolve());
    }
}