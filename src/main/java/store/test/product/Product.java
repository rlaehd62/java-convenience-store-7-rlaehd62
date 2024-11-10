package store.test.product;

import java.util.List;
import store.model.product.Price;

public class Product {

    private final String name;
    private final Price price;

    public Product(String name, int price) {
        this.name = name;
        this.price = new Price(price);
    }

    public static Product of(List<String> elements) {
        String name = elements.get(0);
        int price = Integer.parseInt(elements.get(1));
        return new Product(name, price);
    }

    public String getName() {
        return name;
    }

    public Price getPrice() {
        return price;
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
