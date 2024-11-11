package store.repository.policy;

import store.model.policy.DefaultPolicy;
import store.model.policy.SalesPolicy;
import store.model.policy.manager.SalesPolicyManager;

public class SalesPolicyRepository {

    private final SalesPolicyManager manager;
    private final DefaultPolicy DEFAULT_POLICY;

    public SalesPolicyRepository() {
        manager = new SalesPolicyManager();
        DEFAULT_POLICY = new DefaultPolicy("");
    }

    public DefaultPolicy DEFAULT_POLICY() {
        return DEFAULT_POLICY;
    }

    public SalesPolicy findPolicy(String name) {
        return manager.getPolicy(name)
                .filter(SalesPolicy::isAvailable)
                .orElse(DEFAULT_POLICY);
    }

    public void save(SalesPolicy policy) {
        if (!manager.existsPolicy(policy.getName())) {
            manager.addPolicy(policy);
        }
    }
}
