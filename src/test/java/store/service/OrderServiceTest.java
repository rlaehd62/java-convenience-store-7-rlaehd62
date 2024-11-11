package store.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import store.config.RepositoryBean;
import store.config.ServiceBean;
import store.config.SystemBeanExtension;
import store.exception.ProductException;
import store.mock.model.manager.FakeSalesProduct;
import store.model.order.Order;
import store.model.product.SalesProduct;
import store.model.product.SalesType;
import store.repository.order.OrderRepository;
import store.repository.payment.ReceiptRepository;
import store.service.order.OrderService;

@ExtendWith(SystemBeanExtension.class)
public class OrderServiceTest {
    private final RepositoryBean repositoryBean;
    private final ServiceBean serviceBean;
    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final ReceiptRepository receiptRepository;

    public OrderServiceTest() {
        serviceBean = ServiceBean.getInstance();
        repositoryBean = RepositoryBean.getInstance();
        orderService = serviceBean.getOrderService();
        orderRepository = repositoryBean.getOrderRepository();
        receiptRepository = repositoryBean.getReceiptRepository();
    }

    @Test
    @DisplayName("프로모션 상품 주문 처리 테스트")
    void processPromotionalOrder() {
        // given
        SalesProduct promotional = new FakeSalesProduct(SalesType.PROMOTIONAL);
        promotional.setQuantity(1);

        Order order = new Order(promotional, 2);
        receiptRepository.save(order);
        orderRepository.save(order);

        Assertions.assertThatThrownBy(orderService::runTransaction)
                .isInstanceOf(ProductException.class);

    }
}
