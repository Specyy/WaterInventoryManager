package ca.purification.inventory.ui.element;

import java.util.Optional;
import java.util.function.Function;


/**
 * Represents a user prompt that requests input through a UI element.
 * A {@link PromptElement} allows for the validation and conversion of user input
 * to a specified type {@link T}, handling retries through a provided prompt.
 *
 * <p>This prompt element can display a retry message if the input fails validation.
 * The user is expected to provide input by calling {@link #setResult(String)} explicitly.</p>
 *
 * @param <T> The type of data that the user should enter into this prompt. 
 *            Conversion from text to this {@link T} is managed by the 
 *            {@link #getInputConverter() input converter}.
 *
 * @see UIElement
 */
public class PromptElement<T> extends UIElement {
    private ParagraphElement prompt;
    private ParagraphElement retryPrompt;

    private Function<String, Optional<T>> inputConverter;

    private boolean hasResult;
    private T result;

    public PromptElement(String prompt, Function<String, Optional<T>> inputConverter) {
        this(new ParagraphElement(prompt), inputConverter);

        this.prompt.getStyle().setInline(true);
        this.retryPrompt.getStyle().setInline(true);
    }

    public PromptElement(ParagraphElement prompt, Function<String, Optional<T>> inputConverter) {
        this(prompt, new ParagraphElement(prompt.getContent()), inputConverter);
    }

    public PromptElement(String prompt, String retryPrompt, Function<String, Optional<T>> inputConverter) {
        this(new ParagraphElement(prompt), new ParagraphElement(retryPrompt), inputConverter);

        this.prompt.getStyle().setInline(true);
        this.retryPrompt.getStyle().setInline(true);
    }

    public PromptElement(ParagraphElement prompt, ParagraphElement retryPrompt,
                         Function<String, Optional<T>> inputConverter) {
        this.prompt = prompt;
        this.retryPrompt = retryPrompt;
        this.inputConverter = inputConverter;
    }

    public Optional<T> getResult() {
        return hasResult ? Optional.of(result) : Optional.empty();
    }

    public boolean setResult(String input) {
        Optional<T> potentialResult;

        try {
            potentialResult = inputConverter.apply(input);
        } catch (Exception e) {
            this.retryPrompt.setContent(e.getMessage());
            return false;
        }

        if (potentialResult.isEmpty()) {
            return false;
        }

        setResult(potentialResult.orElseThrow());
        return true;
    }

    public void setResult(T result) {
        this.result = result;
        hasResult = true;
    }

    public Function<String, Optional<T>> getInputConverter() {
        return inputConverter;
    }

    public void setInputConverter(Function<String, Optional<T>> inputConverter) {
        this.inputConverter = inputConverter;
        result = null;
        hasResult = false;
    }

    public ParagraphElement getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt.setContent(prompt);
    }

    public void setPrompt(ParagraphElement prompt) {
        this.prompt = prompt;
    }

    public ParagraphElement getRetryPrompt() {
        return retryPrompt;
    }

    public void setRetryPrompt(String retryPrompt) {
        this.retryPrompt.setContent(retryPrompt);
    }

    public void setRetryPrompt(ParagraphElement retryPrompt) {
        this.retryPrompt = retryPrompt;
    }
}

