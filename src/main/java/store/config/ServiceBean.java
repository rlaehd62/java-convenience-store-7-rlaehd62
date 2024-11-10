package store.config;

import store.service.CartService;
import store.service.OrderService;
import store.service.PaymentService;
import store.test.service.ProductService;
import store.test.service.SalesPolicyService;
import store.test.service.SalesProductService;

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
        productService = new ProductService(bean.getProductRepository());
        salesPolicyService = new SalesPolicyService(bean.getSalesPolicyRepository());
        salesProductService = new SalesProductService(bean.getSalesPolicyRepository(), bean.getProductRepository(),
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

    public SalesProductService getSalesProductService() {
        return salesProductService;
    }

    public CartService getCartService() {
        return cartService;
    }

    public OrderService getOrderService() {
        return orderService;
    }

    public PaymentService getPaymentService() {
        return paymentService;
    }
}
