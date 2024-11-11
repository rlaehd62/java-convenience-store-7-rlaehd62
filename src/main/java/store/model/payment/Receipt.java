package store.model.payment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;
import store.model.order.Order;
import store.model.product.Price;
import store.model.product.Product;
import store.model.product.SalesProduct;
import store.model.product.SalesType;

public class Receipt {
    private List<Order> purchaseHistory;
    private boolean isMembershipDiscounted;

    public Receipt() {
        this.purchaseHistory = new ArrayList<>();
    }

    public void addOrder(Order order) {
        purchaseHistory.add(order);
    }

    public void removeOrder(Order order) {
        purchaseHistory.remove(order);
    }

    public boolean isMembershipDiscounted() {
        return isMembershipDiscounted;
    }

    public void setMembershipDiscounted(boolean membershipDiscounted) {
        isMembershipDiscounted = membershipDiscounted;
    }

    public List<Order> getPurchaseHistory() {
        return purchaseHistory;
    }

    public int getTotalQuantity() {
        return stream()
                .map(Order::getTotalQuantity)
                .reduce(0, Integer::sum);
    }

    public Map<String, History> getTotalHistory() {
        Map<String, History> history = new HashMap<>();
        forEach(order -> {
            SalesProduct salesProduct = order.getSalesProduct();
            Product produt = salesProduct.getProduct();
            String name = produt.getName();
            History item = history.getOrDefault(name, new History(name));
            item.add(order);
            history.put(name, item);
        });
        return history;
    }

    public Price getTotalPolicyDiscount(SalesType type, Function<Order, Integer> quantityMapper) {
        int totalPrice = purchaseHistory.stream()
                .filter(order -> order.getSalesProduct().isType(type))
                .mapToInt(order -> order.getSalesProduct().getProduct().getPrice()
                        .calculate(quantityMapper.apply(order)))
                .sum();
        return new Price(-totalPrice);
    }

    public Price getTotalPrice() {
        int totalPrice = 0;
        for (Order order : purchaseHistory) {
            SalesProduct salesProduct = order.getSalesProduct();
            Product product = salesProduct.getProduct();
            Price price = product.getPrice();
            totalPrice += price.calculate(order.getTotalQuantity());
        }
        return new Price(totalPrice);
    }

    public Price getMembershipDiscount() {
        if (!isMembershipDiscounted) {
            return new Price(0);
        }
        Price totalPolicyDiscount = getTotalPolicyDiscount(SalesType.NORMAL, Order::getTotalQuantity);
        int membershipDiscount = (int) (totalPolicyDiscount.money() * 0.3d);
        return new Price(Math.max(-8000, membershipDiscount));
    }

    public Price getFinalPrice() {
        Price total = getTotalPrice();
        Price promo = getTotalPolicyDiscount(SalesType.PROMOTIONAL, Order::getAmountOfGet);
        Price membership = getMembershipDiscount();
        int finalPrice = total.sum(promo, membership);
        return new Price(finalPrice);
    }

    public void clear() {
        purchaseHistory.clear();
    }

    public Stream<Order> stream() {
        return purchaseHistory.stream();
    }

    public void forEach(Consumer<Order> consumer) {
        purchaseHistory.forEach(consumer);
    }
}
