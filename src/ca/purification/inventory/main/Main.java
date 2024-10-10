package ca.purification.inventory.main;

/*
 * Student name: Alvyn Kang
 */

/**
 * The {@code Main} class is the entry point for the Water Purification Inventory Management application.
 * It initializes the {@link AppContext}, which manages the overall flow and resources of the application,
 * and starts the application by invoking its {@code run} method.
 *
 * <p>This class provides the main method that launches the application.</p>
 *
 * @see AppContext
 */
public class Main {
    public static void main(String[] args) {
        AppContext appContext = new AppContext("Water Purification Inventory Management");
        appContext.run();
    }
}