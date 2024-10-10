package ca.purification.inventory.view;

import ca.purification.inventory.viewmodel.ViewModel;

import java.util.Optional;

/**
 * The {@code View} class serves as an abstract base for all views in the 
 * application, representing the UI components that are linked to a 
 * corresponding {@code ViewModel}. It provides a mechanism to display 
 * the view and retrieve the associated view model, facilitating the 
 * interaction between the user interface and the underlying data model.
 *
 * @see ViewModel
 */
public abstract class View {
    protected ViewModel viewModel;

    public View() {
        this(null);
    }

    public View(ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public abstract Optional<View> show();

    public Optional<ViewModel> getViewModel() {
        return Optional.ofNullable(viewModel);
    }
}
