package store.repository.order;

import java.util.List;
import store.model.order.Order;
import store.model.order.manager.OrderManager;
import store.model.product.SalesProduct;
import store.model.product.SalesType;

public class OrderRepository {

    private OrderManager orderManager;

    public OrderRepository() {
        orderManager = new OrderManager();
    }

    public List<Order> findAll() {
        return orderManager.getOrders();
    }

    public void drop(Order order) {
        orderManager.removeOrder(order);
    }

    public boolean existOrder(String name, SalesType type) {
        return orderManager.getOrders()
                .stream()
                .map(Order::getSalesProduct)
                .filter(salesProduct -> salesProduct.isType(type))
                .map(SalesProduct::getProduct)
                .anyMatch(product -> product.getName().equals(name));
    }

    public List<Order> findAllWithSalesType(SalesType type) {
        return orderManager.getOrders().stream()
                .filter(order -> order.getSalesProduct().isType(type))
                .toList();
    }

    public void save(Order order) {
        if (!orderManager.existsOrder(order)) {
            orderManager.addOrder(order);
        }
    }

    public void clear() {
        orderManager.getOrders().clear();
    }
}
