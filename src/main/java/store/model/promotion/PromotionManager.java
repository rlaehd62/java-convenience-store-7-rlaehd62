package store.model.promotion;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;

public class PromotionManager {

    private final Map<String, Promotion> promotions;

    public PromotionManager() {
        promotions = new HashMap<>();
    }

    public void addPromotion(Promotion promotion) {
        promotions.put(promotion.getName(), promotion);
    }

    public void forEach(BiConsumer<String, Promotion> biConsumer) {
        promotions.forEach(biConsumer);
    }

    public Optional<Promotion> getPromotion(String name) {
        if (!promotions.containsKey(name)) {
            return Optional.empty();
        }
        return Optional.of(promotions.get(name));
    }
}
