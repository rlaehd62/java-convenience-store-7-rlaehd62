package store.exception;

import store.config.constant.ErrorMessage;

public class OrderException extends IllegalArgumentException {
    public OrderException(ErrorMessage errorMessage) {
        super(errorMessage.toString());
    }
}
