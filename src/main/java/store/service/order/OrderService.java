package store.service.order;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import store.config.constant.ErrorMessage;
import store.exception.ProductException;
import store.model.cart.CartItem;
import store.model.order.Order;
import store.model.product.Product;
import store.model.product.SalesProduct;
import store.model.product.SalesType;
import store.repository.cart.CartRepository;
import store.repository.order.OrderRepository;
import store.repository.payment.ReceiptRepository;
import store.repository.product.SalesProductRepository;
import store.service.product.SalesProductService;

public class OrderService {
    private final OrderRepository orderRepository;
    private final ReceiptRepository receiptRepository;
    private final CartRepository cartRepository;
    private final SalesProductRepository salesProductRepository;
    private final SalesProductService salesProductService;

    public OrderService(OrderRepository orderRepository, ReceiptRepository receiptRepository,
                        CartRepository cartRepository,
                        SalesProductRepository salesProductRepository, SalesProductService salesProductService) {
        this.orderRepository = orderRepository;
        this.receiptRepository = receiptRepository;
        this.cartRepository = cartRepository;
        this.salesProductRepository = salesProductRepository;
        this.salesProductService = salesProductService;
    }

    public void makeOrders() {
        List<CartItem> cartItems = cartRepository.findAll();
        cartItems.forEach(this::classify);
    }

    public void makeOrder(CartItem cartItem, int quantity, SalesType type) {
        String name = cartItem.getName();
        SalesProduct salesProduct = salesProductRepository.findSalesProductsByName(name, type);
        orderRepository.save(new Order(salesProduct, quantity));
    }

    public void classify(CartItem cartItem) {
        int expectedAsPromotion = classifyAsPromotion(cartItem);
        int remainder = cartItem.getQuantity() - expectedAsPromotion;
        if (remainder > 0) {
            makeOrder(cartItem, cartItem.getQuantity() - expectedAsPromotion, SalesType.NORMAL);
        }
    }

    public int classifyAsPromotion(CartItem item) {
        try {
            String name = item.getName();
            SalesProduct salesProduct = salesProductRepository.findSalesProductsByName(name, SalesType.PROMOTIONAL);
            int expected = salesProductService.getPossibleAmount(salesProduct, item.getQuantity());
            if (expected > 0) {
                makeOrder(item, expected, SalesType.PROMOTIONAL);
                return expected;
            }
        } catch (Exception e) {
            return 0;
        }
        return 0;
    }

    private void runPromotionTransaction(Order order) {
        SalesProduct salesProduct = order.getSalesProduct();
        int totalQuantity = order.getTotalQuantity();
        int currentQuantity = salesProduct.getQuantity();
        if (totalQuantity > currentQuantity) {
            throw new ProductException(ErrorMessage.QUANTITY_OVERFLOW);
        }
        salesProduct.setQuantity(currentQuantity - totalQuantity);
    }

    private void validateNormal(Order order) {
        AtomicInteger totalQuantity = new AtomicInteger(order.getTotalQuantity());
        AtomicInteger totalDecrease = new AtomicInteger(0);
        Product product = order.getSalesProduct().getProduct();
        product.forEach(salesProduct -> {
            int decrease = Math.min(salesProduct.getQuantity(), totalQuantity.get());
            totalDecrease.getAndAdd(decrease);
        });
        if ((totalQuantity.get()) > (totalDecrease.get())) {
            throw new ProductException(ErrorMessage.QUANTITY_OVERFLOW);
        }
    }

    public void runTransaction() {
        List<Order> orders = receiptRepository.findAll();
        orders.forEach(this::processTransaction);
    }

    private void processTransaction(Order order) {
        SalesProduct salesProduct = order.getSalesProduct();
        if (salesProduct.isType(SalesType.PROMOTIONAL)) {
            runPromotionTransaction(order);
            return;
        }
        processNormal(order);
    }

    private void runNormalTransaction(Order order) {
        AtomicInteger totalQuantity = new AtomicInteger(order.getTotalQuantity());
        Product product = order.getSalesProduct().getProduct();
        product.forEach(salesProduct -> {
            int decrease = Math.min(salesProduct.getQuantity(), totalQuantity.get());
            salesProduct.setQuantity(salesProduct.getQuantity() - decrease);
        });
    }

    private void processNormal(Order order) {
        validateNormal(order);
        runNormalTransaction(order);
    }

    public void clear() {
        cartRepository.clear();
        orderRepository.clear();
        receiptRepository.clear();
        receiptRepository.setMembership(false);
    }
}
