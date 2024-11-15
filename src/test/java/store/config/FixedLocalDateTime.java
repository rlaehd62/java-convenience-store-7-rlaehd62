package store.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Test
@ExtendWith(FixedLocalDateTimeExtension.class)
public @interface FixedLocalDateTime {
}