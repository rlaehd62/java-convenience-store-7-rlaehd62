package store.model.order;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Order {
    private final List<OrderItem> orderItems;

    public Order() {
        orderItems = new ArrayList<>();
    }

    public void forEach(Consumer<OrderItem> consumer) {
        orderItems.forEach(consumer);
    }

    public List<OrderItem> filter(Predicate<? super OrderItem> predicate) {
        return orderItems.stream()
                .filter(predicate)
                .toList();
    }
}
