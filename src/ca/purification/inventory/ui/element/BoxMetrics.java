package ca.purification.inventory.ui.element;

/**
 * The {@code BoxMetrics} class represents the box metrics for UI elements,
 * defining the padding and margins for the top, bottom, left, and right sides.
 *
 * <p>This class provides various constructors for creating box metrics with 
 * different configurations and methods to set and retrieve the values of 
 * each side's metrics.</p>
 *
 * <p>Examples of use:
 * <pre>
 *     BoxMetrics defaultMetrics = new BoxMetrics(); // 0 on all sides
 *     BoxMetrics uniformMetrics = new BoxMetrics(5); // all sides have 5
 *     BoxMetrics specificMetrics = new BoxMetrics(2, 4, 1, 3); // top, bottom, left, right
 * </pre>
 * </p>
 */
public class BoxMetrics {
    private int top, bottom, left, right;

    public BoxMetrics() {
        this(0);
    }

    public BoxMetrics(int value) {
        this(value, value);
    }

    public BoxMetrics(int vertical, int horizontal) {
        this(vertical, vertical, horizontal, horizontal);
    }

    public BoxMetrics(int top, int bottom, int left, int right) {
        this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;
    }

    public void set(int value) {
        setVertical(value);
        setHorizontal(value);
    }

    public void set(int vertical, int horizontal) {
        setVertical(vertical);
        setHorizontal(horizontal);
    }

    public void set(int top, int bottom, int left, int right) {
        setTop(top);
        setBottom(bottom);
        setLeft(left);
        setRight(right);
    }

    public void setVertical(int value) {
        setTop(value);
        setBottom(value);
    }

    public void setHorizontal(int value) {
        setLeft(value);
        setRight(value);
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getBottom() {
        return bottom;
    }

    public void setBottom(int bottom) {
        this.bottom = bottom;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public boolean isZero() {
        return top == 0 && bottom == 0 && left == 0 && right == 0;
    }
}