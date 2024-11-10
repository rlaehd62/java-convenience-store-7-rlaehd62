package store.exception;

import store.config.constant.ErrorMessage;

public class PromotionException extends IllegalArgumentException {
    public PromotionException(ErrorMessage errorMessage) {
        super(errorMessage.toString());
    }
}
