package store.utility;

import java.time.LocalDate;
import store.model.promotion.Promotion;

public class PromotionBuilder {
    private String name;
    private int buy;
    private int get;

    private LocalDate startDate;
    private LocalDate endDate;

    public PromotionBuilder() {

    }

    public PromotionBuilder name(String name) {
        this.name = name;
        return this;
    }

    public PromotionBuilder buy(int buy) {
        this.buy = buy;
        return this;
    }

    public PromotionBuilder get(int get) {
        this.get = get;
        return this;
    }

    public PromotionBuilder startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public PromotionBuilder endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public Promotion build() {
        return new Promotion(name, buy, get, startDate, endDate);
    }
}
