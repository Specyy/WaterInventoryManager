package ca.purification.inventory.ui.element;

/**
 * The {@code ElementBorder} class represents the border of a UI element,
 * defined by its metrics in terms of top, bottom, left, and right dimensions.
 *
 * <p>This class provides methods to set and retrieve the border metrics, 
 * allowing for flexible customization of the appearance of UI elements.</p>
 * 
 * @see UIElement
 * @see ElementStyle
 * @see ca.purification.inventory.ui.element.text.RepeatedTextBorder
 */
public class ElementBorder {
    protected BoxMetrics metrics;

    public ElementBorder() {
        this(0);
    }

    public ElementBorder(int value) {
        this(value, value);
    }

    public ElementBorder(int vertical, int horizontal) {
        this(vertical, vertical, horizontal, horizontal);
    }

    public ElementBorder(int top, int bottom, int left, int right) {
        this(new BoxMetrics(top, bottom, left, right));
    }

    public ElementBorder(BoxMetrics metrics) {
        this.metrics = metrics;
    }

    public void set(int value) {
        metrics.set(value);
    }

    public void set(int vertical, int horizontal) {
        metrics.set(vertical, horizontal);
    }

    public void set(int top, int bottom, int left, int right) {
        metrics.set(top, bottom, left, right);
    }

    public void setVertical(int value) {
        metrics.setVertical(value);
    }

    public void setHorizontal(int value) {
        metrics.setHorizontal(value);
    }

    public int getTop() {
        return metrics.getTop();
    }

    public void setTop(int top) {
        metrics.setTop(top);
    }

    public int getBottom() {
        return metrics.getBottom();
    }

    public void setBottom(int bottom) {
        metrics.setBottom(bottom);
    }

    public int getLeft() {
        return metrics.getLeft();
    }

    public void setLeft(int left) {
        metrics.setLeft(left);
    }

    public int getRight() {
        return metrics.getRight();
    }

    public void setRight(int right) {
        metrics.setRight(right);
    }

    public boolean isZero() {
        return metrics.isZero();
    }

    public BoxMetrics getMetrics() {
        return metrics;
    }

    public void setMetrics(BoxMetrics metrics) {
        this.metrics = metrics;
    }
}
