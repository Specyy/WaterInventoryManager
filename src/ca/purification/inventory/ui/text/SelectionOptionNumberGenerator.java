package ca.purification.inventory.ui.text;

import java.util.OptionalInt;
import java.util.function.Function;
import java.util.function.IntFunction;

/**
 * The {@code SelectionOptionNumberGenerator} interface provides a contract for 
 * generating selection option numbers and converting selection markers to 
 * integer indices. It serves as a mechanism for defining how selection options 
 * are numbered and how user input markers are interpreted.
 *
 * <p>Implementations of this interface are used in selection components 
 * to facilitate user interaction, ensuring that selection markers can be 
 * reliably converted and generated based on application requirements.</p>
 * 
 * @see SelectionRenderer
 */
public interface SelectionOptionNumberGenerator extends
        IntFunction<String>,
        Function<String, OptionalInt> {
    OptionalInt convert(String marker);

    String generate(int index);

    @Override
    default OptionalInt apply(String value) {
        return convert(value);
    }

    @Override
    default String apply(int value) {
        return generate(value);
    }
}
