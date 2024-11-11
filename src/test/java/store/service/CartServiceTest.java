package store.service;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import store.config.RepositoryBean;
import store.config.ServiceBean;
import store.config.SystemBeanExtension;
import store.config.constant.ErrorMessage;
import store.exception.ProductException;
import store.mock.model.manager.FakeSalesProduct;
import store.mock.repository.FakeCartRepository;
import store.mock.repository.FakeSalesPolicyRepository;
import store.mock.repository.FakeSalesProductRepository;
import store.mock.service.FakeCartService;
import store.model.cart.CartItem;
import store.model.product.SalesType;
import store.repository.cart.CartRepository;
import store.repository.policy.SalesPolicyRepository;
import store.repository.product.SalesProductRepository;
import store.service.cart.CartService;
import store.service.product.SalesProductService;

@ExtendWith(SystemBeanExtension.class)
public class CartServiceTest {

    private final RepositoryBean repositoryBean;
    private final ServiceBean serviceBean;
    private final SalesProductService salesProductService;
    private final CartService cartService;
    private final SalesPolicyRepository salesPolicyRepository;
    private final SalesProductRepository salesProductRepository;
    private final CartRepository cartRepository;

    public CartServiceTest() {
        serviceBean = ServiceBean.getInstance();
        repositoryBean = RepositoryBean.getInstance();
        salesPolicyRepository = new FakeSalesPolicyRepository();
        salesProductRepository = new FakeSalesProductRepository();
        salesProductService = new SalesProductService(salesPolicyRepository, salesProductRepository);
        cartRepository = new FakeCartRepository();
        cartService = new FakeCartService(salesProductRepository, cartRepository, salesProductService);
    }

    @Test
    @DisplayName("장바구니 아이템 생성 테스트")
    void createCartItem_ValidInput_ReturnsCartItem() {
        String input = "[티본스테이크-1]";
        CartItem result = cartService.createCartItem(input);
        Assertions.assertThat(result.getName()).isEqualTo("티본스테이크");
        Assertions.assertThat(result.getQuantity()).isEqualTo(1);
    }

    @Test
    @DisplayName("장바구니 생성 테스트")
    void createCart() {
        String input = "[티본스테이크-1],[바비큐립-2]";
        cartService.createCart(input);
        List<CartItem> savedItems = cartRepository.findAll();
        Assertions.assertThat(savedItems).hasSize(2);
        Assertions.assertThat(savedItems.get(0).getName()).isEqualTo("티본스테이크");
        Assertions.assertThat(savedItems.get(1).getName()).isEqualTo("바비큐립");
    }

    @Test
    @DisplayName("유효하지 않은 상품이 있을 경우 예외 발생")
    void checkValidityOfCart_WithInvalidItem() {
        cartRepository.save(new CartItem("없는 메뉴", 1));
        salesProductRepository.save(new FakeSalesProduct(SalesType.PROMOTIONAL));

        Assertions.assertThatThrownBy(cartService::checkValidityOfCart)
                .isInstanceOf(ProductException.class)
                .hasMessage(ErrorMessage.NO_ITEM_FOUND.toString());
    }

    @Test
    @DisplayName("증정품 수량 업데이트 테스트")
    void updateGiveAways() {
        CartItem item = new CartItem("콜라", 2);
        cartService.updateGiveAways(item);
        Assertions.assertThat(item.getQuantity()).isEqualTo(3);
    }

    // 타임머신?

    @Test
    @DisplayName("증정품 수량 업데이트 반례 테스트")
    void updateNoGiveAways() {
        CartItem item = new CartItem("콜라", 1);
        cartService.updateGiveAways(item);
        Assertions.assertThat(item.getQuantity()).isEqualTo(1);
    }
}
