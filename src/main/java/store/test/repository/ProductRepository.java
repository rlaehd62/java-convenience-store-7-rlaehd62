package store.test.repository;

import java.util.Optional;
import store.model.product.Price;
import store.test.product.Product;
import store.test.product.manager.ProductManager;

public class ProductRepository {

    private final ProductManager manager;

    public ProductRepository() {
        manager = new ProductManager();
    }

    public void save(Product product) {
        if (!manager.existsProduct(product.getName())) {
            manager.addProduct(product);
        }
    }

    public Optional<Product> findProduct(String name) {
        return manager.getProduct(name);
    }

    public Optional<Price> findPriceOfProduct(String name) {
        Optional<Product> optional = manager.getProduct(name);
        if (optional.isEmpty()) {
            return Optional.empty();
        }
        Product product = optional.get();
        return Optional.of(product.getPrice());
    }

    public boolean existsProduct(String name) {
        return manager.existsProduct(name);
    }
}
