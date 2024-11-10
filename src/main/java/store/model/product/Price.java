package store.model.product;

public record Price(int money) {

    public int calculate(int n) {
        return money() * n;
    }

    public int sum(Price... prices) {
        int total = money;
        for (Price price : prices) {
            total += price.money;
        }
        return total;
    }

    @Override
    public String toString() {
        return String.format("%,d원", money);
    }
}
