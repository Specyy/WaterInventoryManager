package ca.purification.inventory.util;

import ca.purification.inventory.ui.element.UIElement;

import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

/**
 * A collection of string utility functions related to {@link String Strings}
 * mainly purposed for the UI.
 *
 * @see UIElement
 */
public final class StringUtils {
    public static final String LINE_TERMINATOR_REGEX
            = "(\r\n)|[\r\n\f\u000B\u0085\u2028\u2029]";
    public static final Pattern LINE_TERMINATOR_PATTERN = Pattern.compile(LINE_TERMINATOR_REGEX);

    private StringUtils() {
    }

    public static String formatDate(LocalDate date) {
        return date == null ? "-" : date.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public static String[] splitLines(String string) {
        return string == null ? null : LINE_TERMINATOR_PATTERN.split(string, 0);
    }

    public static boolean containsRegex(String string, String regex) {
        return containsPattern(string, Pattern.compile(regex));
    }

    public static boolean containsPattern(String string, Pattern pattern) {
        return pattern.matcher(string).find();
    }

    public static String replacePattern(String string, Pattern pattern, String replacement) {
        return pattern.matcher(string).replaceAll(replacement);
    }

    public static boolean isIntegral(String string) {
        if (string == null || string.isBlank()) {
            return false;
        }

        string = string.strip();

        final char minusSign = DecimalFormatSymbols.getInstance().getMinusSign();
        return isUnsignedIntegral(string.charAt(0) == minusSign ? string.substring(1) : string);
    }

    public static boolean isUnsignedIntegral(String string) {
        if (string == null || string.isBlank()) {
            return false;
        }

        string = string.strip();

        for (int i = 0; i < string.length(); i++) {
            if (!Character.isDigit(string.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    public static boolean isFloat(String string) {
        if (string == null) {
            return false;
        }

        string = string.strip();

        final char decimalSeparator = DecimalFormatSymbols.getInstance().getDecimalSeparator();
        final char minusSign = DecimalFormatSymbols.getInstance().getMinusSign();
        boolean foundDecimal = false;

        for (int i = 0; i < string.length(); i++) {
            char current = string.charAt(i);

            if (current == decimalSeparator) {
                if (foundDecimal) {
                    return false;
                }

                foundDecimal = true;
            } else if (current == minusSign && i == 0) {
                continue;
            } else if (!Character.isDigit(current)) {
                return false;
            }
        }

        return true;
    }

    public static String findLongest(String[] strings) {
        if (strings == null || strings.length == 0) {
            return null;
        }

        String longest = strings[0];

        for (int i = 1; i < strings.length; i++) {
            String current = strings[i];

            if (current.length() > longest.length()) {
                longest = current;
            }
        }

        return longest;
    }
}