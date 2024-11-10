package store.exception;

import store.config.constant.ErrorMessage;

public class ValidationException extends IllegalArgumentException {
    public ValidationException(ErrorMessage errorMessage) {
        super(errorMessage.toString());
    }
}
