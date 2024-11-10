package store.test.product.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import store.test.product.Product;
import store.test.product.SalesProduct;
import store.test.product.SalesType;

public class SalesProductManager {
    private final List<SalesProduct> products;

    public SalesProductManager() {
        this.products = new ArrayList<>();
    }

    private Integer createKey() {
        return products.size() + 1;
    }

    public void addProduct(SalesProduct product) {
        products.add(product);
    }

    public boolean removeProduct(SalesProduct product) {
        return products.remove(product);
    }

    public boolean existsProduct(SalesProduct product) {
        return products.contains(product);
    }

    public List<SalesProduct> getProducts() {
        return products;
    }

    public Stream<SalesProduct> stream() {
        return products.stream();
    }

    public void forEach(Consumer<SalesProduct> consumer) {
        stream().forEach(consumer);
    }

    public Stream<SalesProduct> filter(Predicate<? super SalesProduct> predicate) {
        return stream().filter(predicate);
    }

    public List<SalesProduct> getProductsByType(SalesType type) {
        return filter(product -> product.getSalesType().equals(type))
                .toList();
    }

    public Optional<SalesProduct> getProduct(String name) {
        for (SalesProduct salesProduct : products) {
            Product product = salesProduct.getProduct();
            if (name.equals(product.getName())) {
                return Optional.of(salesProduct);
            }
        }
        return Optional.empty();
    }
}
