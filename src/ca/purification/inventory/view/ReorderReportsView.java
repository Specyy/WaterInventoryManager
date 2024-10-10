package ca.purification.inventory.view;

import ca.purification.inventory.ui.element.ParagraphElement;
import ca.purification.inventory.ui.element.SelectionElement;
import ca.purification.inventory.ui.text.TextElementPresenter;
import ca.purification.inventory.util.LazyDependency;
import ca.purification.inventory.viewmodel.ReorderReportsViewModel;
import ca.purification.inventory.viewmodel.UnitSortOrder;

import java.util.Optional;

/**
 * The {@code ReorderReportsView} class represents a user interface for 
 * selecting the sort order for reorder reports. It allows users to choose 
 * from various sorting options, such as sorting by serial number, model, 
 * or the most recent test date. The selected sort order is then set in 
 * the {@code ReorderReportsViewModel}. After making a selection, the 
 * view navigates back to the main menu.
 *
 * @see ReorderReportsViewModel
 * @see UnitSortOrder
 * @see LazyDependency
 * @see MenuView
 */
public class ReorderReportsView extends View {
    private final ReorderReportsViewModel viewModel;
    private final LazyDependency<MenuView> mainMenu;

    private final TextElementPresenter elementPresenter;

    private final SelectionElement<UnitSortOrder> sortOrderSelection;

    public ReorderReportsView(ReorderReportsViewModel viewModel, 
                              TextElementPresenter presenter,
                              LazyDependency<MenuView> menuView) {
        super(viewModel);
        this.viewModel = viewModel;
        this.elementPresenter = presenter;
        this.mainMenu = menuView;

        sortOrderSelection = new SelectionElement<>();

        ParagraphElement sortOrderHeader = new ParagraphElement("Select desired report sort order:");
        sortOrderHeader.getStyle().getPadding().setHorizontal(1);
        sortOrderHeader.getStyle().setBorder(1);
        sortOrderSelection.setLabel(sortOrderHeader);

        sortOrderSelection.addOption("Sort by serial number", UnitSortOrder.SERIAL_NUMBER)
                .addOption("Sort by model, then serial number.", UnitSortOrder.MODEL)
                .addOption("Sort by most recent test date.", UnitSortOrder.TEST_DATE)
                .addOption("Cancel", null);
    }


    @Override
    public Optional<View> show() {
        elementPresenter.push(sortOrderSelection);
        assert sortOrderSelection.hasSelectedOption();

        UnitSortOrder sortOrder = sortOrderSelection.getSelectedOption().getValue();
        if (sortOrder != null) {
            viewModel.setSortOrder(sortOrder);
        }

        return Optional.of(mainMenu.resolve());
    }
}
