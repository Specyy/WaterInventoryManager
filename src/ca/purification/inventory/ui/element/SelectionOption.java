package ca.purification.inventory.ui.element;

/**
 * Represents a selectable option in a user interface, containing a label and a value.
 * This class extends {@link UIElement} and provides the necessary methods to get and set 
 * the label and value associated with the option.
 *
 * <p>
 * The label is a human-readable description of the option, while the value represents 
 * the data associated with the option. This makes it suitable for use in various UI 
 * components, such as dropdowns or selection menus.
 * </p>
 *
 * @param <T> the type of the value associated with this selection option
 *
 * @see UIElement
 * @see SelectionElement
 */
public class SelectionOption<T> extends UIElement {
    private String label;
    private T value;

    public SelectionOption(String label, T value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
