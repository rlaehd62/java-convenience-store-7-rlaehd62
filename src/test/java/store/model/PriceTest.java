package store.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import store.model.product.Price;

public class PriceTest {
    @Test
    public void 계산_확인() {
        Price origin = new Price(1000);
        Price factor = new Price(21);
        Assertions.assertThat(origin.sum(factor))
                .isEqualTo(1021);
    }

    @Test
    public void 형식_확인() {
        Price origin = new Price(13000);
        Assertions.assertThat(origin.toString())
                .contains("13,000");
    }
}
