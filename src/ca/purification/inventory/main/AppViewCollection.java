package ca.purification.inventory.main;

import ca.purification.inventory.ui.text.TextElementPresenter;
import ca.purification.inventory.util.LazyClassRegistry;
import ca.purification.inventory.util.LazyDependency;
import ca.purification.inventory.view.*;
import ca.purification.inventory.viewmodel.*;

import java.util.function.Supplier;

/**
 * The {@code AppViewCollection} class is responsible for managing and lazily loading the views of the application.
 * It serves as a central repository for all application views, ensuring that they are instantiated only when needed
 * and that they can be easily retrieved and used by the {@link AppContext}.
 *
 * <p>Each view is registered with a class-to-instance mapping through the {@link LazyClassRegistry}. This allows
 * views to be created only when requested, optimizing memory and performance.</p>
 *
 * <p>The class also manages the interactions between views and their corresponding view models, linking
 * dependencies such as the {@link ca.purification.inventory.model.PurificationUnitManager} to appropriate
 * views and view models.</p>
 *
 * @see AppContext
 * @see View
 * @see LazyClassRegistry
 */
public class AppViewCollection {
    private final LazyClassRegistry<View> views = new LazyClassRegistry<>();
    private final TextElementPresenter elementPresenter = new TextElementPresenter();
    private final AppContext appContext;

    public AppViewCollection(AppContext appContext) {
        this.appContext = appContext;
        registerViews();
    }

    public <T extends View> LazyDependency<T> getLazyView(Class<T> viewClass) {
        return views.castDependency(viewClass);
    }

    public <T extends View> T getView(Class<T> viewClass) {
        return getLazyView(viewClass).resolve();
    }

    public <T extends View> void addIfAbsent(Class<? extends T> viewClass, Supplier<? extends T> view) {
        views.registerIfAbsent(viewClass, new LazyDependency<>(view::get));
    }

    private void registerViews() {
        registerIntroView();
        registerMenuView();
        registerReadFileView();
        registerDisplayUnitView();
        registerCreateUnitView();
        registerTestUnitView();
        registerShipUnitView();
        registerPrintReportView();
        registerReorderUnitsView();
    }

    private void registerIntroView() {
        views.registerIfAbsent(IntroView.class, () ->
                new IntroView(appContext.getName(), elementPresenter, views.castDependency(MenuView.class))
        );
    }

    private void registerMenuView() {
        views.registerIfAbsent(MenuView.class, () ->
                new MenuView(
                        elementPresenter,
                        views.castDependency(ReadFileView.class),
                        views.castDependency(CreateUnitView.class),
                        views.castDependency(DisplayUnitView.class),
                        views.castDependency(TestUnitView.class),
                        views.castDependency(ShipUnitView.class),
                        views.castDependency(PrintReportView.class),
                        views.castDependency(ReorderReportsView.class)
                )
        );
    }

    private void registerReadFileView() {
        views.registerIfAbsent(ReadFileView.class, () ->
                new ReadFileView(
                        new ReadFileViewModel(appContext.getUnitManager()),
                        elementPresenter,
                        views.castDependency(MenuView.class)
                )
        );
    }

    private void registerDisplayUnitView() {
        views.registerIfAbsent(DisplayUnitView.class, () ->
                new DisplayUnitView(
                        new DisplayUnitViewModel(appContext.getUnitManager()),
                        elementPresenter,
                        views.castDependency(MenuView.class)
                )
        );
    }

    private void registerCreateUnitView() {
        views.registerIfAbsent(CreateUnitView.class, () ->
                new CreateUnitView(
                        new CreateUnitViewModel(appContext.getUnitManager()),
                        elementPresenter,
                        views.castDependency(MenuView.class)
                )
        );
    }

    private void registerTestUnitView() {
        views.registerIfAbsent(TestUnitView.class, () ->
                new TestUnitView(
                        new TestUnitViewModel(appContext.getUnitManager()),
                        elementPresenter,
                        views.castDependency(MenuView.class)
                )
        );
    }

    private void registerShipUnitView() {
        views.registerIfAbsent(ShipUnitView.class, () ->
                new ShipUnitView(
                        new ShipUnitViewModel(appContext.getUnitManager()),
                        elementPresenter,
                        views.castDependency(MenuView.class)
                )
        );
    }

    private void registerPrintReportView() {
        views.registerIfAbsent(PrintReportView.class, () -> {
            ReorderReportsView reportOrderView = views.castDependency(ReorderReportsView.class).resolve();
            ReorderReportsViewModel reportOrderViewModel = (ReorderReportsViewModel)
                    reportOrderView.getViewModel().orElseThrow();

            return new PrintReportView(
                    new PrintReportViewModel(appContext.getUnitManager(), reportOrderViewModel::getSortOrder),
                    elementPresenter,
                    views.castDependency(MenuView.class)
            );
        });
    }

    private void registerReorderUnitsView() {
        views.registerIfAbsent(ReorderReportsView.class, () ->
                new ReorderReportsView(
                        new ReorderReportsViewModel(appContext.getUnitManager()),
                        elementPresenter,
                        views.castDependency(MenuView.class)
                )
        );
    }
}