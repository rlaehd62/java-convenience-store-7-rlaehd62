package store.model.product;

import java.util.List;
import store.config.constant.Message;

public class NormallItem {

    private final NormalProduct item;
    private int quantity;

    public NormallItem(NormalProduct item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public static NormallItem of(List<String> elements) {
        int quantity = Integer.parseInt(elements.get(2));
        NormalProduct product = NormalProduct.of(elements);
        return new NormallItem(product, quantity);
    }

    public NormalProduct getItem() {
        return item;
    }

    public void decreaseQuantity(int n) {
        quantity -= n;
    }

    public boolean hasQuantity(int n) {
        return quantity >= n;
    }

    public boolean isEmpty() {
        return quantity <= 0;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object obj) {
        String name = item.getName();
        return name.equals(obj);
    }

    @Override
    public String toString() {
        String quantityMessage = switch (quantity) {
            case 0 -> String.valueOf(Message.OUT_OF_STOCK);
            default -> quantity + "개";
        };
        return String.format(Message.ITEM.toString(), item, quantityMessage);
    }
}
