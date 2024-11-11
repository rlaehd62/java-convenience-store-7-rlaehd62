package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.config.constant.ErrorMessage;
import store.config.constant.Message;
import store.config.constant.Regex;
import store.utility.FlowHandler;
import store.utility.Validator;

public class InputView {
    public static String readOrder() {
        System.out.println(Message.PURCHASE_ITEM);
        String input = Console.readLine();
        System.out.println();
        return input;
    }

    public static boolean readForYN(Message message, String... arguments) {
        return FlowHandler.runWithReturnWithBoolean(() -> {
            System.out.printf(message.getConsoleMessage(), arguments);
            System.out.println();
            String yn = Console.readLine();
            System.out.println();
            Validator.validate(yn, Regex.YES_OR_NO, ErrorMessage.YN_FAILURE);
            return yn.equals("Y");
        }, e -> OutputView.of(e.getMessage(), true));
    }

    public static String readForGiveAways(String name) {
        System.out.println();
        System.out.printf(Message.MORE_QUANTITY.toString(), name);
        System.out.println();
        return Console.readLine();
    }

    public static String readForRetry() {
        System.out.println();
        System.out.println(Message.CONTINUE.getConsoleMessage());
        return Console.readLine();
    }
}
