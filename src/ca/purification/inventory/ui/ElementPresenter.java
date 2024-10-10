package ca.purification.inventory.ui;

import ca.purification.inventory.ui.element.UIElement;

/**
 * The {@code ElementPresenter} interface defines a contract for presenting 
 * {@code UIElement} instances to the user interface. Implementations of this 
 * interface should provide the means to display UI elements, typically by 
 * pushing them to the output stream or rendering context.
 *
 * @see UIElement
 */
public interface ElementPresenter {
    void push(UIElement element);
}