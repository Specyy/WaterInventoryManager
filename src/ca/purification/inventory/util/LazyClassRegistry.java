package ca.purification.inventory.util;

/**
 * The {@code LazyClassRegistry} is a specialized registry for managing 
 * dependencies associated with specific class types. It extends 
 * {@link LazyDependencyRegistry} to allow for lazy resolution of 
 * dependencies based on their class types, enabling efficient memory 
 * usage and initialization.
 *
 * @param <V> the base type of the dependencies managed by this registry
 * @see LazyDependencyRegistry
 */
public class LazyClassRegistry<V> extends LazyDependencyRegistry<Class<? extends V>, V> {
    public <U extends V> LazyDependency<U> castDependency(Class<U> valueClass) {
        return super.getDependency(valueClass).cast();
    }
}
