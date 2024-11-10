package store.model.product;

import store.config.constant.Message;

public class PromotionalItem {

    private final PromotionalProduct product;
    private int quantity;

    public PromotionalItem(PromotionalProduct product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public PromotionalProduct getProduct() {
        return product;
    }

    public void decreaseQuantity(int n) {
        quantity -= n;
    }

    public boolean isEmpty() {
        return quantity <= 0;
    }

    public boolean hasQuantity(int n) {
        return (quantity) >= n;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object obj) {
        String name = product.getProduct().getName();
        return name.equals(obj);
    }

    @Override
    public String toString() {
        if (quantity == 0) {
            return String.format(String.valueOf(Message.PROMOTIONAL_ITEM), product, Message.OUT_OF_STOCK,
                    getProduct().getPromotion());
        }
        String quantityMessage = String.format(Message.QUANTITY.toString(), quantity);
        return String.format(String.valueOf(Message.PROMOTIONAL_ITEM), product, quantityMessage,
                getProduct().getPromotion());
    }
}
