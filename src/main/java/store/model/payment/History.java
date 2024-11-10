package store.model.payment;

import store.model.product.Price;
import store.test.order.Order;
import store.test.product.Product;
import store.test.product.SalesProduct;

public class History {

    private final String name;
    private int quantity;
    private int totalPrice;

    public History(String name) {
        this.name = name;
        this.quantity = 0;
        this.totalPrice = 0;
    }

    public void add(Order order) {
        SalesProduct salesProduct = order.getSalesProduct();
        Product product = salesProduct.getProduct();
        Price price = product.getPrice();
        int totalQuantity = order.getTotalQuantity();
        setQuantity(quantity + totalQuantity);
        setTotalPrice(totalPrice + price.calculate(totalQuantity));
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

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
