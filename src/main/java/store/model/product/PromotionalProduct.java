package store.model.product;

import java.util.List;
import store.model.promotion.Promotion;

public class PromotionalProduct {

    private final NormalProduct product;
    private final Promotion promotion;

    public PromotionalProduct(NormalProduct product, Promotion promotion) {
        this.product = product;
        this.promotion = promotion;
    }

    public static PromotionalProduct of(Promotion promotion, List<String> elements) {
        NormalProduct product = NormalProduct.of(elements);
        return new PromotionalProduct(product, promotion);
    }

    public NormalProduct getProduct() {
        return product;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    @Override
    public String toString() {
        return String.format("%s", getProduct());
    }
}
