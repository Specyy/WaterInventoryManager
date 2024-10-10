package ca.purification.inventory.ui.element;

import java.util.*;
import java.util.function.UnaryOperator;

/**
 * Represents a UI element that allows users to select an option from a list of selectable items.
 * This class extends {@link UIElement} and implements {@link List} to manage a collection of 
 * {@link SelectionOption} objects. It provides functionality to set a label, manage selectable 
 * options, and track the currently selected option.
 *
 * <p>
 * The selection options are stored in a list, allowing for easy manipulation of the options 
 * available to the user. This class is designed to be flexible and reusable in various UI contexts.
 * </p>
 *
 * @param <T> the type of the values associated with each selection option
 *
 * @see UIElement
 * @see SelectionOption
 */
public class SelectionElement<T> extends UIElement implements List<SelectionOption<T>> {
    private UIElement label;
    private final List<SelectionOption<T>> options;
    private SelectionOption<T> selectedOption;

    public SelectionElement() {
        this(Collections.emptyList());
    }

    @SafeVarargs
    public SelectionElement(SelectionOption<T>... options) {
        this(new ParagraphElement(""), options);
    }

    public SelectionElement(Collection<? extends SelectionOption<T>> options) {
        this(new ParagraphElement(""), options);
    }

    @SafeVarargs
    public SelectionElement(UIElement label, SelectionOption<T>... options) {
        this.label = label;
        this.options = new ArrayList<>(options.length);
        Collections.addAll(this, options);
    }

    public SelectionElement(UIElement label, Collection<? extends SelectionOption<T>> options) {
        this.label = label;
        this.options = new ArrayList<>(options);
    }

    public SelectionOption<T> getOption(int index) {
        return get(index);
    }

    public T getOptionValue(int index) {
        return getOption(index).getValue();
    }

    public SelectionElement<T> addOption(T optionValue) {
        return addOption(null, optionValue);
    }

    public SelectionElement<T> addOption(String label, T value) {
        add(new SelectionOption<>(label, value));
        return this;
    }

    public UIElement getLabel() {
        return label;
    }

    public void setLabel(UIElement label) {
        this.label = label;
    }

    public boolean hasSelectedOption() {
        return selectedOption != null;
    }

    public SelectionOption<T> getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(int index) {
        this.selectedOption = getOption(index);
    }

    @Override
    public int size() {
        return options.size();
    }

    @Override
    public boolean isEmpty() {
        return options.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return options.contains(o);
    }

    @Override
    public Iterator<SelectionOption<T>> iterator() {
        return options.iterator();
    }

    @Override
    public Object[] toArray() {
        return options.toArray();
    }

    @Override
    public <U> U[] toArray(U[] a) {
        return options.toArray(a);
    }

    @Override
    public boolean add(SelectionOption<T> tSelectionOption) {
        return options.add(tSelectionOption);
    }

    @Override
    public boolean remove(Object o) {
        return options.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return options.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends SelectionOption<T>> c) {
        return options.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends SelectionOption<T>> c) {
        return options.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return options.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return options.retainAll(c);
    }

    @Override
    public void replaceAll(UnaryOperator<SelectionOption<T>> operator) {
        options.replaceAll(operator);
    }

    @Override
    public void sort(Comparator<? super SelectionOption<T>> c) {
        options.sort(c);
    }

    @Override
    public void clear() {
        options.clear();
    }

    @Override
    public SelectionOption<T> get(int index) {
        return options.get(index);
    }

    @Override
    public SelectionOption<T> set(int index, SelectionOption<T> element) {
        return options.set(index, element);
    }

    @Override
    public void add(int index, SelectionOption<T> element) {
        options.add(index, element);
    }

    @Override
    public SelectionOption<T> remove(int index) {
        return options.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return options.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return options.lastIndexOf(o);
    }

    @Override
    public ListIterator<SelectionOption<T>> listIterator() {
        return options.listIterator();
    }

    @Override
    public ListIterator<SelectionOption<T>> listIterator(int index) {
        return options.listIterator(index);
    }

    @Override
    public List<SelectionOption<T>> subList(int fromIndex, int toIndex) {
        return options.subList(fromIndex, toIndex);
    }
}