package store.model.order;

public class OrderItem {
    private final String name;
    private int buy;
    private int get;
    private boolean isPromotional;

    public OrderItem(OrderItem orderItem) {
        this.name = orderItem.name;
        this.buy = orderItem.buy;
        this.get = orderItem.get;
        this.isPromotional = orderItem.isPromotional;
    }

    public OrderItem(String name, int buy) {
        this.name = name;
        this.buy = buy;
        this.get = 0;
        this.isPromotional = false;
    }

    public boolean isEmpty() {
        return buy <= 0;
    }

    public String getName() {
        return name;
    }

    public int getBuy() {
        return buy;
    }

    public void setBuy(int buy) {
        this.buy = buy;
    }

    public int getGet() {
        return get;
    }

    public void setGet(int get) {
        this.get = get;
    }

    public int getTotalQuantity() {
        return getBuy() + getGet();
    }

    public boolean isPromotional() {
        return isPromotional;
    }

    public void setPromotional(boolean promotional) {
        isPromotional = promotional;
    }

    @Override
    public boolean equals(Object obj) {
        return name.equals(obj);
    }
}