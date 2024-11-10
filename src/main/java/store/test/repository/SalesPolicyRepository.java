package store.test.repository;

import store.test.policy.DefaultPolicy;
import store.test.policy.SalesPolicy;
import store.test.policy.manager.SalesPolicyManager;

public class SalesPolicyRepository {

    private final SalesPolicyManager manager;
    private final DefaultPolicy DEFAULT_POLICY;

    public SalesPolicyRepository() {
        manager = new SalesPolicyManager();
        DEFAULT_POLICY = new DefaultPolicy("");
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
