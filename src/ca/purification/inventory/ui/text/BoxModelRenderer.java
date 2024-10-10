package ca.purification.inventory.ui.text;

import ca.purification.inventory.ui.element.BoxMetrics;
import ca.purification.inventory.ui.element.ElementAlignment;
import ca.purification.inventory.ui.element.ElementBorder;
import ca.purification.inventory.ui.element.ElementStyle;
import ca.purification.inventory.ui.element.text.RepeatedTextBorder;
import ca.purification.inventory.util.StringUtils;

/**
 * Renders UI elements with a box model, which includes margins, 
 * padding, borders, and content. This class takes an {@link ElementStyle}
 * and a content string, generating a formatted representation that 
 * respects the specified styling attributes.
 *
 * <p>
 * The rendering process includes:
 * <ul>
 * <li>Checking for empty elements and optimizing rendering accordingly.</li>
 * <li>Building a frame with vertical margins, borders, and padded content.</li>
 * <li>Aligning content based on the specified alignment settings.</li>
 * </ul>
 * </p>
 *
 * @see ElementStyle
 * @see BoxMetrics
 * @see ElementBorder
 * @see RepeatedTextBorder
 * @see TextElementRenderer
 */
public class BoxModelRenderer {
    public String render(ElementStyle elementStyle, String elementContent) {
        // Optimization for empty elements
        if (isEmptyElement(elementStyle, elementContent)) {
            return "";
        }

        StringBuilder frameBuilder = performFramedRender(elementStyle, elementContent);
        return performPostProcessing(frameBuilder, elementStyle);
    }

    private StringBuilder performFramedRender(ElementStyle elementStyle, String elementContent) {
        final StringBuilder frameBuilder = new StringBuilder();

        RenderingContext context = new RenderingContext(elementStyle, elementContent);

        renderVerticalFrame(frameBuilder, context, true);
        renderFramedContent(frameBuilder, context);
        renderVerticalFrame(frameBuilder, context, false);

        return frameBuilder;
    }

    private boolean isEmptyElement(ElementStyle elementStyle, String elementContent) {
        return elementContent.isEmpty()
                && !isFramedElement(elementStyle)
                && elementStyle.getMinimumWidth() <= 0;
    }

    boolean isFramedElement(ElementStyle elementStyle) {
        return isFramedElement(elementStyle.getMargin(),
                elementStyle.getPadding(),
                elementStyle.getBorder().getMetrics());
    }

    private boolean isFramedElement(BoxMetrics margin, BoxMetrics padding, BoxMetrics border) {
        return !margin.isZero() || !padding.isZero() || !border.isZero();
    }

    private String performPostProcessing(StringBuilder frameBuilder, ElementStyle elementStyle) {
        if (!elementStyle.isInline()) {
            frameBuilder.append(System.lineSeparator());
        }

        return frameBuilder.toString();
    }

    private void renderFramedContent(StringBuilder frameBuilder, RenderingContext context) {

        final BoxMetrics padding = context.getPadding();
        final BoxMetrics margin = context.getMargin();
        final RepeatedTextBorder border = context.getBorderOverride();
        final int lineCount = context.getContentLineCount();

        boolean hasFrame = isFramedElement(padding, margin, border.getMetrics());

        for (int contentRow = 0; contentRow < lineCount; contentRow++) {
            frameBuilder.repeat(' ', margin.getLeft());
            frameBuilder.repeat(context.getBorderOverride().getBorderChar(), border.getLeft());
            frameBuilder.repeat(' ', padding.getLeft());

            renderAlignedLine(frameBuilder, context, context.getContentLine(contentRow));

            frameBuilder.repeat(' ', padding.getRight());
            frameBuilder.repeat(border.getBorderChar(), border.getRight());
            frameBuilder.repeat(' ', margin.getRight());

            if (hasFrame || contentRow < lineCount - 1) {
                frameBuilder.append(System.lineSeparator());
            }
        }
    }

    private void renderAlignedLine(StringBuilder lineBuilder, RenderingContext context, String line) {
        renderAlignedLine(lineBuilder,
                context.getOriginalStyle().getAlignment(),
                line,
                context.getContentWidth());
    }

    void renderAlignedLine(StringBuilder lineBuilder, ElementAlignment alignment, String line, int width) {
        switch (alignment) {
            case CENTER:
                lineBuilder.repeat(' ', (width - line.length() + 1) / 2);
                lineBuilder.append(line);
                lineBuilder.repeat(' ', (width - line.length()) / 2);
                break;

            case RIGHT:
                lineBuilder.repeat(' ', width - line.length());
                lineBuilder.append(line);
                break;

            case LEFT:
            default:
                lineBuilder.append(line);
                lineBuilder.repeat(' ', width - line.length());
                break;
        }
    }

    private void renderVerticalFrame(StringBuilder frameBuilder, RenderingContext context, boolean isTop) {
        if (isTop) {
            renderVerticalMargin(frameBuilder, context, true);
            renderVerticalBorder(frameBuilder, context, true);
            renderVerticalPadding(frameBuilder, context, true);
        } else {
            renderVerticalPadding(frameBuilder, context, false);
            renderVerticalBorder(frameBuilder, context, false);
            renderVerticalMargin(frameBuilder, context, false);
        }
    }

