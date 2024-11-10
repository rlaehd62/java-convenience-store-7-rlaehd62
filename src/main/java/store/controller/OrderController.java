package store.controller;

import store.config.ServiceBean;
import store.service.OrderService;

public class OrderController {
    // TODO: test 패키지의 MVC로 교체하기
    private final OrderService service;

    public OrderController() {
        ServiceBean bean = ServiceBean.getInstance();
        service = bean.getOrderService();
    }

    public void toOrder() {
        service.makeOrders();
    }
}
