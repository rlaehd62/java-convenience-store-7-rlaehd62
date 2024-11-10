package store.service;

import java.util.List;
import store.config.constant.ErrorMessage;
import store.exception.ProductException;
import store.model.cart.CartItem;
import store.repository.CartRepository;
import store.repository.OrderRepository;
import store.test.order.Order;
import store.test.product.Product;
import store.test.product.SalesProduct;
import store.test.product.SalesType;
import store.test.repository.ReceiptRepository;
import store.test.repository.SalesProductRepository;
import store.test.service.SalesProductService;

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
            if (salesProduct.isType(SalesType.NORMAL) && promotional.isNormal()) {
                int quantity = order.getTotalQuantity();
                int remainder = promotional.getQuantity();
                promotional.setQuantity(0);
                order.setAmountOfBuy(quantity - remainder);
            }

        } catch (Exception e) {
            // Do Nothing ..
        }
    }

    private void processTransaction(Order order) {
        takePromotionalsFirst(order);
        SalesProduct salesProduct = order.getSalesProduct();
        if (!salesProduct.hasQuantity(order.getTotalQuantity())) {
            throw new ProductException(ErrorMessage.QUANTITY_OVERFLOW);
        }
        int requiredQuantity = order.getTotalQuantity();
        int currentQuantity = salesProduct.getQuantity();
        salesProduct.setQuantity(currentQuantity - requiredQuantity);
    }

    public void runTransaction() {
        List<Order> orders = receiptRepository.findAll();
        orders.forEach(this::processTransaction);
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
