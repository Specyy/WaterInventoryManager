package ca.purification.inventory.ui.element;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a row in a table, containing columns of elements. 
 * This class implements {@link Iterable<T>} to allow iteration 
 * over its columns.
 *
 * <p>
 * The row can dynamically manage its columns, providing methods 
 * for adding, removing, and retrieving columns. It also supports 
 * filling in columns with a default item if the specified index 
 * exceeds the current number of columns.
 * </p>
 *
 * @param <T> the type of elements contained in the columns of the row
 *
 * @see UIElement
 * @see TableElement
 */
public class TableRow<T extends UIElement> implements Iterable<T> {
    private final List<T> cols;

    public TableRow() {
        cols = new ArrayList<>();
    }

    @SafeVarargs
    public TableRow(T... cols) {
        this.cols = new ArrayList<>(cols.length);

        for (T col : cols) {
            this.addColumn(col);
        }
    }

    public TableRow(Iterable<? extends T> cols) {
        this();
        cols.forEach(this::addColumn);
    }

    public TableRow(Iterator<? extends T> cols) {
        this();
        cols.forEachRemaining(this::addColumn);
    }

    public void setColumn(int col, T item) {
        setColumn(col, item, null);
    }

    public void setColumn(int col, T item, T fillCell) {
        if (col < 0) {
            throw new IndexOutOfBoundsException("Column index must be a positive integer");
        }

        for (int oldColumnCount = cols.size(); oldColumnCount <= col; oldColumnCount++) {
            addColumn(fillCell);
        }

        cols.set(col, item);
    }

    public void addColumn(T col) {
        cols.add(col);
    }

    public void addHeader(T col) {
        BoxMetrics border = col.getStyle().getBorder().getMetrics();
        border.setBottom(border.getBottom() + 1);

        addColumn(col);
    }

    public void removeColumn(int col) {
        cols.remove(col);
    }

    public T getColumn(int col) {
        return cols.get(col);
    }

    public int getColumnCount() {
        return cols.size();
    }

    public T[] toArray(T[] array) {
        return cols.toArray(array);
    }

    @Override
    public Iterator<T> iterator() {
        return cols.iterator();
    }
}
