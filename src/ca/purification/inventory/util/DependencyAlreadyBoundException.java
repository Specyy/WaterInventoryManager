package ca.purification.inventory.util;

/**
 * The {@code DependencyAlreadyBoundException} is thrown when there is an attempt 
 * to bind a dependency that has already been bound. This exception extends 
 * {@link RuntimeException} to indicate that the binding error is a runtime issue.
 * 
 * @see LazyDependencyRegistry
 */
public class DependencyAlreadyBoundException extends RuntimeException {
    public DependencyAlreadyBoundException(Object dependencyKey) {
        super("Dependency \"" + dependencyKey + "\" is already bound");
    }
}
