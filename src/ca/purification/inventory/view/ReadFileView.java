package ca.purification.inventory.view;

import ca.purification.inventory.ui.element.ParagraphElement;
import ca.purification.inventory.ui.element.PromptElement;
import ca.purification.inventory.ui.text.TextElementPresenter;
import ca.purification.inventory.util.LazyDependency;
import ca.purification.inventory.viewmodel.ReadFileViewModel;

import java.io.File;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * The {@code ReadFileView} class provides a user interface for reading 
 * data from a JSON file. It prompts the user for a file path, validates 
 * the input, and attempts to load the file into the associated 
 * {@code ReadFileViewModel}. If the file is successfully read, the 
 * view displays the number of products loaded. Users can cancel the 
 * operation by providing a blank input.
 *
 * <p>In the event of invalid input, the class provides appropriate 
 * feedback and allows users to re-enter the file path until a valid 
 * file is specified or the operation is canceled.</p>
 *
 * @see ReadFileViewModel
 * @see LazyDependency
 * @see MenuView
 * @see PromptElement
 * @see ParagraphElement
 */
public class ReadFileView extends View {
    private final ReadFileViewModel viewModel;
    private final LazyDependency<MenuView> mainMenu;

    private final TextElementPresenter elementPresenter;

    private final String filePromptText = """
            Enter the path to the input JSON file; blank to cancel.
            WARNING: This will replace all current data with data from the file.
            >\s""";
    private final PromptElement<Optional<File>> filePrompt =
            new PromptElement<>(filePromptText, this::getEnteredFile);

    public ReadFileView(ReadFileViewModel viewModel, 
                        TextElementPresenter presenter,
                        LazyDependency<MenuView> menuView) {
        super(viewModel);
        this.viewModel = viewModel;
        this.elementPresenter = presenter;
        this.mainMenu = menuView;
    }

    private Optional<Optional<File>> getEnteredFile(String input) {
        if (input.isEmpty()) {
            return Optional.of(Optional.empty());
        }

        Optional<File> potentialFile = getFile(input);
        return potentialFile.isPresent() ? Optional.of(potentialFile) : Optional.empty();
    }

    private Optional<File> getFile(String filePath) {
        File file;

        String invalidPathPrompt = "Please enter a valid path name.\n> ";
        try {
            file = Paths.get(filePath).toFile();
        } catch (InvalidPathException | UnsupportedOperationException e) {
            filePrompt.setRetryPrompt(invalidPathPrompt);
            return Optional.empty();
        }

        String fileNotFoundText = String.format("Could not find file '%s'.%n" +
                "Please enter another file path.%n" +
                "> ", filePath);
        try {
            if (!file.isFile() || !file.canRead()) {
                filePrompt.setRetryPrompt(fileNotFoundText);
                return Optional.empty();
            }
        } catch (SecurityException e) {
            filePrompt.setRetryPrompt(fileNotFoundText);
            return Optional.empty();
        }

        return Optional.of(file);
    }

    @Override
    public Optional<View> show() {
        showFilePrompt();
        return Optional.of(mainMenu.resolve());
    }

    private void showFilePrompt() {
        while (true) {
            Optional<File> potentialFile = promptForFile();
            if (potentialFile.isEmpty()) {
                break;
            }

            if (processFile(potentialFile.orElseThrow())) {
                break;
            }
        }

        filePrompt.setPrompt(filePromptText);
    }

    private boolean processFile(File file) {
        try {
            viewModel.setUnitFile(file);

            String successText = String.format("Read %d products from JSON file '%s'.",
                    viewModel.getUnitCount(),
                    file.getPath());
            elementPresenter.push(new ParagraphElement(successText));
        } catch (Exception e) {
            String invalidFormatText = "Parsing error: " + e.getMessage() + '\n' +
                    "Please enter another file path.\n" +
                    "> ";
            filePrompt.setPrompt(invalidFormatText);
            return false;
        }

        return true;
    }

    private Optional<File> promptForFile() {
        elementPresenter.push(filePrompt);

        Optional<Optional<File>> potentialFile = filePrompt.getResult();
        assert potentialFile.isPresent();

        return potentialFile.orElseThrow();
    }
}
