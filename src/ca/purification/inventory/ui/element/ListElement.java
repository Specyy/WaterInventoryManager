package ca.purification.inventory.ui.element;

import java.util.*;
import java.util.function.IntFunction;
import java.util.function.UnaryOperator;

/**
 * The {@code ListElement} class represents a customizable list of UI elements.
 * It extends the {@link UIElement} class and implements the {@link List} interface,
 * allowing for standard list operations while maintaining styling properties.
 *
 * <p>This class supports various ways to initialize a list of elements, including
 * using varargs, collections, iterators, and other iterable structures. Additionally,
 * it provides a customizable marker generator for numbering list items.</p>
 *
 * @param <T> the type of elements contained in this list, which must extend {@link UIElement}.
 *           
 * @see UIElement
 */
public class ListElement<T extends UIElement> extends UIElement implements List<T> {
    private final List<T> list;

    private IntFunction<String> markerGenerator = index -> (index + 1) + ". ";

    public ListElement() {
        this.list = new ArrayList<>();
    }

    @SafeVarargs
    public ListElement(T... elements) {
        this.list = new ArrayList<>(elements.length);
        Collections.addAll(this, elements);
    }

    public ListElement(Collection<? extends T> elements) {
        this.list = new ArrayList<>(elements);
    }

    public ListElement(Iterator<T> iterator) {
        this();
        iterator.forEachRemaining(this::add);
    }

    public ListElement(Iterable<T> iterable) {
        this();
        iterable.forEach(this::add);
    }

    public IntFunction<String> getMarkerGenerator() {
        return markerGenerator;
    }

    public void setMarkerGenerator(IntFunction<String> numberGenerator) {
        this.markerGenerator = numberGenerator;
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }

    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @Override
    public <U> U[] toArray(U[] a) {
        return list.toArray(a);
    }

    @Override
    public boolean add(T t) {
        return list.add(t);
    }

    @Override
    public boolean remove(Object o) {
        return list.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return list.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return list.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        return list.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return list.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return list.retainAll(c);
    }

    @Override
    public void replaceAll(UnaryOperator<T> operator) {
        list.replaceAll(operator);
    }

    @Override
    public void sort(Comparator<? super T> c) {
        list.sort(c);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public T get(int index) {
        return list.get(index);
    }

    @Override
    public T set(int index, T element) {
        return list.set(index, element);
    }

    @Override
    public void add(int index, T element) {
        list.add(index, element);
    }

    @Override
    public T remove(int index) {
        return list.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }

    @Override
    public ListIterator<T> listIterator() {
        return list.listIterator();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return list.listIterator(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return list.subList(fromIndex, toIndex);
    }
}
