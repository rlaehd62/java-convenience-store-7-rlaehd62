package store.service;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import store.repository.OrderRepository;
import store.test.order.Order;
import store.model.payment.Receipt;
import store.test.product.Product;
import store.test.product.SalesProduct;
import store.test.product.SalesType;
import store.test.repository.ReceiptRepository;
import store.utility.FlowHandler;

public class PaymentService {

    private final OrderRepository orderRepository;
    private final ReceiptRepository receiptRepository;

    public PaymentService(OrderRepository orderRepository, ReceiptRepository receiptRepository) {
        this.orderRepository = orderRepository;
        this.receiptRepository = receiptRepository;
    }


    public void setMembership(boolean isMembership) {
        receiptRepository.setMembership(isMembership);
    }

    public void confirmMembership(Supplier<Boolean> supplier, Consumer<Exception> failureHandler) {
        FlowHandler.runWithRetry(supplier, () -> setMembership(true), failureHandler);
    }

    public void confirmPayment(Consumer<Order> successHandler) {
        List<Order> orders = orderRepository.findAllWithSalesType(SalesType.NORMAL);
        orders.forEach(order -> confirmOrder(order, successHandler));
    }

    public void confirmOrder(Order order, Consumer<Order> successHandler) {
        SalesProduct salesProduct = order.getSalesProduct();
        Product product = salesProduct.getProduct();
        if (!orderRepository.existOrder(product.getName(), SalesType.PROMOTIONAL)) {
            return;
        }
        successHandler.accept(order);
    }

    public void removeOrderFromReceipt(Order order) {
        List<Order> orders = orderRepository.findAll();
        orders.remove(order);
    }

    public void processReceipt(Consumer<Receipt> consumer) {
        consumer.accept(receiptRepository.getReceipt());
    }

    public void prepareReceipt() {
        List<Order> orders = orderRepository.findAll();
        orders.forEach(receiptRepository::save);
    }
}
