package ca.purification.inventory.ui.text;

import ca.purification.inventory.ui.InvalidElementException;
import ca.purification.inventory.ui.element.UIElement;
import ca.purification.inventory.ui.element.PromptElement;

/**
 * The {@code PromptRenderer} class is responsible for rendering 
 * {@link PromptElement} instances. It handles the prompt display 
 * and user input processing, enabling interactive text-based 
 * prompts in the UI.
 * 
 * @see PromptElement
 */
public class PromptRenderer extends TextElementRenderer {
    private final ParagraphRenderer promptRenderer = new ParagraphRenderer();

    @Override
    public boolean accepts(Class<? extends UIElement> elementType) {
        return elementType == PromptElement.class;
    }

    @Override
    public void render(TextElementPresenter presenter, UIElement element) {
        if (!(element instanceof PromptElement<?> prompt)) {
            throw new InvalidElementException(element);
        }

        promptRenderer.render(presenter, prompt.getPrompt());
        String input = presenter.getInput().nextLine();

        while (!prompt.setResult(input)) {
            promptRenderer.render(presenter, prompt.getRetryPrompt());
            input = presenter.getInput().nextLine();
        }
    }

    @Override
    protected String renderContent(UIElement element) {
        if (!(element instanceof PromptElement<?> prompt)) {
            return null;
        }

        return promptRenderer.render(prompt.getPrompt());
    }
}
