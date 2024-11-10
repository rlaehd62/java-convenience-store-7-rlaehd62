package store.utility;

import store.config.constant.ErrorMessage;
import store.config.constant.Regex;
import store.exception.ValidationException;

public class Validator {

    public static void validate(String input, Regex regex, ErrorMessage message) {
        String expression = regex.getExpression();
        if (!input.matches(expression)) {
            throw new ValidationException(message);
        }
    }

}
