package ca.purification.inventory.ui.text;

import ca.purification.inventory.ui.InvalidElementException;
import ca.purification.inventory.ui.element.*;
import ca.purification.inventory.util.StringUtils;

import java.util.Optional;
import java.util.OptionalInt;

/**
 * The {@code SelectionRenderer} class is responsible for rendering 
 * {@link SelectionElement} instances, allowing users to select from 
 * a list of options. It manages the display of selection prompts 
 * and the processing of user input to select the desired option.
 * 
 * @see SelectionOption
 */
public class SelectionRenderer extends TextElementRenderer {
    private final ListRenderer listRenderer;
    private final PromptRenderer promptRenderer;
    private final TextElementRenderer nestedRenderer;

    private SelectionOptionNumberGenerator optionNumberGenerator;

    private static final SelectionOptionNumberGenerator defaultNumberGenerator =
            new SelectionOptionNumberGenerator() {
                @Override
                public OptionalInt convert(String marker) {
                    String selectedMark = marker.strip();

                    if (!StringUtils.isUnsignedIntegral(selectedMark) || selectedMark.length() > 10) {
                        return OptionalInt.empty();
                    }

                    try {
                        return OptionalInt.of(Integer.parseUnsignedInt(selectedMark) - 1);
                    } catch (NumberFormatException ex) {
                        // Number is too large for some reason...
                        return OptionalInt.empty();
                    }
                }

                @Override
                public String generate(int index) {
                    return String.valueOf(index + 1);
                }
            };

    public SelectionRenderer(TextElementRenderer nestedRenderer) {
        this(nestedRenderer, defaultNumberGenerator);
    }

    public SelectionRenderer(TextElementRenderer nestedRenderer,
                             SelectionOptionNumberGenerator optionNumberGenerator) {
        this.nestedRenderer = nestedRenderer;
        this.listRenderer = new ListRenderer(nestedRenderer);
        this.promptRenderer = new PromptRenderer();
        this.optionNumberGenerator = optionNumberGenerator;
    }

    @Override
    public boolean accepts(Class<? extends UIElement> elementType) {
        return elementType == SelectionElement.class;
    }

    @Override
    public void render(TextElementPresenter presenter, UIElement element) {
        if (!(element instanceof SelectionElement<?> selection)) {
            throw new InvalidElementException(element);
        }

        super.render(presenter, selection);

        PromptElement<Integer> promptElement = getSelectionPrompt(selection);
        promptRenderer.render(presenter, promptElement);

        Optional<Integer> result = promptElement.getResult();
        assert result.isPresent();

        selection.setSelectedOption(promptElement.getResult().orElseThrow());
    }

    @Override
    protected String renderContent(UIElement element) {
        if (!(element instanceof SelectionElement<?> selection)) {
            return null;
        }

        StringBuilder selectionBuilder = new StringBuilder();
        selectionBuilder.append(nestedRenderer.render(selection.getLabel()));

        ListElement<SelectionOption<?>> selectionList = getSelectionList(selection);
        selectionBuilder.append(listRenderer.render(selectionList));

        return selectionBuilder.toString();
    }

    protected ListElement<SelectionOption<?>> getSelectionList(SelectionElement<?> selection) {
        ListElement<SelectionOption<?>> selectionList = new ListElement<>();

        selectionList.setMarkerGenerator(index -> optionNumberGenerator.generate(index) + ". ");
        selectionList.addAll(selection);

        return selectionList;
    }

    protected PromptElement<Integer> getSelectionPrompt(SelectionElement<?> selection) {
        String prompt = "> ";
        String retryPrompt = getRetryPrompt(selection, prompt);

        return new PromptElement<>(prompt, retryPrompt,
                input -> convertInputToIndex(selection, input));
    }


    private Optional<Integer> convertInputToIndex(SelectionElement<?> selection, String input) {
        OptionalInt convertedIndex = optionNumberGenerator.convert(input);
        if (convertedIndex.isEmpty()) {
            return Optional.empty();
        }

        int index = convertedIndex.orElseThrow();
        if (index < 0 || index >= selection.size()) {
            return Optional.empty();
        }

        return Optional.of(index);
    }

    private String getRetryPrompt(SelectionElement<?> selection, String originalPrompt) {
        if (selection.size() <= 1) {
            return "Error: Please enter a valid selection\n" + originalPrompt;
        }

        return String.format("Error: Please enter a selection between %s and %s%n%s",
                optionNumberGenerator.generate(0),
                optionNumberGenerator.generate(selection.size() - 1),
                originalPrompt);
    }

    public SelectionOptionNumberGenerator getOptionNumberGenerator() {
        return optionNumberGenerator;
    }

    public void setOptionNumberGenerator(SelectionOptionNumberGenerator optionNumberGenerator) {
        this.optionNumberGenerator = optionNumberGenerator;
    }
}
