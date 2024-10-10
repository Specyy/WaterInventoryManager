package ca.purification.inventory.view;

import ca.purification.inventory.ui.element.ParagraphElement;
import ca.purification.inventory.ui.text.TextElementPresenter;
import ca.purification.inventory.util.LazyDependency;

import java.util.Optional;

/**
 * The {@code IntroView} class is responsible for displaying the introductory 
 * information of the application, including the application name and the 
 * author's name. This view serves as the initial screen that users see 
 * before they proceed to the main menu.
 *
 * <p>The class utilizes a {@code TextElementPresenter} to manage the display 
 * of text elements, specifically a {@code ParagraphElement} for the greeting 
 * message. After showing the introductory information, it transitions 
 * seamlessly to the {@code MenuView}.</p>
 *
 * @see MenuView
 * @see TextElementPresenter
 * @see ParagraphElement
 */
public class IntroView extends View {
    private final LazyDependency<MenuView> menuView;
    
    private final TextElementPresenter elementPresenter = new TextElementPresenter();
    private final ParagraphElement greetingText;

    public IntroView(final String appName, LazyDependency<MenuView> menuView) {
        this.menuView = menuView;

        this.greetingText = new ParagraphElement(appName + "\nby Alvyn Kang.");
        this.greetingText.getStyle().getBorder().setVertical(1);
    }

    @Override
    public Optional<View> show() {
        elementPresenter.push(greetingText);
        return Optional.of(menuView.resolve());
    }
}