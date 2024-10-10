package ca.purification.inventory.view.helper;

import ca.purification.inventory.model.InvalidSerialNumberException;
import ca.purification.inventory.model.PurificationUnitManager;
import ca.purification.inventory.model.SerialNumber;
import ca.purification.inventory.ui.element.ParagraphElement;
import ca.purification.inventory.ui.element.PromptElement;
import ca.purification.inventory.ui.text.TextElementPresenter;
import ca.purification.inventory.viewmodel.UnitSortOrder;

import java.util.Optional;

/**
 * The {@code SerialNumberPrompt} class handles the user interaction 
 * for entering a serial number associated with a 
 * {@code PurificationUnit}. It provides a prompt for the user to 
 * input a serial number, offering options to list available units 
 * or cancel the action. If the input is invalid or no units exist, 
 * appropriate feedback is provided to the user.
 *
 * <p>This class utilizes a {@code PurificationUnitManager} to check 
 * for existing units and validates the entered serial number. It 
 * displays prompts through a {@code TextElementPresenter} and 
 * allows customization of input options such as the commands for 
 * listing units and canceling input.</p>
 *
 * @see PurificationUnitManager
 * @see SerialNumber
 * @see InvalidSerialNumberException
 * @see PromptElement
 * @see TextElementPresenter
 * @see UnitSortOrder
 */
public class SerialNumberPrompt {
    private final ParagraphElement noUnitsText = new ParagraphElement("No units defined.\n" +
            "Please create a unit and then re-try this option.");
    private final PromptElement<String> serialNumberPrompt;

    private final PurificationUnitManager unitManager;
    private String listUnitsInputText;
    private String cancelInputText;
    private UnitSortOrder unitSortOrder;

    public SerialNumberPrompt(PurificationUnitManager unitManager) {
        this(unitManager, "0", "-1");
    }

    public SerialNumberPrompt(PurificationUnitManager unitManager,
                              String listUnitsInputText, String cancelInputText) {
        this.unitManager = unitManager;
        this.listUnitsInputText = listUnitsInputText;
        this.cancelInputText = cancelInputText;

        String serialNumberPromptText = String.format("Enter the serial number (%s for list, %s for cancel): ",
                listUnitsInputText,
                cancelInputText);
        serialNumberPrompt = new PromptElement<>(serialNumberPromptText, this::getSerialNumberOrOption);
    }

    public Optional<SerialNumber> show(TextElementPresenter elementPresenter) {
        if (!unitManager.hasUnits()) {
            elementPresenter.push(noUnitsText);
            return Optional.empty();
        }

        return showPrompt(elementPresenter);
    }

    private Optional<SerialNumber> showPrompt(TextElementPresenter elementPresenter) {
        // Lazy init
        PurificationUnitList unitList = null;

        while (true) {
            elementPresenter.push(serialNumberPrompt);

            Optional<String> serialNumber = serialNumberPrompt.getResult();
            assert serialNumber.isPresent();

            String actualSerialNumber = serialNumber.orElseThrow();

            if (actualSerialNumber.equals(cancelInputText)) {
                return Optional.empty();
            } else if (actualSerialNumber.equals(listUnitsInputText)) {
                if (unitList == null) {
                    unitList = new PurificationUnitList(unitManager.getUnits());
                    unitList.sortUnits(unitSortOrder.getSorter());
                }

                unitList.show(elementPresenter);
                continue;
            }

            return Optional.of(new SerialNumber(actualSerialNumber));
        }
    }

    private Optional<String> getSerialNumberOrOption(String input) {
        if (input.equals(cancelInputText) || input.equals(listUnitsInputText)) {
            return Optional.of(input);
        }

        String invalidSerialNumberText = String.format("No unit found matching serial '%s'%n%s",
                input,
                serialNumberPrompt.getPrompt());

        try {
            if (input.isBlank() || !unitManager.hasUnit(new SerialNumber(input))) {
                serialNumberPrompt.setRetryPrompt(invalidSerialNumberText);
                return Optional.empty();
            }
        } catch (InvalidSerialNumberException e) {
            serialNumberPrompt.setRetryPrompt(invalidSerialNumberText);
            return Optional.empty();
        }

        return Optional.of(input);
    }

    public UnitSortOrder getUnitSortOrder() {
        return unitSortOrder;
    }

    public void setUnitSortOrder(UnitSortOrder unitSortOrder) {
        this.unitSortOrder = unitSortOrder;
    }

    public String getListUnitsInputText() {
        return listUnitsInputText;
    }

    public void setListUnitsInputText(String listUnitsInputText) {
        this.listUnitsInputText = listUnitsInputText;
    }

    public String getCancelInputText() {
        return cancelInputText;
    }

    public void setCancelInputText(String cancelInputText) {
        this.cancelInputText = cancelInputText;
    }
}
