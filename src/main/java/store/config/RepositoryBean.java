package store.config;

import store.repository.cart.CartRepository;
import store.repository.order.OrderRepository;
import store.repository.payment.ReceiptRepository;
import store.repository.policy.SalesPolicyRepository;
import store.repository.product.ProductRepository;
import store.repository.product.SalesProductRepository;

public class RepositoryBean {
    private static RepositoryBean instance = null;
    private final ProductRepository productRepository;
    private final SalesPolicyRepository salesPolicyRepository;
    private final SalesProductRepository salesProductRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final ReceiptRepository receiptRepository;

    private RepositoryBean() {
        productRepository = new ProductRepository();
        salesPolicyRepository = new SalesPolicyRepository();
        salesProductRepository = new SalesProductRepository();
        cartRepository = new CartRepository();
        orderRepository = new OrderRepository();
        receiptRepository = new ReceiptRepository();
    }

    public static RepositoryBean getInstance() {
        if (instance == null) {
            instance = new RepositoryBean();
        }
        return instance;
    }

    public void clear() {
        instance = null;
    }

    public ProductRepository getProductRepository() {
        return productRepository;
    }

    public SalesPolicyRepository getSalesPolicyRepository() {
        return salesPolicyRepository;
    }

    public SalesProductRepository getSalesProductRepository() {
        return salesProductRepository;
    }

    public CartRepository getCartRepository() {
        return cartRepository;
    }

    public OrderRepository getOrderRepository() {
        return orderRepository;
    }

    public ReceiptRepository getReceiptRepository() {
        return receiptRepository;
    }
}
