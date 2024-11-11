package store.controller;

import store.config.ServiceBean;
import store.config.constant.Message;
import store.model.order.Order;
import store.model.payment.Receipt;
import store.model.product.Product;
import store.model.product.SalesProduct;
import store.service.order.OrderService;
import store.service.payment.PaymentService;
import store.utility.FlowHandler;
import store.view.InputView;
import store.view.OutputView;
import store.view.callback.PaymentInteface;

public class PaymentController implements PaymentInteface {

    private final PaymentService paymentService;
    private final OrderService orderService;

    public PaymentController() {
        ServiceBean bean = ServiceBean.getInstance();
        paymentService = bean.getPaymentService();
        orderService = bean.getOrderService();
    }

    public void processOrder() {
        paymentService.prepareReceipt();
        paymentService.confirmPayment(this);
        paymentService.confirmMembership(this);
        FlowHandler.run(() -> {
            orderService.runTransaction();
            paymentService.processReceipt(this::printReceipt);
        }, e -> OutputView.of(e.getMessage(), true));
        orderService.clear();
    }

    public void askForPayment(Order order) {
        SalesProduct salesProduct = order.getSalesProduct();
        Product product = salesProduct.getProduct();
        String name = product.getName();
        String quantity = String.valueOf(order.getAmountOfBuy());
        if (!InputView.readForYN(Message.PROMOTION_DISCOUNT, name, quantity)) {
            paymentService.removeOrderFromReceipt(order);
        }
    }

    public void askForMembership() {
        if (InputView.readForYN(Message.MEMBERSHIP, "")) {
            paymentService.setMembership(true);
        }
    }

    private void printReceipt(Receipt receipt) {
        OutputView.printReceipt(receipt);
    }
}