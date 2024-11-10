package store.model.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import store.model.order.OrderItem;

public class Inventory {
    private final Map<Integer, NormallItem> normalProducts;
    private final Map<Integer, PromotionalItem> promotionalProducts;

    public Inventory() {
        normalProducts = new HashMap<>();
        promotionalProducts = new HashMap<>();
    }

    private Integer generateID() {
        return normalProducts.size() + promotionalProducts.size();
    }

    public void addNormalItem(NormallItem item) {
        normalProducts.put(generateID(), item);
    }

    public void addPromotionalItem(PromotionalItem item) {
        promotionalProducts.put(generateID(), item);
    }

    public void takeOrder(OrderItem orderItem) {
        if (orderItem.isPromotional()) {
            takePromotionalOrder(orderItem);
            return;
        }

        takeNormalOrder(orderItem);
    }

    private void takePromotionalOrder(OrderItem orderItem) {
        findPromotionalItem(orderItem.getName()).ifPresent(
                promotionalItem -> promotionalItem.decreaseQuantity(orderItem.getTotalQuantity()));
    }

    private void takeNormalOrder(OrderItem orderItem) {
        findNormalItem(orderItem.getName()).ifPresent(
                normalItem -> normalItem.decreaseQuantity(orderItem.getTotalQuantity()));

    }

    public boolean hasEnoughQuantity(String name, int quantity) {
        return getTotalQuantity(name) >= quantity;
    }

    public int getTotalQuantity(String name) {
        Optional<NormallItem> optionalNormallItem = findNormalItem(name);
        Optional<PromotionalItem> optionalPromotionalItem = findPromotionalItem(name);
        AtomicInteger totalQuantity = new AtomicInteger(0);
        optionalNormallItem.ifPresent(item -> totalQuantity.addAndGet(item.getQuantity()));
        optionalPromotionalItem.ifPresent(item -> totalQuantity.addAndGet(item.getQuantity()));
        return totalQuantity.get();
    }

    public Optional<Price> getPromotionalPrice(String name) {
        return promotionalProducts.values().stream()
                .filter(promotionalItem -> promotionalItem.equals(name))
                .map(PromotionalItem::getProduct)
                .map(PromotionalProduct::getProduct)
                .map(NormalProduct::getPrice)
                .findFirst();
    }

    public Optional<Price> getNormalPrice(String name) {
        return normalProducts.values().stream()
                .filter(promotionalItem -> promotionalItem.equals(name))
                .map(NormallItem::getItem)
                .map(NormalProduct::getPrice)
                .findFirst();
    }

    public boolean hasItem(String name) {
        Optional<NormallItem> optionalNormallItem = findNormalItem(name);
        Optional<PromotionalItem> optionalPromotionalItem = findPromotionalItem(name);
        return optionalNormallItem.isPresent() || optionalPromotionalItem.isPresent();
    }


    public Optional<NormallItem> findNormalItem(String name) {
        Optional<Integer> optionalKey = indexOfNormalItem(name);
        if (optionalKey.isPresent()) {
            int key = optionalKey.get();
            return findNormalItem(key);
        }
        return Optional.empty();
    }

    public Optional<PromotionalItem> findPromotionalItem(String name) {
        Optional<Integer> optionalKey = indexOfPromotionalItem(name);
        if (optionalKey.isPresent()) {
            int key = optionalKey.get();
            return findPromotionalItem(key);
        }
        return Optional.empty();
    }

    public Optional<NormallItem> findNormalItem(int key) {
        if (!normalProducts.containsKey(key)) {
            return Optional.empty();
        }
        return Optional.of(normalProducts.get(key));
    }

    public Optional<PromotionalItem> findPromotionalItem(int key) {
        if (!promotionalProducts.containsKey(key)) {
            return Optional.empty();
        }
        return Optional.of(promotionalProducts.get(key));
    }

    public Optional<Integer> indexOfNormalItem(String name) {
        return normalProducts.entrySet()
                .stream()
                .filter(entry -> entry.getValue().equals(name))
                .map(Map.Entry::getKey)
                .findFirst();
    }

    public Optional<Integer> indexOfPromotionalItem(String name) {
        return promotionalProducts.entrySet()
                .stream()
                .filter(entry -> entry.getValue().equals(name))
                .map(Map.Entry::getKey)
                .findFirst();
    }

    public List<Object> getDataLabels() {
        List<Object> objects = new ArrayList<>();
        for (int i = 0; i < generateID(); i++) {
            Optional<PromotionalItem> op1 = findPromotionalItem(i);
            if (op1.isPresent()) {
                objects.add(op1.get());
                continue;
            }
            objects.add(findNormalItem(i).get());
        }
        return objects;
    }
}

// TODO: 끝까지 돌려보기 (케이스 섞어서)
// TODO: 책임 분리 잘 하기 (Factory, Repository)