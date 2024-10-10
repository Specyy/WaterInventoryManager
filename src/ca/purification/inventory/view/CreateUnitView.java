package ca.purification.inventory.view;

import ca.purification.inventory.model.InvalidModelException;
import ca.purification.inventory.model.InvalidSerialNumberException;
import ca.purification.inventory.model.ModelId;
import ca.purification.inventory.model.SerialNumber;
import ca.purification.inventory.ui.element.ParagraphElement;
import ca.purification.inventory.ui.element.PromptElement;
import ca.purification.inventory.ui.text.TextElementPresenter;
import ca.purification.inventory.util.LazyDependency;
import ca.purification.inventory.viewmodel.CreateUnitViewModel;

import java.util.Optional;

/**
 * The {@code CreateUnitView} class provides a user interface for creating 
 * new purification units by prompting the user to enter product information, 
 * specifically the model ID and serial number. It validates the user input 
 * to ensure it adheres to the required formats and displays appropriate error 
 * messages when invalid input is provided.
 *
 * <p>This view utilizes {@code PromptElement} to handle user input for both 
 * the model ID and serial number, allowing users to retry upon input errors. 
 * If the user submits an empty line, the creation process is aborted, and 
 * the main menu is displayed.</p>
 *
 * @see View
 * @see CreateUnitViewModel
 * @see ModelId
 * @see SerialNumber
 * @see InvalidModelException
 * @see InvalidSerialNumberException
 */
public class CreateUnitView extends View {
    private final CreateUnitViewModel viewModel;
    private final LazyDependency<MenuView> mainMenu;

    private final TextElementPresenter elementPresenter;

    private final ParagraphElement instructionsText =
            new ParagraphElement("Enter product info; blank line to quit.");
    private final PromptElement<Optional<ModelId>> modelIdPrompt =
            new PromptElement<>("Model: ", this::getModelId);
    private final PromptElement<Optional<SerialNumber>> serialNumberPrompt =
            new PromptElement<>("Serial number: ", this::getSerialNumber);

    public CreateUnitView(CreateUnitViewModel viewModel,
                          TextElementPresenter presenter, 
                          LazyDependency<MenuView> menuView) {
        super(viewModel);
        this.viewModel = viewModel;
        this.elementPresenter = presenter;
        this.mainMenu = menuView;
    }

    private Optional<Optional<ModelId>> getModelId(String input) {
        if (input.isEmpty()) {
            return Optional.of(Optional.empty());
        }

        try {
            return Optional.of(Optional.of(new ModelId(input)));
        } catch (InvalidModelException e) {
            String invalidModelText = String.format("Unable to add the product.%n" +
                    "     '%s'%n" +
                    "Please try again.%n%s", e.getMessage(), modelIdPrompt.getPrompt());
            modelIdPrompt.setRetryPrompt(invalidModelText);
        }

        return Optional.empty();
    }

    private Optional<Optional<SerialNumber>> getSerialNumber(String input) {
        if (input.isEmpty()) {
            return Optional.of(Optional.empty());
        }

        try {
            return Optional.of(Optional.of(new SerialNumber(input)));
        } catch (InvalidSerialNumberException e) {
            String invalidSerialNumberText = String.format("Unable to add the product.%n" +
                    "     '%s'%n" +
                    "Please try again.%n%s", e.getMessage(), serialNumberPrompt.getPrompt());
            serialNumberPrompt.setRetryPrompt(invalidSerialNumberText);
        }

        return Optional.empty();
    }

    @Override
    public Optional<View> show() {
        elementPresenter.push(instructionsText);

        elementPresenter.push(modelIdPrompt);
        Optional<Optional<ModelId>> potentialModelId = modelIdPrompt.getResult();
        assert potentialModelId.isPresent();
        Optional<ModelId> modelId = potentialModelId.orElseThrow();

        if (modelId.isEmpty()) {
            return Optional.of(mainMenu.resolve());
        }

        elementPresenter.push(serialNumberPrompt);
        Optional<Optional<SerialNumber>> potentialSerialNumber = serialNumberPrompt.getResult();
        assert potentialSerialNumber.isPresent();
        Optional<SerialNumber> serialNumber = potentialSerialNumber.orElseThrow();

        if (serialNumber.isEmpty()) {
            return Optional.of(mainMenu.resolve());
        }

        viewModel.setCreatedUnit(modelId.orElseThrow(), serialNumber.orElseThrow());

        return Optional.of(mainMenu.resolve());
    }
}
