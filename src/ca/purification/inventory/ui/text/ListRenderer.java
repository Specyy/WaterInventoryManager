package ca.purification.inventory.ui.text;

import ca.purification.inventory.ui.element.UIElement;
import ca.purification.inventory.ui.element.ListElement;
import ca.purification.inventory.util.StringUtils;

/**
 * The {@code ListRenderer} class is responsible for rendering 
 * {@link ListElement} instances in a text-based format. It 
 * utilizes a nested renderer to handle the rendering of 
 * individual list items, allowing for flexibility in how 
 * those items are represented.
 *
 * <p>
 * This renderer supports different types of list items, 
 * using the provided {@link TextElementRenderer} to render 
 * the content of each item appropriately.
 * </p>
 * 
 * @see ListElement
 */
public class ListRenderer extends TextElementRenderer {
    private final TextElementRenderer nestedRenderer;

    public ListRenderer(TextElementRenderer nestedRenderer) {
        this.nestedRenderer = nestedRenderer;
    }

    @Override
    public boolean accepts(Class<? extends UIElement> elementType) {
        return elementType == ListElement.class;
    }

    @Override
    protected String renderContent(UIElement element) {
        if (!(element instanceof ListElement<?> list)) {
            return null;
        }

        return renderList(list);
    }

    private String renderList(ListElement<?> list) {
        StringBuilder listBuilder = new StringBuilder();

        for (int itemNum = 0; itemNum < list.size(); itemNum++) {
            int markerWidth = renderItemMarker(listBuilder, list, itemNum);
            renderItemContent(listBuilder, list, itemNum, markerWidth);
        }

        return listBuilder.toString();
    }

    private void renderItemContent(StringBuilder listBuilder,
                                   ListElement<?> list, int itemNum, int markerWidth) {
        UIElement item = list.get(itemNum);
        String renderedItem = renderListItem(item);
        String[] itemLines = StringUtils.splitLines(renderedItem);

        for (int itemRow = 0; itemRow < itemLines.length; itemRow++) {
            String itemLine = itemLines[itemRow];

            if (itemRow != 0) {
                listBuilder.repeat(' ', markerWidth);
            }

            listBuilder.append(itemLine);

            boolean hasNextLine = item.getStyle().isInline()
                    || itemNum < list.size() - 1
                    || itemRow < itemLines.length - 1;
            
            if (hasNextLine) {
                listBuilder.append(System.lineSeparator());
            }
        }
    }

    private int renderItemMarker(StringBuilder output, ListElement<?> list, int itemNum) {
        String lineMarker = list.getMarkerGenerator().apply(itemNum);
        output.append(lineMarker);
        return lineMarker.length();
    }

    private String renderListItem(UIElement element) {
        TextElementRenderer renderer = (element instanceof ListElement<?>) ? this : nestedRenderer;
        return renderer.render(element);
    }
}
