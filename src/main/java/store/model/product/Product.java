package store.model.product;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Product {

    private final String name;
    private final Price price;
    private final List<SalesProduct> salesProducts;

    public Product(String name, int price) {
        this.name = name;
        this.price = new Price(price);
        this.salesProducts = new ArrayList<>();
    }

    public static Product of(List<String> elements) {
        String name = elements.get(0);
        int price = Integer.parseInt(elements.get(1));
        return new Product(name, price);
    }

    public void addSalesProduct(SalesProduct salesProduct) {
        salesProducts.add(salesProduct);
    }

    public String getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }

    public List<SalesProduct> getSalesProducts() {
        return salesProducts;
    }

    public void forEach(Consumer<SalesProduct> consumer) {
        salesProducts.forEach(consumer);
    }

    @Override
    public boolean equals(Object obj) {
        Product others = (Product) obj;
        return name.equals(others.name);
    }

    @Override
    public String toString() {
        return String.format("%s %s", name, price);
    }
}
