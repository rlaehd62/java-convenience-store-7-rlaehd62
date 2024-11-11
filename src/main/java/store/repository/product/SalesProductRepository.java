package store.repository.product;

import java.util.List;
import store.config.constant.ErrorMessage;
import store.exception.ProductException;
import store.model.product.Product;
import store.model.product.SalesProduct;
import store.model.product.SalesType;
import store.model.product.manager.SalesProductManager;

public class SalesProductRepository {

    private final SalesProductManager manager;

    public SalesProductRepository() {
        manager = new SalesProductManager();
    }

    public boolean save(SalesProduct product) {
        String name = product.getProduct().getName();
        SalesType type = product.getSalesType();
        if (existsSalesProductWithType(name, type)) {
            return false;
        }
        manager.addProduct(product);
        return true;
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

    public boolean existsSalesProductWithType(String name, SalesType type) {
        return manager.getProducts()
                .stream()
                .filter(salesProduct -> salesProduct.isType(type))
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
}
