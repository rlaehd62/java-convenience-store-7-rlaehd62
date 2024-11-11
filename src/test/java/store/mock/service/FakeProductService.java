package store.mock.service;

import store.mock.repository.FakeProductRepository;
import store.mock.repository.FakeSalesPolicyRepository;
import store.mock.repository.FakeSalesProductRepository;
import store.service.product.ProductService;

public class FakeProductService extends ProductService {
    public FakeProductService() {
        super(new FakeProductRepository(), new FakeSalesProductRepository(), new FakeSalesPolicyRepository());
    }
}
