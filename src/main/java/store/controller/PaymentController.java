package store.controller;

import store.config.ServiceBean;
import store.config.SystemBean;
import store.config.constant.ErrorMessage;
import store.model.product.Inventory;
import store.service.OrderService;
import store.service.Validator;
import store.test.order.Order;
import store.model.payment.Receipt;
import store.service.PaymentService;
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
            paymentService.processReceipt(this::printReceipt);
            orderService.runTransaction();
        }, e -> OutputView.of(e.getMessage(), true));
    }
}