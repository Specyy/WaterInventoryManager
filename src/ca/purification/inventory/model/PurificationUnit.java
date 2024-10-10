package ca.purification.inventory.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The {@code PurificationUnit} class represents a water purification unit within the inventory system.
 *
 * <p>A purification unit is identified by a {@link ModelId} and a {@link SerialNumber}. The unit also maintains a
 * list of {@link PurificationUnitTest} results that track its testing history. Additionally, the unit may have a
 * {@link LocalDate} indicating when it was shipped out, which affects its {@link ShippingStatus}.</p>
 *
 * <p>This class supports operations to add and retrieve tests, check the shipping status of the unit, and track the
 * shipping date.</p>
 *
 * @see ModelId
 * @see SerialNumber
 * @see PurificationUnitTest
 * @see ShippingStatus
 */
public class PurificationUnit {
    private final ModelId model;
    private final SerialNumber serialNumber;
    private List<PurificationUnitTest> tests;
    private LocalDate dateShipped;

    public PurificationUnit(ModelId model, SerialNumber serialNumber) {
        this(model, serialNumber, new ArrayList<>(), null);
    }

    public PurificationUnit(ModelId model,
                            SerialNumber serialNumber,
                            List<PurificationUnitTest> tests,
                            LocalDate dateShipped) {
        this.model = model;
        this.serialNumber = serialNumber;
        this.tests = tests;
        this.dateShipped = dateShipped;
    }

    public ModelId getModel() {
        return model;
    }

    public SerialNumber getSerialNumber() {
        return serialNumber;
    }

    public void addTest(PurificationUnitTest test) {
        this.tests.add(test);
    }

    public void setTests(List<PurificationUnitTest> tests) {
        this.tests = tests;
    }

    public List<PurificationUnitTest> getTests() {
        return tests;
    }

    public Optional<PurificationUnitTest> getLatestTest() {
        if (tests.isEmpty()) {
            return Optional.empty();
        }

        List<PurificationUnitTest> sortedTests = new ArrayList<>(tests);
        sortedTests.sort(PurificationUnitTest::compareTo);
        return Optional.of(sortedTests.getLast());
    }

    public ShippingStatus getShippingStatus() {
        return dateShipped == null ? ShippingStatus.IN_STOCK : ShippingStatus.SHIPPED_OUT;
    }

    public void setDateShipped(LocalDate dateShipped) {
        this.dateShipped = dateShipped;
    }

    public LocalDate getDateShipped() {
        return dateShipped;
    }
}
