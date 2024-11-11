package store.controller;

import store.config.ServiceBean;
import store.config.constant.ErrorMessage;
import store.model.cart.CartItem;
import store.service.Validator;
import store.service.cart.CartService;
import store.utility.FlowHandler;
import store.view.InputView;
import store.view.OutputView;

public class CartController {
    private final Validator validator;
    private final CartService service;

    public CartController() {
        ServiceBean bean = ServiceBean.getInstance();
        validator = new Validator();
        service = bean.getCartService();
    }

    public void receiveOrder() {
        FlowHandler.runWithRetry(() -> {
            takeOrder();
            searchForGiveAways();
            return true;
        }, e -> OutputView.of(e.getMessage(), true));
    }

    private void takeOrder() {
        String orderInput = InputView.readOrder();
        validator.isValidOrder(orderInput);
        service.createCart(orderInput);
        service.checkValidityOfCart();
    }

    private void searchForGiveAways() {
        service.searchForGiveAways(this::askForGiveAways);
    }

    private void askForGiveAways(CartItem cartItem) {
        String name = cartItem.getName();
        FlowHandler.runWithRetry(() -> {
            String yn = InputView.readForGiveAways(name);
            return validator.isYesOrNo(yn);
        }, () -> service.updateGiveAways(cartItem), e -> OutputView.of(ErrorMessage.YN_FAILURE, true));
    }


}
