package ca.purification.inventory.view;

import ca.purification.inventory.model.SerialNumber;
import ca.purification.inventory.ui.element.ParagraphElement;
import ca.purification.inventory.ui.text.TextElementPresenter;
import ca.purification.inventory.util.LazyDependency;
import ca.purification.inventory.view.helper.SerialNumberPrompt;
import ca.purification.inventory.viewmodel.ShipUnitViewModel;

import java.util.Optional;

/**
 * The {@code ShipUnitView} class represents a user interface for shipping
 * units in the application. It prompts the user for a serial number
 * corresponding to the unit to be shipped, interacts with the
 * {@code ShipUnitViewModel} to manage the shipping process, and
 * provides confirmation once the unit is successfully shipped.
 * Upon completion, it navigates back to the main menu view.
 *
 * @see ShipUnitViewModel
 * @see SerialNumberPrompt
 * @see LazyDependency
 * @see MenuView
 */
public class ShipUnitView extends View {
    private final ShipUnitViewModel viewModel;
    private final LazyDependency<MenuView> mainMenu;

    private final TextElementPresenter elementPresenter;

    private final SerialNumberPrompt serialNumberPrompt;
    private final ParagraphElement confirmationText = new ParagraphElement("Unit successfully shipped.");

    public ShipUnitView(ShipUnitViewModel viewModel,
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
            viewModel.setShippedUnit(serialNumber.orElseThrow());
            elementPresenter.push(confirmationText);
        }

        return Optional.of(mainMenu.resolve());
    }
}
