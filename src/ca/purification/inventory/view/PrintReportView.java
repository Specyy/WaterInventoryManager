package ca.purification.inventory.view;

import ca.purification.inventory.model.PurificationUnit;
import ca.purification.inventory.ui.element.ParagraphElement;
import ca.purification.inventory.ui.element.SelectionElement;
import ca.purification.inventory.ui.text.TextElementPresenter;
import ca.purification.inventory.util.LazyDependency;
import ca.purification.inventory.view.helper.DefectiveUnitList;
import ca.purification.inventory.view.helper.PurificationUnitList;
import ca.purification.inventory.view.helper.ReadyToShipUnitList;
import ca.purification.inventory.viewmodel.PrintReportViewModel;
import ca.purification.inventory.viewmodel.ReportOption;

import java.util.Collection;
import java.util.Optional;

/**
 * The {@code PrintReportView} class provides a user interface for generating
 * and displaying product reports based on user-selected criteria. Users can
 * choose from various report options, including all products, defective
 * products that failed their last test, and products that are ready to ship
 * but not yet dispatched.
 *
 * <p>This class interacts with the {@code PrintReportViewModel} to retrieve
 * the necessary data for generating reports. Once the user selects a report
 * option, the corresponding list of purification units is created and displayed
 * using the appropriate helper class, such as {@code PurificationUnitList},
 * {@code DefectiveUnitList}, or {@code ReadyToShipUnitList}.</p>
 *
 * @see PrintReportViewModel
 * @see ReportOption
 * @see PurificationUnitList
 * @see DefectiveUnitList
 * @see ReadyToShipUnitList
 * @see LazyDependency
 * @see MenuView
 * @see SelectionElement
 * @see ParagraphElement
 */
public class PrintReportView extends View {
    private final PrintReportViewModel viewModel;
    private final LazyDependency<MenuView> mainMenu;

    private final TextElementPresenter elementPresenter = new TextElementPresenter();

    private final SelectionElement<ReportOption> reportOptionSelection;

    public PrintReportView(PrintReportViewModel viewModel, LazyDependency<MenuView> menuView) {
        super(viewModel);
        this.viewModel = viewModel;
        this.mainMenu = menuView;

        reportOptionSelection = new SelectionElement<>();

        ParagraphElement reportOptionsHeader = new ParagraphElement("Report Options");
        reportOptionsHeader.getStyle().getPadding().setHorizontal(1);
        reportOptionsHeader.getStyle().setBorder(1);
        reportOptionSelection.setLabel(reportOptionsHeader);

        reportOptionSelection.addOption(ReportOption.ALL.getLabel() + 
                                ":           All products.", ReportOption.ALL)
                .addOption(ReportOption.DEFECTIVE.getLabel() + 
                                ":     Products that failed their last test.", ReportOption.DEFECTIVE)
                .addOption(ReportOption.READY_TO_SHIP.getLabel() + 
                                ": Products passed tests, not shipped.", ReportOption.READY_TO_SHIP)
                .addOption("Cancel report request.", null);
    }

    @Override
    public Optional<View> show() {
        elementPresenter.push(reportOptionSelection);
        assert reportOptionSelection.hasSelectedOption();

        ReportOption reportOption = reportOptionSelection.getSelectedOption().getValue();
        if (reportOption != null) {
            viewModel.setReportOption(reportOption);

            Collection<? extends PurificationUnit> reportedUnits = viewModel.getReportedUnits();
            PurificationUnitList list = switch (viewModel.getReportOption()) {
                case ALL -> new PurificationUnitList(reportedUnits);
                case DEFECTIVE -> new DefectiveUnitList(reportedUnits);
                case READY_TO_SHIP -> new ReadyToShipUnitList(reportedUnits);
            };

            list.show(elementPresenter);
        }

        return Optional.of(mainMenu.resolve());
    }
}
