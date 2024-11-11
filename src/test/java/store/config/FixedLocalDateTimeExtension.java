package store.config;

import java.time.LocalDateTime;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import store.utility.DateTimeUtil;

public class FixedLocalDateTimeExtension implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        DateTimeUtil.getInstance().fixedMode(LocalDateTime.of(2024, 11, 30, 0, 0));
    }
}
