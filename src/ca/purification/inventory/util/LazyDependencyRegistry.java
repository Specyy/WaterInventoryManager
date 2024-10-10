package ca.purification.inventory.util;

import java.util.*;
import java.util.function.Supplier;

/**
 * The {@code LazyDependencyRegistry} class provides a registry for managing 
 * lazily resolved dependencies, allowing for the registration and retrieval 
 * of dependencies based on specified keys. This class supports deferred 
 * initialization and enables the handling of both resolved and unresolved 
 * dependencies efficiently.
 *
 * @param <K> the type of keys used to identify dependencies
 * @param <T> the type of dependencies managed by this registry
 * @see LazyDependency
 * @see DependencyAlreadyBoundException
 * @see UnresolvedDependencyException
 */
public class LazyDependencyRegistry<K, T> implements Iterable<LazyDependency<T>> {
    protected final Map<K, LazyDependency<T>> registry = new HashMap<>();

    public void registerAll(LazyDependencyRegistry<K, T> dependencies) {
        registry.putAll(dependencies.registry);
    }

    public LazyDependencyRegistry<K, T> register(K dependencyKey, Supplier<T> valueFactory) {
        if (registry.containsKey(dependencyKey)) {
            if (!(registry.get(dependencyKey) instanceof DependencyCandidate<T> candidate) || candidate.isBound) {
                throw new DependencyAlreadyBoundException(dependencyKey);
            }

            candidate.bind(valueFactory);
            return this;
        }

        registry.put(dependencyKey, new LazyDependency<>(valueFactory));
        return this;
    }

    public LazyDependencyRegistry<K, T> registerIfAbsent(K dependencyKey, Supplier<T> valueFactory) {
        return isRegistered(dependencyKey) ? this : register(dependencyKey, valueFactory);
    }

    public LazyDependencyRegistry<K, T> register(K dependencyKey, LazyDependency<T> dependency) {
        if (registry.containsKey(dependencyKey)) {
            if (!(registry.get(dependencyKey) instanceof DependencyCandidate<T> candidate) || candidate.isBound) {
                throw new DependencyAlreadyBoundException(dependencyKey);
            }

            candidate.bind(dependency);
            return this;
        }

        registry.put(dependencyKey, dependency);
        return this;
    }

    public LazyDependencyRegistry<K, T> registerIfAbsent(K registryKey, LazyDependency<T> dependency) {
        return isRegistered(registryKey) ? this : register(registryKey, dependency);
    }

    public T resolve(K registryKey) {
        return getDependency(registryKey).resolve();
    }

    public boolean isRegistered(K dependencyKey) {
        if (!registry.containsKey(dependencyKey)) {
            return false;
        }

        if (registry.get(dependencyKey) instanceof DependencyCandidate<T> candidate) {
            return candidate.isBound;
        }

        return true;
    }

    public boolean isRegistered(LazyDependency<T> dependency) {
        for (LazyDependency<T> registeredDependency : registry.values()) {
            LazyDependency<T> actualDependency = registeredDependency instanceof DependencyCandidate<T> candidate 
                    && candidate.innerDependency != null 
                    ? candidate.innerDependency 
                    : registeredDependency;
            
            if (actualDependency.equals(dependency)) {
                return true;
            }
        }

        return false;
    }

    public LazyDependency<T> getDependency(K dependencyKey) {
        if (!registry.containsKey(dependencyKey)) {
            DependencyCandidate<T> candidate = new DependencyCandidate<>(dependencyKey);
            registry.put(dependencyKey, candidate);
            return candidate;
        }

        LazyDependency<T> dependency = registry.get(dependencyKey);

        return dependency instanceof DependencyCandidate<T> candidate
                && candidate.innerDependency != null ? candidate.innerDependency : dependency;
    }

    @Override
    public Iterator<LazyDependency<T>> iterator() {
        Collection<LazyDependency<T>> registryValues = this.registry.values();
        List<LazyDependency<T>> values = new ArrayList<>(registryValues.size());

        registryValues.iterator().forEachRemaining((value) -> {
            LazyDependency<T> dependency = value;

            if (dependency instanceof DependencyCandidate<T> candidate
                    && candidate.innerDependency != null) {
                dependency = candidate.innerDependency;
            }

            values.add(dependency);
        });

        return values.iterator();
    }

    /**
     * The {@code DependencyCandidate} class represents a candidate dependency 
     * in the {@code LazyDependencyRegistry}. It encapsulates a lazy dependency 
     * that can be bound to a value factory or another dependency. If the 
     * dependency is unresolved, an exception will be thrown upon resolution. 
     * This class allows for dynamic binding and management of dependencies, 
     * enabling effective handling of both resolved and unresolved states.
     *
     * @param <T> the type of the dependency
     * @see LazyDependency
     * @see UnresolvedDependencyException
     * @see LazyDependencyRegistry
     */
    private static class DependencyCandidate<T> extends LazyDependency<T> {
        private boolean isBound;
        private LazyDependency<T> innerDependency;

        public DependencyCandidate(Object dependencyKey) {
            super(() -> {
                throw new UnresolvedDependencyException(dependencyKey);
            });
        }

        @Override
        public T resolve() {
            return innerDependency == null ? super.resolve() : innerDependency.resolve();
        }

        @Override
        public void setCacheable(boolean cacheable) {
            if (innerDependency == null) {
                super.setCacheable(cacheable);
            } else {
                innerDependency.setCacheable(cacheable);
            }
        }

        @Override
        public boolean isCacheable() {
            return innerDependency == null ? super.isCacheable() : innerDependency.isCacheable();
        }

        public void bind(Supplier<T> valueFactory) {
            this.valueFactory = valueFactory;
            isBound = true;
        }

        public void bind(LazyDependency<T> innerDependency) {
            this.innerDependency = innerDependency;
            isBound = true;
        }
    }
}