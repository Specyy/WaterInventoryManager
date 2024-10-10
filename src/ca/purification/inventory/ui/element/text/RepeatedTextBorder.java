package ca.purification.inventory.ui.element.text;

import ca.purification.inventory.ui.element.BoxMetrics;
import ca.purification.inventory.ui.element.ElementBorder;

/**
 * The {@code RepeatedTextBorder} class represents a text-based border for UI elements.
 *
 * <p>This class extends {@link ElementBorder} and allows for the creation of borders
 * using a specified character repeated to form the border. The border can be customized
 * using different border characters and box metrics.</p>
 *
 * <p>Examples of use:
 * <pre>
 *     ParagraphElement paragraph = new Paragraph("Hello World");
 *     RepeatedTextBorder customBorder = new RepeatedTextBorder(new BoxMetrics(1), '#');
 *     
 *     paragraph.getStyle().setBorder(customBorder);
 *     
 *     Presenter presenter = new TextPresenter();
 *     presenter.push(paragraph);
 *     
 *     // Output: #############
 *     //         #Hello World#
 *     //         #############
 * </pre>
 * </p>
 */
public class RepeatedTextBorder extends ElementBorder {
    private static final char DEFAULT_BORDER_CHAR = '*';
    private char borderChar;

    public RepeatedTextBorder() {
        this(new BoxMetrics());
    }

    public RepeatedTextBorder(BoxMetrics metrics) {
        this(metrics, DEFAULT_BORDER_CHAR);
    }

    public RepeatedTextBorder(char borderChar) {
        this(new BoxMetrics(0), borderChar);
    }

    public RepeatedTextBorder(BoxMetrics metrics, char borderChar) {
        super(metrics);
        setBorderChar(borderChar);
    }

    public char getBorderChar() {
        return borderChar;
    }

    public void setBorderChar(char borderChar) {
        if (Character.isWhitespace(borderChar)) {
            throw new IllegalArgumentException("Cannot have a white-space border!");
        }

        this.borderChar = borderChar;
    }
}
