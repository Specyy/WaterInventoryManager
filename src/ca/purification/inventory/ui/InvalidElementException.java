package ca.purification.inventory.ui;

import ca.purification.inventory.ui.element.UIElement;

/**
 * The {@code InvalidElementException} is an exception thrown to indicate 
 * that a specific {@code UIElement} type cannot be rendered. This 
 * exception extends {@link IllegalArgumentException} to provide 
 * a clear indication of the issue related to invalid arguments.
 *
 * @see UIElement
 * @see IllegalArgumentException
 */
public class InvalidElementException extends IllegalArgumentException {
    public InvalidElementException() {
    }

    public InvalidElementException(UIElement element) {
        this(element.getClass());
    }

    public InvalidElementException(Class<? extends UIElement> elementType) {
        super(String.format("Cannot render element type: %s", elementType));
    }
}
