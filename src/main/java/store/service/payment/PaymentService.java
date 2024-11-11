package store.service.payment;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import store.model.order.Order;
import store.model.payment.Receipt;
import store.model.product.Product;
import store.model.product.SalesProduct;
import store.model.product.SalesType;
import store.repository.order.OrderRepository;
import store.repository.payment.ReceiptRepository;
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
        orderRepository.drop(order);
        receiptRepository.drop(order);
    }

    public void processReceipt(Consumer<Receipt> consumer) {
        consumer.accept(receiptRepository.getReceipt());
    }

    public void prepareReceipt() {
        List<Order> orders = orderRepository.findAll();
        orders.forEach(receiptRepository::save);
    }
}
