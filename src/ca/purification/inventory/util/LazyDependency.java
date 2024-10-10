package ca.purification.inventory.util;

import java.util.function.Supplier;

/**
 * The {@code LazyDependency} class provides a mechanism for lazily 
 * resolving dependencies, allowing for deferred initialization and 
 * optional caching of the resolved value. This is particularly useful 
 * in scenarios where the creation of a dependency is expensive or 
 * when it may not be needed immediately.
 *
 * @param <T> the type of the dependency managed by this class
 * @see Supplier
 */
public class LazyDependency<T> {
    protected T cachedValue;
    protected boolean isCacheable;
    protected Supplier<T> valueFactory;

    public LazyDependency(Supplier<T> valueFactory) {
        this(valueFactory, true);
    }

    public LazyDependency(Supplier<T> valueFactory, boolean isCacheable) {
        this.valueFactory = valueFactory;
        this.isCacheable = isCacheable;
    }

    @SuppressWarnings("unchecked")
    public <C> LazyDependency<C> cast() {
        return new LazyDependency<>(() -> (C) resolve(), isCacheable);
    }

    public T resolve() {
        if (isCacheable && cachedValue != null) {
            return cachedValue;
        }

        T value = valueFactory.get();
        return isCacheable ? cachedValue = value : value;
    }

    public boolean isCacheable() {
        return isCacheable;
    }

    public void setCacheable(boolean cacheable) {
        if (!cacheable) {
            cachedValue = null;
        }

        isCacheable = cacheable;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj == this) {
            return true;
        }

        return (obj instanceof LazyDependency<?> other) && hashCode() == other.hashCode();
    }

    @Override
    public int hashCode() {
        return valueFactory.hashCode();
    }
}
