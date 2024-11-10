package store.repository;

import java.util.List;
import java.util.Optional;
import store.model.order.OrderItem;
import store.model.product.Inventory;
import store.model.product.NormallItem;
import store.model.product.PromotionalItem;
import store.test.order.Order;
import store.test.order.manager.OrderManager;
import store.test.product.SalesProduct;
import store.test.product.SalesType;

public class OrderRepository {

    private Inventory inventory;
    private OrderManager orderManager;

    public OrderRepository() {
        orderManager = new OrderManager();
    }

    public List<Order> findAll() {
        return orderManager.getOrders();
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

    private boolean hasEnoughQuantityOfNormals(OrderItem orderItem) {
        String name = orderItem.getName();
        Optional<NormallItem> optional = inventory.findNormalItem(name);
        if (optional.isEmpty()) {
            return false;
        }

        NormallItem normallItem = optional.get();
        int quantity = normallItem.getQuantity();
        return quantity >= orderItem.getTotalQuantity();
    }

    private boolean hasEnoughQuantityOfPromotionals(OrderItem orderItem) {
        String name = orderItem.getName();
        Optional<PromotionalItem> optional = inventory.findPromotionalItem(name);
        if (optional.isEmpty()) {
            return false;
        }

        PromotionalItem promotionalItem = optional.get();
        int quantity = promotionalItem.getQuantity();
        return quantity >= orderItem.getTotalQuantity();
    }

    public boolean hasEnoughQuantity(OrderItem orderItem) {
        return hasEnoughQuantityOfNormals(orderItem) || hasEnoughQuantityOfPromotionals(orderItem);
    }
}
