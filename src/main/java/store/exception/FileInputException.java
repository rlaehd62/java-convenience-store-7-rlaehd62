package store.exception;

import store.config.constant.ErrorMessage;

public class FileInputException extends IllegalArgumentException {
    public FileInputException(ErrorMessage errorMessage) {
        super(errorMessage.toString());
    }
}
