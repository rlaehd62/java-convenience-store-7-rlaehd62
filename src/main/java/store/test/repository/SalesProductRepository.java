package store.test.repository;

import java.util.List;
import java.util.Optional;
import store.config.constant.ErrorMessage;
import store.exception.ProductException;
import store.test.product.Product;
import store.test.product.SalesProduct;
import store.test.product.SalesType;
import store.test.product.manager.SalesProductManager;

public class SalesProductRepository {

    private final SalesProductManager manager;

    public SalesProductRepository() {
        manager = new SalesProductManager();
    }

    public void save(SalesProduct product) {
        manager.addProduct(product);
    }

    public List<SalesProduct> findAll() {
        return manager.getProducts();
    }

    public List<String> findAllNames() {
        return manager.getProducts()
                .stream()
                .map(SalesProduct::getProduct)
                .map(Product::getName)
                .toList();
    }

    public boolean existsSalesProduct(String name) {
        return manager.getProducts()
                .stream()
                .map(SalesProduct::getProduct)
                .map(Product::getName)
                .anyMatch(name::equals);
    }

    public SalesProduct findSalesProductsByName(String name, SalesType type) {
        List<SalesProduct> products = manager.getProductsByType(type);
        return products.stream()
                .filter(salesProduct -> {
                    Product product = salesProduct.getProduct();
                    return product.getName().equals(name);
                })
                .findFirst()
                .orElseThrow(() -> new ProductException(ErrorMessage.NO_ITEM_FOUND));
    }

    public SalesProduct findSalesProductsByName(String name) {
        Optional<SalesProduct> optional = manager.getProduct(name);
        optional.orElseThrow(() -> new ProductException(ErrorMessage.NO_ITEM_FOUND));
        return optional.get();
    }
}
