package store.model.cart;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Cart {
    private final List<CartItem> items;

    public Cart() {
        items = new ArrayList<>();
    }

    public void addItem(CartItem item) {
        items.add(item);
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void forEach(Consumer<CartItem> consumer) {
        items.forEach(consumer);
    }

    public Stream<CartItem> filter(Predicate<? super CartItem> predicate) {
        return items.stream().filter(predicate);
    }
}
