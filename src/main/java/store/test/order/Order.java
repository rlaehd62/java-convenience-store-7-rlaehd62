package store.test.order;

import store.test.policy.SalesPolicy;
import store.test.product.SalesProduct;

public class Order {
    private final SalesProduct salesProduct;
    private int amountOfBuy;
    private int amountOfGet;

    public Order(SalesProduct salesProduct, int totalQuantity) {
        this.salesProduct = salesProduct;
        SalesPolicy policy = salesProduct.getPolicy();
        this.amountOfGet = policy.extractAmountOfGet(totalQuantity);
        this.amountOfBuy = totalQuantity - this.amountOfGet;
    }

    public SalesProduct getSalesProduct() {
        return salesProduct;
    }

    public int getAmountOfGet() {
        return amountOfGet;
    }

    public void setAmountOfGet(int amountOfGet) {
        this.amountOfGet = amountOfGet;
    }

    public int getAmountOfBuy() {
        return amountOfBuy;
    }

    public void setAmountOfBuy(int amountOfBuy) {
        this.amountOfBuy = amountOfBuy;
    }

    public int getTotalQuantity() {
        return amountOfBuy + amountOfGet;
    }
}
