package store.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.mock.model.manager.FakeSalesProduct;
import store.model.product.Price;
import store.model.product.Product;
import store.model.product.SalesProduct;
import store.model.product.SalesType;

public class SalesProductTest {

    @Test
    @DisplayName("금액이 잘 계산되는지 확인한다.")
    public void 금액_계산_확인() {
        SalesProduct salesProduct = new FakeSalesProduct(SalesType.PROMOTIONAL);
        Product product = salesProduct.getProduct();
        Price price = product.getPrice();
        Assertions.assertThat(price.calculate(salesProduct.getQuantity()))
                .isEqualTo(10000);
    }

    @Test
    @DisplayName("프로모션 재고의 일반 재고화 조건을 확인한다.")
    public void 프로모션_특수조건_확인() {
        SalesProduct salesProduct = new FakeSalesProduct(SalesType.PROMOTIONAL);
        salesProduct.setQuantity(salesProduct.getPolicy().getAmountOfBuy() - 1);
        Assertions.assertThat(salesProduct.isNormal())
                .isEqualTo(true);

        salesProduct.setQuantity(salesProduct.getPolicy().getTotalAmount());
        Assertions.assertThat(salesProduct.isNormal())
                .isEqualTo(false);
    }

    @Test
    @DisplayName("프로모션 재고의 일반 재고화 조건의 반례를 확인한다.")
    public void 프로모션_특수조건_반레() {
        SalesProduct salesProduct = new FakeSalesProduct(SalesType.PROMOTIONAL);
        salesProduct.setQuantity(salesProduct.getPolicy().getTotalAmount());
        Assertions.assertThat(salesProduct.isNormal())
                .isEqualTo(false);
    }
}
