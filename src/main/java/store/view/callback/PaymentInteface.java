package store.view.callback;

import store.model.order.Order;

public interface PaymentInteface {

    public void askForMembership();

    public void askForPayment(Order order);
}
