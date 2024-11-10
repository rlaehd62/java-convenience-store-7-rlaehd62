package store.model.promotion;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import store.utility.PromotionBuilder;

public class Promotion {
    private static final DateTimeFormatter formatter;

    static {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }

    private final String name;
    private final int buy;
    private final int get;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Promotion(String name, int buy, int get, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static PromotionBuilder builder() {
        return new PromotionBuilder();
    }

    public static Promotion of(List<String> elements) {
        String name = elements.get(0);
        int buy = Integer.parseInt(elements.get(1));
        int get = Integer.parseInt(elements.get(2));
        LocalDate startDate = LocalDate.parse(elements.get(3), formatter);
        LocalDate endDate = LocalDate.parse(elements.get(4), formatter);
        return builder()
                .name(name)
                .buy(buy).get(get)
                .startDate(startDate).endDate(endDate)
                .build();
    }

    public boolean isAvailable() {
        LocalDate curr = DateTimes.now().toLocalDate();
        return curr.isAfter(startDate) && curr.isBefore(endDate);
    }

    public String getName() {
        return name;
    }

    public int getBuy() {
        return buy;
    }

    public int getGet() {
        return get;
    }

    @Override
    public String toString() {
        return getName();
    }
}
