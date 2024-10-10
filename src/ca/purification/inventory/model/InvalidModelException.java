package ca.purification.inventory.model;

/**
 * The {@code InvalidModelException} class represents an exception that is thrown when an invalid {@code modelId}
 * is encountered, such as when its length exceeds the maximum allowable length.
 *
 * <p>This exception is a specialized form of {@link IllegalArgumentException} and includes the problematic
 * {@code modelId} that caused the exception, making it easier to identify the source of the issue.</p>
 *
 * <p>It is typically used in scenarios where {@link ModelId} validation fails.</p>
 *
 * @see ModelId
 * @see IllegalArgumentException
 */
public class InvalidModelException extends IllegalArgumentException {
    private final String modelId;

    public InvalidModelException(String modelId) {
        this("Model ID Error: Length exceeds maximum length.", modelId);
    }

    public InvalidModelException(String message, String modelId) {
        super(message);
        this.modelId = modelId;
    }

    public String getModelId() {
        return modelId;
    }
}
