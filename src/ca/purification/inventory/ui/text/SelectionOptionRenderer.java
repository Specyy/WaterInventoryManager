package ca.purification.inventory.ui.text;

import ca.purification.inventory.ui.element.UIElement;
import ca.purification.inventory.ui.element.ParagraphElement;
import ca.purification.inventory.ui.element.SelectionOption;

/**
 * The {@code SelectionOptionRenderer} class is responsible for rendering 
 * {@link SelectionOption} instances. It handles the display of selection 
 * options, allowing for both labeled and value-based representations 
 * of options in the UI.
 * 
 * @see ca.purification.inventory.ui.element.SelectionElement
 * @see SelectionOption
 */
public class SelectionOptionRenderer extends TextElementRenderer {
    private final ParagraphRenderer optionRenderer = new ParagraphRenderer();

    private final TextElementRenderer nestedRenderer;

    public SelectionOptionRenderer(TextElementRenderer nestedRenderer) {
        this.nestedRenderer = nestedRenderer;
    }

    @Override
    public boolean accepts(Class<? extends UIElement> elementType) {
        return elementType == SelectionOption.class;
    }

    @Override
    protected String renderContent(UIElement element) {
        if (!(element instanceof SelectionOption<?> option)) {
            return null;
        }

        if (option.getLabel() == null) {
            if (option.getValue() instanceof UIElement valueElement) {
                return nestedRenderer.render(valueElement);
            } else if (option.getValue() instanceof String value) {
                return value.isEmpty() ? "" : optionRenderer.render(new ParagraphElement(value));
            }

            return "";
        }

        return option.getLabel().isEmpty() ? "" : optionRenderer.render(new ParagraphElement(option.getLabel()));
    }
}
