package ca.purification.inventory.view;

import ca.purification.inventory.model.SerialNumber;
import ca.purification.inventory.ui.element.ParagraphElement;
import ca.purification.inventory.ui.element.PromptElement;
import ca.purification.inventory.ui.text.TextElementPresenter;
import ca.purification.inventory.util.LazyDependency;
import ca.purification.inventory.view.helper.SerialNumberPrompt;
import ca.purification.inventory.viewmodel.TestUnitViewModel;

import java.util.Optional;

/**
 * The {@code TestUnitView} class represents a user interface for conducting 
 * tests on units in the application. It allows users to input a serial 
 * number, record test results, and provide comments. This view interacts 
 * with the {@code TestUnitViewModel} to manage the state and behavior 
 * of unit tests. Upon completion, it navigates back to the main menu view.
 *
 * @see TestUnitViewModel
 * @see SerialNumberPrompt
 * @see LazyDependency
 * @see MenuView
 */
public class TestUnitView extends View {
    private final TestUnitViewModel viewModel;
    private final LazyDependency<MenuView> mainMenu;

    private final TextElementPresenter elementPresenter;

    private final SerialNumberPrompt serialNumberPrompt;
    private final PromptElement<Boolean> testPassedPrompt = new PromptElement<>("Pass? (Y/n): ",
            "Error: Please enter [Y]es or [N]o: ", input -> {
        if (input.equalsIgnoreCase("y")) {
            return Optional.of(true);
        }

        if (input.equalsIgnoreCase("n")) {
            return Optional.of(false);
        }

        return Optional.empty();
    });

    private final PromptElement<String> testCommentPrompt = new PromptElement<>("Comment: ", Optional::of);
    private final ParagraphElement confirmationText = new ParagraphElement("Test recorded.");

    public TestUnitView(TestUnitViewModel viewModel, 
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
            promptTestInfo();
        }

        return Optional.of(mainMenu.resolve());
    }

    private void promptTestInfo() {
        elementPresenter.push(testPassedPrompt);
        elementPresenter.push(testCommentPrompt);

        Optional<Boolean> testPassed = testPassedPrompt.getResult();
        Optional<String> testComment = testCommentPrompt.getResult();
        assert testPassed.isPresent();
        assert testComment.isPresent();

        viewModel.setCreatedUnitTest(testPassed.orElseThrow(), testComment.orElseThrow());
        
        elementPresenter.push(confirmationText);
    }
}
