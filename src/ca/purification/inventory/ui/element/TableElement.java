package ca.purification.inventory.ui.element;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a table in a user interface, containing rows of elements. 
 * This class extends {@link UIElement} and implements {@link Iterable<TableRow>}
 * to allow iteration over its rows.
 *
 * <p>
 * The table can dynamically manage its rows and cells, providing methods 
 * for adding rows, setting cell values, and retrieving cell data. The 
 * table's structure can be easily converted into arrays for various 
 * uses within the application.
 * </p>
 *
 * @param <T> the type of elements contained in each row of the table
 *
 * @see UIElement
 * @see TableRow
 */
public class TableElement<T extends UIElement> extends UIElement implements Iterable<TableRow<T>> {
    protected final List<TableRow<T>> rows;

    public TableElement() {
        this.rows = new ArrayList<>();
    }

    @SafeVarargs
    public TableElement(TableRow<? extends T>... rows) {
        this.rows = new ArrayList<>(rows.length);
        addRows(rows);
    }

    public TableElement(Iterable<TableRow<? extends T>> rows) {
        this();
        rows.forEach(this::addRow);
    }

    public TableElement(Iterator<TableRow<? extends T>> rows) {
        this();
        rows.forEachRemaining(this::addRow);
    }

    @SafeVarargs
    public final TableRow<T> addRow(T... items) {
        TableRow<T> row = new TableRow<>(items);
        this.rows.add(row);

        return row;
    }

    public void addRow(TableRow<? extends T> row) {
        this.rows.add(new TableRow<>(row));
    }

    @SafeVarargs
    public final void addRows(TableRow<? extends T>... rows) {
        for (TableRow<? extends T> row : rows) {
            addRow(row);
        }
    }

    public TableRow<T> getRow(int row) {
        return rows.get(row);
    }

    public void setCell(int row, int col, T item) {
        setCell(row, col, item, null);
    }

    public void setCell(int row, int col, T item, T fillCell) {
        if (row < 0) {
            throw new IndexOutOfBoundsException("Row index must be a positive integer");
        }

        for (int oldRowCount = rows.size(); oldRowCount <= row; oldRowCount++) {
            rows.add(new TableRow<>());
        }

        getRow(row).setColumn(col, item, fillCell);
    }

    public T getCell(int row, int col) {
        return getRow(row).getColumn(col);
    }

    public int getRowCount() {
        return rows.size();
    }

    public int getColumnCount() {
        return this.rows.stream()
                .mapToInt(TableRow::getColumnCount)
                .max()
                .orElse(0);
    }

    public int getCellCount() {
        return getRowCount() * getColumnCount();
    }

    public UIElement[][] toArray() {
        final int rowCount = getRowCount();
        final int colCount = getColumnCount();
        final UIElement[][] cells = new UIElement[rowCount][colCount];

        fillCells(cells);
        return cells;
    }

    @SuppressWarnings("unchecked")
    public T[][] toArray(T[][] array) {
        final int rowCount = getRowCount();
        final int colCount = getColumnCount();

        if (array.length < rowCount * colCount) {
            Class<? extends UIElement> elementType = (Class<? extends UIElement>)
                    array.getClass().getComponentType().getComponentType();
            array = (T[][]) Array.newInstance(elementType, rowCount, colCount);
        }

        fillCells(array);
        return array;
    }

    private void fillCells(UIElement[][] cells) {
        for (int row = 0; row < cells.length; row++) {
            TableRow<T> tableRow = getRow(row);

            for (int col = 0; col < cells[row].length; col++) {
                cells[row][col] = tableRow.getColumn(col);
            }
        }
    }

    @Override
    public Iterator<TableRow<T>> iterator() {
        return this.rows.iterator();
    }
}
