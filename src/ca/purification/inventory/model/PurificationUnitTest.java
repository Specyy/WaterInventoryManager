package ca.purification.inventory.model;

import java.time.LocalDate;

/**
 * The {@code PurificationUnitTest} class represents a test conducted on a purification unit,
 * containing information about the test date, the outcome, and optional comments regarding the test results.
 *
 * <p>This class supports the following features:
 * <ul>
 *     <li>Storing the date of the test.</li>
 *     <li>Indicating whether the test has passed or failed.</li>
 *     <li>Providing a space for comments related to the test results.</li>
 *     <li>Comparing tests based on their dates to facilitate sorting.</li>
 * </ul>
 * </p>
 *
 * @see PurificationUnit
 */
public class PurificationUnitTest implements Comparable<PurificationUnitTest> {
    private final LocalDate date;
    private final boolean isTestPassed;
    private String testResultComment;

    public PurificationUnitTest(LocalDate date, boolean hasPassed, String comment) {
        this.date = date;
        this.isTestPassed = hasPassed;
        this.testResultComment = comment;
    }

    public LocalDate getDate() {
        return date;
    }

    public boolean hasPassed() {
        return isTestPassed;
    }

    public String getComment() {
        return testResultComment;
    }

    public void setComment(String comment) {
        this.testResultComment = comment;
    }

    @Override
    public int compareTo(PurificationUnitTest other) {
        return date.compareTo(other == null ? LocalDate.MAX : other.date);
    }
}
