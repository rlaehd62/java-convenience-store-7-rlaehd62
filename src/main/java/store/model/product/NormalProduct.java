package store.model.product;

import java.util.List;
import store.config.constant.Message;

public class NormalProduct {
    private final String name;
    private final Price price;

    public NormalProduct(String name, int money) {
        this.name = name;
        this.price = new Price(money);
    }

    public static NormalProduct of(List<String> elements) {
        String name = elements.get(0);
        int price = Integer.parseInt(elements.get(1));
        return new NormalProduct(name, price);
    }

    public String getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return String.format(Message.PRODUCT.toString(), name, price);
    }
}
