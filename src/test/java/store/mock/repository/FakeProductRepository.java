package store.mock.repository;

import store.mock.model.FakeProduct;
import store.repository.product.ProductRepository;

public class FakeProductRepository extends ProductRepository {

    public FakeProductRepository() {
        save(new FakeProduct());
    }
}
