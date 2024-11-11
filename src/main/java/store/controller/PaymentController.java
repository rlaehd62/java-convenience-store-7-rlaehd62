package store.controller;

import store.config.ServiceBean;
import store.model.order.Order;
import store.model.payment.Receipt;
import store.service.Validator;
import store.service.order.OrderService;
import store.service.payment.PaymentService;
import store.utility.FlowHandler;
import store.view.InputView;
import store.view.OutputView;

public class PaymentController {

    private final PaymentService paymentService;
    private final OrderService orderService;
    private final Validator validator;

    public PaymentController() {
        ServiceBean bean = ServiceBean.getInstance();
        validator = new Validator();
        paymentService = bean.getPaymentService();
        orderService = bean.getOrderService();
    }

    private boolean askForMembership() {
        String yn = InputView.readForMembershipDiscount();
        return validator.isYesOrNo(yn);
    }

    private void askForPayment(Order order) {
        FlowHandler.runWithRetry(() -> {
                    String yn = InputView.readForPromotion(order);
                    return !validator.isYesOrNo(yn);
                },
                () -> paymentService.removeOrderFromReceipt(order),
                (e) -> OutputView.of(e.getMessage()));
    }

    private void printReceipt(Receipt receipt) {
        OutputView.printReceipt(receipt);
    }

    public void processOrder() {
        paymentService.prepareReceipt();
        paymentService.confirmPayment(this::askForPayment);
        paymentService.confirmMembership(this::askForMembership, e -> OutputView.of(e.getMessage(), true));
        FlowHandler.run(() -> {
            orderService.runTransaction();
            paymentService.processReceipt(this::printReceipt);
        }, e -> OutputView.of(e.getMessage(), true));
        orderService.clear();
    }
}