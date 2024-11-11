package store.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import store.config.ServiceBean;
import store.config.SystemBeanExtension;
import store.service.product.ProductService;

@ExtendWith(SystemBeanExtension.class)
public class ProductServiceTest {

    private ServiceBean bean;
    private ProductService service;

    public ProductServiceTest() {
        bean = ServiceBean.getInstance();
        service = bean.getProductService();
    }

    @Test
    @DisplayName("데이터를 잘 불러오는지 확인")
    public void 데이터_불러오기_테스트() {
        assertDoesNotThrow(() -> service.loadsProducts());
    }
}
