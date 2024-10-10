package ca.purification.inventory.ui.text;

import ca.purification.inventory.ui.InvalidElementException;
import ca.purification.inventory.ui.element.*;
import ca.purification.inventory.util.LazyDependencyRegistry;

import java.util.Optional;

/**
 * The {@code DefaultElementRenderer} class is responsible for rendering 
 * various types of UI elements in a text-based format. It utilizes a 
 * registry to manage different element renderers, allowing for 
 * dynamic resolution of the appropriate renderer based on the type 
 * of the UI element being rendered.
 *
 * <p>
 * This class extends {@link TextElementRenderer} and supports rendering 
 * for several UI elements, including paragraphs, lists, prompts, 
 * selections, and tables.
 * </p>
 *
 * @see TextElementRenderer
 * @see UIElement
 * @see InvalidElementException
 */
public final class DefaultElementRenderer extends TextElementRenderer {
    private final LazyDependencyRegistry<Class<? extends UIElement>, TextElementRenderer> renderers
            = new LazyDependencyRegistry<>();

    public DefaultElementRenderer() {
        super(null);
        
        renderers.register(ParagraphElement.class, ParagraphRenderer::new)
                .register(ListElement.class, () -> new ListRenderer(this))
                .register(PromptElement.class, PromptRenderer::new)
                .register(SelectionElement.class, () -> new SelectionRenderer(this))
                .register(SelectionOption.class, () -> new SelectionOptionRenderer(this))
                .register(TableElement.class, () -> new TableRenderer(this));
    }

    @Override
    public boolean accepts(Class<? extends UIElement> elementType) {
        return getRenderer(elementType).isPresent();
    }

    @Override
    public void render(TextElementPresenter presenter, UIElement element) {
        getRenderer(element)
                .orElseThrow(() -> new InvalidElementException(element))
                .render(presenter, element);
    }

    @Override
    public String render(UIElement element) {
        return getRenderer(element)
                .orElseThrow(() -> new InvalidElementException(element))
                .render(element);
    }

    @Override
    protected String renderContent(UIElement element) {
        return getRenderer(element)
                .orElseThrow(() -> new InvalidElementException(element))
                .renderContent(element);
    }

    private Optional<TextElementRenderer> getRenderer(UIElement element) {
        return getRenderer(element.getClass());
    }

    @SuppressWarnings("unchecked")
    private Optional<TextElementRenderer> getRenderer(Class<? extends UIElement> elementType) {
        Class<? extends UIElement> currentType = elementType;

        // If no renderer is found for the provided element type, we
        // iteratively attempt to locate a renderer for the element's parent types
        do {
            if (renderers.isRegistered(currentType)) {
                return Optional.of(renderers.resolve(currentType));
            }

            currentType = (Class<? extends UIElement>) currentType.getSuperclass();
        } while (currentType != UIElement.class);

        return Optional.empty();
    }
}
