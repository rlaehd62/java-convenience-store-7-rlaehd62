package store.mock.service;

import store.mock.repository.FakeSalesPolicyRepository;
import store.service.policy.SalesPolicyService;

public class FakeSalesPolicyService extends SalesPolicyService {
    public FakeSalesPolicyService() {
        super(new FakeSalesPolicyRepository());
    }
}
