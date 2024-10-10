package ca.purification.inventory.ui.text;

import ca.purification.inventory.ui.ElementPresenter;
import ca.purification.inventory.ui.InvalidElementException;
import ca.purification.inventory.ui.element.ParagraphElement;
import ca.purification.inventory.ui.element.UIElement;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * The {@code TextElementPresenter} class is responsible for presenting 
 * {@code UIElement} objects in a text-based format to the user. It serves 
 * as a bridge between the UI elements and their rendered representation 
 * in a console or terminal environment. The class utilizes a {@code
 * TextElementRenderer} to convert UI elements into a textual format for 
 * display. 

 * <p>This presenter supports various input and output sources, allowing 
 * flexibility in how user interaction is managed. By default, it uses 
 * the standard input and output streams, but it can be configured to 
 * accept any {@code InputStream} and output to any {@code OutputStream}. 
 * This makes the presenter suitable for use in different contexts, such as 
 * interactive command-line applications or automated testing scenarios.</p>
 *
 * <p>The class provides methods to push {@code UIElement} instances for 
 * rendering and also allows for simple string input to be presented as 
 * a {@code ParagraphElement}. Each call to push triggers the rendering 
 * process, ensuring that the most recent content is displayed to the user 
 * immediately.</p>
 *
 * <p>This implementation is designed to be straightforward and extensible, 
 * enabling the addition of new rendering strategies or input handling 
 * mechanisms as needed.</p>
 * 
 * @see ElementPresenter
 * @see InvalidElementException
 */
public class TextElementPresenter implements ElementPresenter {
    protected final Scanner input;
    protected final PrintStream output;
    private final TextElementRenderer renderer = new DefaultElementRenderer();

    public TextElementPresenter() {
        this(System.in, System.out);
    }

    public TextElementPresenter(InputStream input, OutputStream output) {
        this.input = new Scanner(input);
        this.output = new PrintStream(output, false);
    }

    public void push(UIElement element) {
        renderer.render(this, element);
        output.flush();
    }

    public void push(String text) {
        ParagraphElement inlineText = new ParagraphElement(text);
        inlineText.getStyle().setInline(true);
        
        push(inlineText);
    }

    Scanner getInput() {
        return input;
    }

    PrintStream getOutput() {
        return output;
    }
}
