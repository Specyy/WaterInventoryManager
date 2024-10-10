package ca.purification.inventory.main;

import ca.purification.inventory.model.PurificationUnitManager;
import ca.purification.inventory.view.*;

import java.util.Optional;

/**
 * The {@code AppContext} class provides the main execution context for the purification inventory application. 
 * It manages the current application view, facilitates transitions between different views, and interacts 
 * with the {@link PurificationUnitManager} to handle purification unit data.
 *
 * <p>This class implements {@link Runnable} to allow for the execution of the application in a thread or other 
 * runnable environment. It holds a collection of views and ensures smooth navigation between them.</p>
 *
 * <p>Two constructors are available, either initializing the application with a default view or a specific 
 * view instance.</p>
 * 
 * @see AppViewCollection
 * @see View
 * @see PurificationUnitManager
 */
public final class AppContext implements Runnable {
    private final String name;
    private final AppViewCollection views;
    private final View initialView;
    private final PurificationUnitManager unitManager = new PurificationUnitManager();

    public AppContext(final String name) {
        this.name = name;
        this.views = new AppViewCollection(this);

        this.initialView = this.views.getView(IntroView.class);
    }

    public <T extends View> AppContext(T initialView, final String name) {
        this.name = name;
        this.views = new AppViewCollection(this);
        this.views.addIfAbsent(initialView.getClass(), () -> initialView);

        this.initialView = initialView;
    }

    @Override
    public void run() {
        Optional<View> nextView = Optional.of(initialView);

        do {
            nextView = nextView.orElseThrow().show();
        } while (nextView.isPresent());
    }

    public String getName() {
        return name;
    }

    public PurificationUnitManager getUnitManager() {
        return unitManager;
    }
}
