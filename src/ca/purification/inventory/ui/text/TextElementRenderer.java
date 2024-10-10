package ca.purification.inventory.ui.text;

import ca.purification.inventory.ui.InvalidElementException;
import ca.purification.inventory.ui.element.UIElement;

/**
 * The {@code TextElementRenderer} class serves as an abstract base for rendering 
 * {@code UIElement} instances into text format. It provides a framework for subclasses 
 * to implement specific rendering logic while ensuring consistency in handling UI 
 * elements' styles through the {@code BoxModelRenderer}.
 *
 * <p>Subclasses must define the {@code accepts} method to specify which element types 
 * they can render and implement the {@code renderContent} method to provide the 
 * actual rendering logic. If rendering fails, an {@code InvalidElementException}
 * is thrown to indicate the issue with the element.
 *
 * @see UIElement
 * @see BoxModelRenderer
 * @see InvalidElementException
 */
public abstract class TextElementRenderer {
    protected BoxModelRenderer boxModelRenderer;

    public TextElementRenderer() {
        this(new BoxModelRenderer());
    }

    public TextElementRenderer(BoxModelRenderer boxModelRenderer) {
        this.boxModelRenderer = boxModelRenderer;
    }

    public abstract boolean accepts(Class<? extends UIElement> elementType);

    public void render(TextElementPresenter presenter, UIElement element) {
        String renderedElement = render(element);

        if (renderedElement == null) {
            throw new InvalidElementException(element);
        }

        presenter.getOutput().print(renderedElement);
    }

    public String render(UIElement element) {
        String elementContent = renderContent(element);

        if (elementContent == null || boxModelRenderer == null) {
            return elementContent;
        }

        return boxModelRenderer.render(element.getStyle(), elementContent);
    }

    protected abstract String renderContent(UIElement element);
}
