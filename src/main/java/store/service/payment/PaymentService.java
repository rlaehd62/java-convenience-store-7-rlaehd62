package store.service.payment;

import java.util.List;
import java.util.function.Consumer;
import store.model.order.Order;
import store.model.payment.Receipt;
import store.model.product.Product;
import store.model.product.SalesProduct;
import store.model.product.SalesType;
import store.repository.order.OrderRepository;
import store.repository.payment.ReceiptRepository;
import store.view.callback.PaymentInteface;

public class PaymentService {

    private final OrderRepository orderRepository;
    private final ReceiptRepository receiptRepository;

    public PaymentService(OrderRepository orderRepository, ReceiptRepository receiptRepository) {
        this.orderRepository = orderRepository;
        this.receiptRepository = receiptRepository;
    }

    public void prepareReceipt() {
        List<Order> orders = orderRepository.findAll();
        orders.forEach(receiptRepository::save);
    }

    public void confirmMembership(PaymentInteface paymentInteface) {
        paymentInteface.askForMembership();
    }

    public void setMembership(boolean isMembership) {
        receiptRepository.setMembership(isMembership);
    }


    public void confirmPayment(PaymentInteface paymentInteface) {
        List<Order> orders = orderRepository.findAllWithSalesType(SalesType.NORMAL);
        orders.forEach(order -> confirmOrder(order, paymentInteface));
    }

    public void removeOrderFromReceipt(Order order) {
        orderRepository.drop(order);
        receiptRepository.drop(order);
    }

    public void confirmOrder(Order order, PaymentInteface paymentInteface) {
        SalesProduct salesProduct = order.getSalesProduct();
        Product product = salesProduct.getProduct();
        if (!orderRepository.existOrder(product.getName(), SalesType.PROMOTIONAL)) {
            return;
        }
        paymentInteface.askForPayment(order);
    }

    public void processReceipt(Consumer<Receipt> consumer) {
        consumer.accept(receiptRepository.getReceipt());
    }
}
