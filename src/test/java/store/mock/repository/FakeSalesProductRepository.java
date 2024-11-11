package store.mock.repository;

import store.mock.model.manager.FakeSalesProduct;
import store.model.product.SalesType;
import store.repository.product.SalesProductRepository;

public class FakeSalesProductRepository extends SalesProductRepository {
    public FakeSalesProductRepository() {
        save(new FakeSalesProduct(SalesType.PROMOTIONAL));
        save(new FakeSalesProduct(SalesType.NORMAL));
    }
}
