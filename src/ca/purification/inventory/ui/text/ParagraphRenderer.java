package ca.purification.inventory.ui.text;

import ca.purification.inventory.ui.element.ElementAlignment;
import ca.purification.inventory.ui.element.UIElement;
import ca.purification.inventory.ui.element.ParagraphElement;

/**
 * The {@code ParagraphRenderer} class is responsible for rendering 
 * {@link ParagraphElement} instances in a text-based format. 
 * This renderer handles both plain text and styled content, 
 * providing the flexibility to render content based on the 
 * styling properties of the element.
 * 
 * @see ParagraphElement
 */
public class ParagraphRenderer extends TextElementRenderer {
    @Override
    public boolean accepts(Class<? extends UIElement> elementType) {
        return elementType == ParagraphElement.class;
    }

    @Override
    public String render(UIElement element) {
        return isPlainText(element) ?
                renderContent(element) : super.render(element);
    }

    private boolean isPlainText(UIElement element) {
        return element.getStyle().isInline()
                && !boxModelRenderer.isFramedElement(element.getStyle())
                && element.getStyle().getAlignment() == ElementAlignment.LEFT;
    }

    @Override
    protected String renderContent(UIElement element) {
        if (!(element instanceof ParagraphElement paragraph)) {
            return null;
        }

        return paragraph.getContent().replace("\b", "");
    }
}
