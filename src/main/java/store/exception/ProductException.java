package store.exception;

import store.config.constant.ErrorMessage;

public class ProductException extends IllegalArgumentException {
    public ProductException(ErrorMessage errorMessage) {
        super(errorMessage.toString());
    }
}
