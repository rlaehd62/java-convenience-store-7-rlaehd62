package store.mock.service;

import store.mock.repository.FakeOrderRepository;
import store.repository.payment.ReceiptRepository;
import store.service.payment.PaymentService;

public class FakePaymentService extends PaymentService {
    public FakePaymentService() {
        super(new FakeOrderRepository(), new ReceiptRepository());
    }
}
