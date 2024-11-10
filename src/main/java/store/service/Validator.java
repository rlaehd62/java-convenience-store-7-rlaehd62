package store.service;

import store.config.constant.ErrorMessage;
import store.config.constant.Regex;
import store.exception.FileInputException;
import store.exception.OrderException;

public class Validator {

    private boolean validate(String input, Regex regex) {
        final String expression = regex.getExpression();
        return input.matches(expression);
    }

    public void isListFormatted(String input) {
        if (!validate(input, Regex.LIST_FORMAT)) {
            throw new FileInputException(ErrorMessage.FILE_FORMAT_INVALID);
        }
    }

    public boolean isValidOrder(String input) {
        if (!validate(input, Regex.PURCHASE_SINGLE)) {
            throw new OrderException(ErrorMessage.ETC_INVALID_INPUT);
        }

        return true;
    }

    public boolean isYesOrNo(String input) {
        if (!validate(input, Regex.YES_OR_NO)) {
            throw new OrderException(ErrorMessage.ETC_INVALID_INPUT);
        }
        return input.equals("Y") || input.equals("y");
    }
}
