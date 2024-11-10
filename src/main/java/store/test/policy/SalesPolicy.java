package store.test.policy;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import store.config.system.SystemConfig;

public class SalesPolicy {

    private static final DateTimeFormatter formatter;

    static {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }

    private final String name;
    private final int amountOfBuy;
    private final int amountOfGet;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public SalesPolicy(String name, int amountOfBuy, int amountOfGet, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.amountOfBuy = amountOfBuy;
        this.amountOfGet = amountOfGet;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static SalesPolicy of(List<String> elements) {
        String name = elements.get(0);
        int buy = Integer.parseInt(elements.get(1));
        int get = Integer.parseInt(elements.get(2));
        LocalDate startDate = java.time.LocalDate.parse(elements.get(3), formatter);
        LocalDate endDate = java.time.LocalDate.parse(elements.get(4), formatter);
        return new SalesPolicy(name, buy, get, startDate, endDate);
    }

    public String getName() {
        return name;
    }

    public int getAmountOfBuy() {
        return amountOfBuy;
    }

    public int getAmountOfGet() {
        return amountOfGet;
    }

    public int getTotalAmount() {
        return getAmountOfBuy() + getAmountOfGet();
    }

    public boolean isAvailable() {
        LocalDate now = LocalDate.from(DateTimes.now());
        return startDate.isBefore(now) && endDate.isAfter(now);
    }

    public boolean isNULL() {
        return name.equals(SystemConfig.NULL.getValue());
    }

    public int extractAmountOfGet(int n) {
        return n / (getTotalAmount());
    }

    @Override
    public String toString() {
        return name;
    }
}
