package store.mock.repository;

import store.mock.model.FakeSalesPolicy;
import store.repository.policy.SalesPolicyRepository;

public class FakeSalesPolicyRepository extends SalesPolicyRepository {

    public FakeSalesPolicyRepository() {
        save(new FakeSalesPolicy());
    }
}
