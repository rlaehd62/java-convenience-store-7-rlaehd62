package store.repository.cart;

import java.util.List;
import store.model.cart.Cart;
import store.model.cart.CartItem;

public class CartRepository {

    private Cart cart;

    public CartRepository() {
        cart = new Cart();
    }

    public List<CartItem> findAll() {
        return cart.getItems();
    }

    public void clear() {
        cart.getItems().clear();
    }

    public void save(CartItem cartItem) {
        cart.addItem(cartItem);
    }
}
