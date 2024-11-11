package store.mock.model.manager;

import store.mock.model.FakeProduct;
import store.mock.model.FakeSalesPolicy;
import store.model.product.SalesProduct;
import store.model.product.SalesType;

public class FakeSalesProduct extends SalesProduct {
    public FakeSalesProduct(SalesType type) {
        super(new FakeProduct(), 10, new FakeSalesPolicy(), type);
    }
}
