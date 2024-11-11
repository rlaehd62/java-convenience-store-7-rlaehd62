package store.mock.service;

import store.mock.repository.FakeSalesPolicyRepository;
import store.mock.repository.FakeSalesProductRepository;
import store.service.product.SalesProductService;

public class FakeSalesProductService extends SalesProductService {
    public FakeSalesProductService() {
        super(new FakeSalesPolicyRepository(), new FakeSalesProductRepository());
    }
}
