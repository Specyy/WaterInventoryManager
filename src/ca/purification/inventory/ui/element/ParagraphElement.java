package ca.purification.inventory.ui.element;

/**
 * The {@code ParagraphElement} class represents a paragraph of text within a UI.
 * It extends the {@link UIElement} class and provides functionality to manage
 * and manipulate its textual content.
 *
 * <p>This class allows for appending text, setting content, and retrieving
 * the current content as a string. An empty paragraph can be accessed via the
 * {@link #EMPTY} constant, which provides a default implementation.</p>
 * 
 * @see UIElement
 */
public class ParagraphElement extends UIElement {
    public static final ParagraphElement EMPTY = new ParagraphElement() {
        @Override
        public ParagraphElement append(String content) {
            return this;
        }

        @Override
        public String getContent() {
            return "";
        }

        @Override
        public void setContent(String content) {
            
        }
    };

    private StringBuilder content;

    private ParagraphElement() {
    }

    public ParagraphElement(String content) {
        this.content = new StringBuilder(content);
    }

    public String getContent() {
        return content.toString();
    }

    public void setContent(String content) {
        this.content.setLength(0);
        append(content);
    }

    public ParagraphElement append(String content) {
        this.content.append(content);
        return this;
    }

    public ParagraphElement appendLine(String line) {
        return append(line).append(System.lineSeparator());
    }

    @Override
    public String toString() {
        return getContent();
    }
}
