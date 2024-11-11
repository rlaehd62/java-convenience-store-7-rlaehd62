package store.controller;

import store.config.ServiceBean;
import store.service.order.OrderService;

public class OrderController {
    private final OrderService service;

    public OrderController() {
        ServiceBean bean = ServiceBean.getInstance();
        service = bean.getOrderService();
    }

    public void toOrder() {
        service.makeOrders();
    }
}
