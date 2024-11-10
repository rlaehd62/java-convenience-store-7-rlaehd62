package store.model.cart;

import java.util.List;

public class CartItem {
    private String name;
    private int quantity;

    public CartItem(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public static CartItem of(List<String> elements) {
        String name = elements.get(0);
        int quantity = Integer.parseInt(elements.get(1));
        return new CartItem(name, quantity);
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
