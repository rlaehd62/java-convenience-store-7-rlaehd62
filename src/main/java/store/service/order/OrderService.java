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

    private void takePromotionalsFirst(Order order) {
        try {
            SalesProduct salesProduct = order.getSalesProduct();
            Product product = salesProduct.getProduct();
            String name = product.getName();
            SalesProduct promotional = salesProductRepository.findSalesProductsByName(name, SalesType.PROMOTIONAL);
            if (!salesProduct.isType(SalesType.PROMOTIONAL) && promotional.isNormal()) {
                int quantity = order.getTotalQuantity();
                int remainder = promotional.getQuantity();
                promotional.setQuantity(0);
                order.setAmountOfBuy(quantity - remainder);
            }
        } catch (Exception e) {
        }
    }

//    private void processTransaction(Order order) {
//        takePromotionalsFirst(order);
//        SalesProduct salesProduct = order.getSalesProduct();
//        int remainingQuantity = order.getTotalQuantity();
//        if (!order.isSatisfied() && !salesProduct.hasQuantity(remainingQuantity)) {
//            throw new ProductException(ErrorMessage.QUANTITY_OVERFLOW);
//        }
//        int currentQuantity = salesProduct.getQuantity();
//        salesProduct.setQuantity(currentQuantity - remainingQuantity);
//    }

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

    private void runNormalTransaction(Order order) {
        AtomicInteger totalQuantity = new AtomicInteger(order.getTotalQuantity());
        Product product = order.getSalesProduct().getProduct();
        product.forEach(salesProduct -> {
            int decrease = Math.min(salesProduct.getQuantity(), totalQuantity.get());
            salesProduct.setQuantity(salesProduct.getQuantity() - decrease);
        });
    }

    // Consumer말고 있음 (CallBack Interface 사용하기)

    private void processNormal(Order order) {
        validateNormal(order);
        runNormalTransaction(order);
    }

    private void processTransaction(Order order) {
        SalesProduct salesProduct = order.getSalesProduct();
        if (salesProduct.isType(SalesType.PROMOTIONAL)) {
            runPromotionTransaction(order);
            return;
        }
        processNormal(order);
    }

    public void runTransaction() {
        List<Order> orders = receiptRepository.findAll();
        orders.forEach(this::processTransaction);
    }

    public void clear() {
        cartRepository.clear();
        orderRepository.clear();
        receiptRepository.clear();
        receiptRepository.setMembership(false);
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

    public void makeOrders() {
        List<CartItem> cartItems = cartRepository.findAll();
        cartItems.forEach(this::classify);
    }
}
