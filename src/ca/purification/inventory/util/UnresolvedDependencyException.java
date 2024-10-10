package ca.purification.inventory.util;

/**
 * The {@code UnresolvedDependencyException} class represents an exception 
 * that is thrown when a dependency cannot be resolved in the 
 * {@code LazyDependencyRegistry}. This exception indicates that an 
 * attempt to retrieve a dependency using a specific key has failed, 
 * signaling that the dependency has not been properly registered or 
 * bound.
 *
 * @see LazyDependencyRegistry
 * @see DependencyAlreadyBoundException
 */
public class UnresolvedDependencyException extends RuntimeException {
    public UnresolvedDependencyException(Object dependencyKey) {
        super("Dependency \"" + dependencyKey + "\" could not be resolved");
    }
}
