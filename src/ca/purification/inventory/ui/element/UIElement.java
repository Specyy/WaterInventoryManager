package ca.purification.inventory.ui.element;

/**
 * The {@code UIElement} class serves as an abstract base class for all UI elements,
 * encapsulating their styling properties through an {@link ElementStyle} instance.
 *
 * <p>This class provides methods to get and set the style of UI elements,
 * allowing for consistent styling across different types of elements.</p>
 * 
 * @see ElementStyle
 * @see PromptElement
 * @see ListElement
 * @see ParagraphElement
 * @see SelectionElement
 * @see TableElement
 */
public abstract class UIElement {
    protected ElementStyle style;

    public UIElement() {
        this(new ElementStyle());
    }

    public UIElement(ElementStyle style) {
        this.style = style;
    }

    public ElementStyle getStyle() {
        return style;
    }

    public void setStyle(ElementStyle style) {
        this.style = style;
    }
}
