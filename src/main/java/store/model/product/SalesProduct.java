package store.model.product;

import store.config.constant.Message;
import store.model.policy.SalesPolicy;

public class SalesProduct {

    private final Product product;
    private final SalesType salesType;
    private SalesPolicy policy;
    private int quantity;

    public SalesProduct(Product product, int quantity, SalesPolicy policy, SalesType salesType) {
        this.product = product;
        this.quantity = quantity;
        this.policy = policy;
        this.salesType = salesType;
    }

    public static SalesProduct of(Product product, SalesPolicy policy, SalesType type, String quantityInput) {
        int quantity = Integer.parseInt(quantityInput);
        return new SalesProduct(product, quantity, policy, type);
    }

    public static SalesProduct of(Product product, SalesPolicy policy, String quantityInput) {
        int quantity = Integer.parseInt(quantityInput);
        SalesType type = SalesType.getType(policy);
        return new SalesProduct(product, quantity, policy, type);
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public SalesPolicy getPolicy() {
        return policy;
    }

    public void setPolicy(SalesPolicy policy) {
        this.policy = policy;
    }

    public SalesType getSalesType() {
        return salesType;
    }

    public boolean hasQuantity(int n) {
        return quantity >= n;
    }

    public boolean isNormal() {
        return isType(SalesType.NORMAL) || quantity < policy.getTotalAmount();
    }

    public boolean isType(SalesType type) {
        return salesType.equals(type);
    }

    public boolean isEmpty() {
        return quantity <= 0;
    }

    @Override
    public String toString() {
        if (quantity <= 0) {
            String message = Message.PRODUCT_WITHOUT_QUANTITY.toString();
            return String.format(message, product, policy);
        }
        String message = Message.PRODUCT_WITH_QUANTITY.toString();
        return String.format(message, product, quantity, policy);
    }
}
