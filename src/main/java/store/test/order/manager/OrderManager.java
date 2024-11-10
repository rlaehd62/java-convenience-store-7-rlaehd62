package store.test.order.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import store.test.order.Order;
import store.test.product.Product;
import store.test.product.SalesProduct;
import store.test.product.SalesType;

public class OrderManager {
    private final List<Order> orders;

    public OrderManager() {
        this.orders = new ArrayList<>();
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public boolean removeOrder(Order order) {
        return orders.remove(order);
    }

    public boolean existsOrder(Order order) {
        return orders.contains(order);
    }

    public List<Order> getOrders() {
        return orders;
    }

    public Stream<Order> stream() {
        return orders.stream();
    }

    public void forEach(Consumer<Order> consumer) {
        stream().forEach(consumer);
    }

    public Stream<Order> filter(Predicate<? super Order> predicate) {
        return stream().filter(predicate);
    }

    public Optional<Order> getOrder(String name, SalesType type) {
        for (Order order : orders) {
            SalesProduct salesProduct = order.getSalesProduct();
            Product product = salesProduct.getProduct();
            String productName = product.getName();
            if (name.equals(productName) && salesProduct.isType(type)) {
                return Optional.of(order);
            }
        }
        return Optional.empty();
    }
}
