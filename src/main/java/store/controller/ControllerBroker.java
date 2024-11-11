package store.controller;

import store.service.Validator;
import store.utility.FlowHandler;
import store.view.InputView;
import store.view.OutputView;

public class ControllerBroker {
    private final FrontController frontController;
    private final CartController cartController;
    private final OrderController orderController;
    private final PaymentController paymentController;

    private final Validator validator;

    public ControllerBroker() {
        frontController = new FrontController();
        cartController = new CartController();
        orderController = new OrderController();
        paymentController = new PaymentController();
        validator = new Validator();
    }

    private void runMainProcess() {
        FlowHandler.run(() -> {
            frontController.showFront();
            cartController.receiveOrder();
            orderController.toOrder();
        }, e -> {
            OutputView.of("");
            OutputView.of(e.getMessage());
        });

        paymentController.processOrder();
    }

    private boolean askForRetry() {
        return FlowHandler.runWithRetry(() -> {
            String yn = InputView.readForRetry();
            return validator.isYesOrNo(yn);
        }, (e) -> OutputView.of(e.getMessage()));
    }

    public void run() {
        runMainProcess();
        if (askForRetry()) {
            run();
        }
    }
}
