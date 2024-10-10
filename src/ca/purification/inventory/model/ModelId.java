package ca.purification.inventory.model;

/**
 * The {@code ModelId} class represents a unique identifier for a model within the inventory system.
 *
 * <p>A {@code ModelId} is represented by a string and is subject to a maximum character limit of 
 * {@value #MAX_CHAR_COUNT} characters. If the provided value exceeds this limit, an {@link InvalidModelException}
 * is thrown. This class implements {@link Comparable} to allow for comparison between model IDs.</p>
 *
 * <p>The {@code ModelId} also supports standard object operations such as {@code equals}, {@code hashCode}, 
 * and {@code toString} for proper handling in collections and debugging output.</p>
 *
 * @see Comparable
 */
public class ModelId implements Comparable<ModelId> {
    public static final int MAX_CHAR_COUNT = 10;

    private final String value;

    public ModelId(String value) {
        if (!isValid(value)) {
            throw new InvalidModelException(value);
        }

        this.value = value;
    }

    private static boolean isValid(String value) {
        return value == null || value.length() <= MAX_CHAR_COUNT;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (obj instanceof ModelId modelId) {
            return value.equals(modelId.value);
        }

        return obj instanceof String id && value.equals(id);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public int compareTo(ModelId o) {
        return value.compareTo(o.value);
    }
}
