package store.model.product.manager;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;
import store.model.product.Product;

public class ProductManager {
    private final Map<String, Product> products;

    public ProductManager() {
        this.products = new HashMap<>();
    }

    public void addProduct(Product product) {
        products.put(product.getName(), product);
    }

    public Product removeProduct(String name) {
        return products.remove(name);
    }

    public boolean existsProduct(String name) {
        return products.containsKey(name);
    }

    public Stream<Product> stream() {
        return products.values().stream();
    }

    public void forEach(Consumer<Product> consumer) {
        stream().forEach(consumer);
    }

    public Optional<Product> getProduct(String name) {
        if (!existsProduct(name)) {
            return Optional.empty();
        }
        return Optional.of(products.get(name));
    }
}
