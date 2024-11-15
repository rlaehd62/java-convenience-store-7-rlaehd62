package store.config;

import store.service.cart.CartService;
import store.service.order.OrderService;
import store.service.payment.PaymentService;
import store.service.policy.SalesPolicyService;
import store.service.product.ProductService;
import store.service.product.SalesProductService;

public class ServiceBean {
    private static ServiceBean instance = null;
    private final RepositoryBean bean;
    private final ProductService productService;
    private final SalesPolicyService salesPolicyService;
    private final SalesProductService salesProductService;
    private final CartService cartService;
    private final OrderService orderService;
    private final PaymentService paymentService;

    private ServiceBean() {
        bean = RepositoryBean.getInstance();
        salesPolicyService = new SalesPolicyService(bean.getSalesPolicyRepository());
        productService = new ProductService(bean.getProductRepository(), bean.getSalesProductRepository(),
                bean.getSalesPolicyRepository());
        salesProductService = new SalesProductService(bean.getSalesPolicyRepository(),
                bean.getSalesProductRepository());
        paymentService = new PaymentService(bean.getOrderRepository(), bean.getReceiptRepository());
        orderService = new OrderService(bean.getOrderRepository(), bean.getReceiptRepository(),
                bean.getCartRepository(), bean.getSalesProductRepository(), salesProductService);
        cartService = new CartService(bean.getSalesProductRepository(), bean.getCartRepository(), salesProductService);
        init();
    }

    public static ServiceBean getInstance() {
        if (instance == null) {
            instance = new ServiceBean();
        }
        return instance;
    }

    public void clear() {
        instance = null;
    }

    private void init() {
        productService.loadsProducts();
        salesPolicyService.loadPolicies();
        salesProductService.loadSalesProducts();
    }

    public ProductService getProductService() {
        return productService;
    }

    public SalesProductService getSalesProductService() {
        return salesProductService;
    }

    public CartService getCartService() {
        return cartService;
    }

    public OrderService getOrderService() {
        return orderService;
    }

    public SalesPolicyService getSalesPolicyService() {
        return salesPolicyService;
    }

    public PaymentService getPaymentService() {
        return paymentService;
    }
}
