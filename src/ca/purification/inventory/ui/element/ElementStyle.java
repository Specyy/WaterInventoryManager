package ca.purification.inventory.ui.element;

/**
 * The {@code ElementStyle} class represents the styling properties of a UI element,
 * including padding, margin, border, alignment, minimum width, and inline status.
 *
 * <p>This class provides methods to customize the visual appearance and layout
 * of UI elements, enabling flexible and responsive designs.</p>
 * 
 * @see UIElement
 */
public class ElementStyle {
    private BoxMetrics padding;
    private BoxMetrics margin;
    private ElementBorder border;
    private ElementAlignment alignment = ElementAlignment.LEFT;

    private int minWidth;
    private boolean isInline;

    public ElementStyle() {
        this(new BoxMetrics(), new BoxMetrics(), new ElementBorder(0));
    }
    
    public ElementStyle(BoxMetrics padding, BoxMetrics margin, ElementBorder border) {
        this.padding = padding;
        this.margin = margin;
        this.border = border;
    }

    public BoxMetrics getPadding() {
        return padding;
    }

    public void setPadding(BoxMetrics padding) {
        this.padding = padding;
    }

    public void setPadding(int padding) {
        this.padding.set(padding);
    }

    public BoxMetrics getMargin() {
        return margin;
    }

    public void setMargin(BoxMetrics margin) {
        this.margin = margin;
    }

    public void setMargin(int margin) {
        this.margin.set(margin);
    }

    public ElementBorder getBorder() {
        return border;
    }

    public void setBorder(ElementBorder border) {
        this.border = border;
    }

    public void setBorder(int border) {
        this.border.set(border);
    }

    public int getMinimumWidth() {
        return minWidth;
    }

    public void setMinimumWidth(int minWidth) {
        this.minWidth = minWidth;
    }

    public boolean isInline() {
        return isInline;
    }

    public void setInline(boolean inline) {
        isInline = inline;
    }

    public ElementAlignment getAlignment() {
        return alignment;
    }

    public void setAlignment(ElementAlignment alignment) {
        this.alignment = alignment;
    }
}
