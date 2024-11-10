package store.test.policy.manager;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;
import store.test.policy.SalesPolicy;

public class SalesPolicyManager {
    private final Map<String, SalesPolicy> policies;

    public SalesPolicyManager() {
        this.policies = new HashMap<>();
    }

    public void addPolicy(SalesPolicy policy) {
        policies.put(policy.getName(), policy);
    }

    public SalesPolicy removePolicy(String name) {
        return policies.remove(name);
    }

    public boolean existsPolicy(String name) {
        return policies.containsKey(name);
    }

    public Stream<SalesPolicy> stream() {
        return policies.values().stream();
    }

    public void forEach(Consumer<SalesPolicy> consumer) {
        stream().forEach(consumer);
    }

    public Optional<SalesPolicy> getPolicy(String name) {
        if (!existsPolicy(name)) {
            return Optional.empty();
        }
        return Optional.of(policies.get(name));
    }
}
