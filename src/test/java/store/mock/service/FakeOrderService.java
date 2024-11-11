package store.mock.service;

import store.mock.repository.FakeCartRepository;
import store.mock.repository.FakeOrderRepository;
import store.mock.repository.FakeSalesProductRepository;
import store.repository.payment.ReceiptRepository;
import store.service.order.OrderService;

public class FakeOrderService extends OrderService {
    public FakeOrderService() {
        super(new FakeOrderRepository(), new ReceiptRepository(), new FakeCartRepository(),
                new FakeSalesProductRepository(), new FakeSalesProductService());
    }
}
