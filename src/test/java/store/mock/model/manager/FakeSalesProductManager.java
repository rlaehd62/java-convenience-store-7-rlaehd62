package store.mock.model.manager;

import store.model.product.SalesType;
import store.model.product.manager.SalesProductManager;

public class FakeSalesProductManager extends SalesProductManager {
    public FakeSalesProductManager() {
        addProduct(new FakeSalesProduct(SalesType.PROMOTIONAL));
        addProduct(new FakeSalesProduct(SalesType.NORMAL));
    }
}
