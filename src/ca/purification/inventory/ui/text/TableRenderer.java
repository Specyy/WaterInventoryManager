package ca.purification.inventory.ui.text;

import ca.purification.inventory.ui.element.ElementAlignment;
import ca.purification.inventory.ui.element.TableRow;
import ca.purification.inventory.ui.element.UIElement;
import ca.purification.inventory.ui.element.TableElement;
import ca.purification.inventory.util.StringUtils;

/**
 * The {@code TableRenderer} class is responsible for rendering table elements 
 * as text. It utilizes a nested renderer to format the content of each cell 
 * according to its defined style and alignment.
 *
 * <p>This renderer can handle multiple rows and columns, ensuring that the 
 * table is formatted correctly with appropriate spacing and alignment.</p>
 * 
 * @see TableElement
 */
public class TableRenderer extends TextElementRenderer {
    private final TextElementRenderer nestedRenderer;

    public TableRenderer(TextElementRenderer nestedRenderer) {
        this.nestedRenderer = nestedRenderer;
    }

    @Override
    public boolean accepts(Class<? extends UIElement> elementType) {
        return elementType == TableElement.class;
    }

    @Override
    protected String renderContent(UIElement element) {
        if (!(element instanceof TableElement<?> table)) {
            return null;
        }

        return renderTable(new TableInfo(table));
    }

    private String renderTable(TableInfo tableInfo) {
        StringBuilder tableBuilder = new StringBuilder();

        for (int row = 0; row < tableInfo.getRowCount(); row++) {
            renderRow(tableBuilder, tableInfo, row);

            if (row < tableInfo.getRowCount() - 1) {
                tableBuilder.append(System.lineSeparator());
            }
        }

        return tableBuilder.toString();
    }

    private void renderRow(StringBuilder tableBuilder, TableInfo tableInfo, int row) {
        for (int rowLine = 0; rowLine < tableInfo.getRowHeight(row); rowLine++) {
            renderRowLine(tableBuilder, tableInfo, row, rowLine);

            if (rowLine < tableInfo.getRowHeight(row) - 1) {
                tableBuilder.append(System.lineSeparator());
            }
        }
    }

    private void renderRowLine(StringBuilder tableBuilder, TableInfo tableInfo,
                               int row, int rowLine) {
        for (int col = 0; col < tableInfo.getColumnCount(); col++) {
            String[] cellLines = tableInfo.getCellLines(row, col);
            ElementAlignment alignment = ElementAlignment.LEFT;
            String line = "";

            if (rowLine < cellLines.length) {
                UIElement cell = tableInfo.getCell(row, col);
                alignment = cell == null ? alignment : cell.getStyle().getAlignment();
                line = cellLines[rowLine];
            }

            boxModelRenderer.renderAlignedLine(tableBuilder,
                    alignment, line, tableInfo.getTableColumnWidth(col));

            if (col < tableInfo.getColumnCount()) {
                tableBuilder.append("  ");
            }
        }
    }

    /**
     * The {@code TableInfo} class encapsulates information about a 
     * {@code TableElement}, including its rows, columns, and their 
     * respective rendered content.
     * 
     * @see TableElement
     */
    private class TableInfo {
        private final TableElement<?> table;
        private final int rowCount;
        private final int colCount;
        private final String[][][] cellLines;
        private final int[] tableColWidth;
        private final int[] rowHeight;

        public TableInfo(TableElement<?> table) {
            this.table = table;
            this.rowCount = table.getRowCount();
            this.colCount = table.getColumnCount();

            this.cellLines = new String[rowCount][colCount][];
            this.tableColWidth = new int[colCount];
            this.rowHeight = new int[rowCount];

            for (int row = 0; row < rowCount; row++) {
                processRow(row);
            }
        }

        private void processRow(int row) {
            for (int col = 0; col < colCount; col++) {
                UIElement cell = getCell(row, col);

                String renderedCell = cell == null ? "" : nestedRenderer.render(cell);
                String[] cellLines = StringUtils.splitLines(renderedCell);
                int cellWidth = StringUtils.findLongest(cellLines).length();

                this.tableColWidth[col] = Math.max(cellWidth, tableColWidth[col]);
                this.rowHeight[row] = Math.max(cellLines.length, this.rowHeight[row]);
                this.cellLines[row][col] = cellLines;
            }
        }

        public TableElement<?> getTable() {
            return table;
        }

        public UIElement getCell(int row, int col) {
            return getRow(row).getColumn(col);
        }

        public TableRow<?> getRow(int row) {
            return table.getRow(row);
        }

        public int getRowCount() {
            return rowCount;
        }

        public int getColumnCount() {
            return colCount;
        }

        public String[][] getRowLines(int row) {
            return cellLines[row];
        }

        public String[] getCellLines(int row, int col) {
            return getRowLines(row)[col];
        }

        public int getTableColumnWidth(int col) {
            return tableColWidth[col];
        }

        public int getRowHeight(int row) {
            return rowHeight[row];
        }
    }
}