    private void renderVerticalMargin(StringBuilder frameBuilder, RenderingContext context, boolean isTop) {
        final BoxMetrics margin = context.getMargin();
        final BoxMetrics border = context.getBorderOverride().getMetrics();

        int marginHeight = isTop ? margin.getTop() : margin.getBottom();
        int renderHeight = Math.max(0, isTop ? marginHeight : marginHeight - 1);

        frameBuilder.repeat(System.lineSeparator(), renderHeight);

        if (!isTop && marginHeight > 0) {
            int lineWidth = margin.getLeft() + border.getLeft() +
                    context.getInnerWidth() +
                    border.getRight() + margin.getRight();

            frameBuilder.repeat(' ', lineWidth);
        }
    }

    private void renderVerticalPadding(StringBuilder frameBuilder, RenderingContext context, boolean isTop) {
        final BoxMetrics padding = context.getPadding();
        final BoxMetrics margin = context.getMargin();
        final RepeatedTextBorder border = context.getBorderOverride();

        int paddingHeight = isTop ? padding.getTop() : padding.getBottom();

        for (int paddingRow = 0; paddingRow < paddingHeight; paddingRow++) {
            frameBuilder.repeat(' ', margin.getLeft());
            frameBuilder.repeat(border.getBorderChar(), border.getLeft());

            frameBuilder.repeat(' ', context.getInnerWidth());

            frameBuilder.repeat(border.getBorderChar(), border.getRight());
            frameBuilder.repeat(' ', margin.getRight());

            boolean hasNextLine = isTop
                    || margin.getBottom() != 0 || border.getBottom() != 0
                    || paddingRow < paddingHeight - 1;
            
            if (hasNextLine) {
                frameBuilder.append(System.lineSeparator());
            }
        }
    }

    private void renderVerticalBorder(StringBuilder frameBuilder, RenderingContext context, boolean isTop) {
        final BoxMetrics margin = context.getMargin();
        final RepeatedTextBorder border = context.getBorderOverride();

        int borderHeight = isTop ? border.getTop() : border.getBottom();

        for (int borderRow = 0; borderRow < borderHeight; borderRow++) {
            frameBuilder.repeat(' ', margin.getLeft());
            renderRepeatedBorder(frameBuilder, context);
            frameBuilder.repeat(' ', margin.getRight());

            boolean hasNextLine = isTop || margin.getBottom() != 0 || borderRow != borderHeight - 1;
            if (hasNextLine) {
                frameBuilder.append(System.lineSeparator());
            }
        }
    }

    private void renderRepeatedBorder(StringBuilder frameBuilder, RenderingContext context) {
        final RepeatedTextBorder border = context.getBorderOverride();
        int width = 0;

        if (border.getLeft() > 0 && border.getRight() > 0) {
            width = border.getLeft() + context.getInnerWidth() + border.getRight();
        } else if (border.getLeft() > 0) {
            width = border.getLeft() + context.getInnerWidth();
        } else if (border.getRight() > 0) {
            frameBuilder.repeat(' ', border.getLeft());
            width = border.getRight();
        } else {
            width = context.getInnerWidth();
        }

        frameBuilder.repeat(border.getBorderChar(), width);
    }

    /**
     * A context for rendering a UI element's content, encapsulating the 
     * element's style, content lines, and dimensions. This class provides 
     * necessary information for rendering the element according to its 
     * styling attributes, such as padding, margins, and borders.
     *
     * <p>
     * The {@code RenderingContext} is initialized with an {@link ElementStyle}
     * and the content string of the element. It calculates various dimensions 
     * and provides methods to retrieve the relevant data for rendering.
     * </p>
     */
    private static class RenderingContext {
        private final String[] contentLines;
        private final ElementStyle originalStyle;
        private final RepeatedTextBorder borderOverride;
        private final int contentWidth;
        private final int innerWidth;

        public RenderingContext(ElementStyle elementStyle, String elementContent) {
            this.contentLines = StringUtils.splitLines(elementContent);

            int rawContentWidth = StringUtils.findLongest(contentLines).length();
            this.contentWidth = Math.max(rawContentWidth, elementStyle.getMinimumWidth());

            this.originalStyle = elementStyle;
            this.borderOverride = resolveBorder(elementStyle.getBorder());

            this.innerWidth = getPadding().getLeft() + contentWidth + getPadding().getRight();
        }

        private static RepeatedTextBorder resolveBorder(ElementBorder actualBorder) {
            if (actualBorder instanceof RepeatedTextBorder repeatedBorder) {
                return repeatedBorder;
            }

            return new RepeatedTextBorder(actualBorder.getMetrics());
        }

        public String getContentLine(int index) {
            return contentLines[index];
        }

        public int getContentLineCount() {
            return contentLines.length;
        }

        public ElementStyle getOriginalStyle() {
            return originalStyle;
        }

        public BoxMetrics getPadding() {
            return originalStyle.getPadding();
        }

        public BoxMetrics getMargin() {
            return originalStyle.getMargin();
        }

        public RepeatedTextBorder getBorderOverride() {
            return borderOverride;
        }

        public int getContentWidth() {
            return contentWidth;
        }

        public int getInnerWidth() {
            return innerWidth;
        }
    }
}