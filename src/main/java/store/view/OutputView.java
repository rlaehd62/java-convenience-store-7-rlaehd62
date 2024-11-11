package store.view;

import static store.config.constant.Message.CONVENIENCE_INTRO;
import static store.config.constant.Message.GREETING;
import static store.config.constant.Message.RECEIPT_GIVEAWAYS_VALUE;

import java.util.List;
import java.util.Map;
import store.config.constant.ErrorMessage;
import store.config.constant.Message;
import store.model.order.Order;
import store.model.payment.History;
import store.model.payment.Receipt;
import store.model.product.Product;
import store.model.product.SalesProduct;
import store.model.product.SalesType;

public class OutputView {

    public static void of(String message, boolean isSpaced) {
        System.out.println(message);
        if (isSpaced) {
            System.out.println();
        }
    }

    public static void of(ErrorMessage errorMessage, boolean isSpaced) {
        System.out.println(errorMessage.toString());
        if (isSpaced) {
            System.out.println();
        }
    }

    public static void of(String message) {
        System.out.println(message);
    }


    public static void printGreeting() {
        System.out.println(GREETING);
        System.out.println(CONVENIENCE_INTRO);
    }

    public static void printReceipt(Receipt receipt) {
        printHistoryOfPurchase(receipt);
        printHistoryOfGiveAway(receipt);
        printResultOfPurchase(receipt);
    }

    private static void printHistoryOfPurchase(Receipt receipt) {
        System.out.println();
        System.out.println(Message.RECEIPT_CONVENIENCE_NAME);
        System.out.println(Message.RECEIPT_HEAD_COLUMN);

        Map<String, History> totalHistory = receipt.getTotalHistory();
        totalHistory.entrySet().forEach(entry -> {
            History history = entry.getValue();
            String line = String.format(Message.RECEIPT_HEAD_VALUE.toString(), history.getName(), history.getQuantity(),
                    history.getTotalPrice());
            System.out.println(line);
        });
    }

    private static void printHistoryOfGiveAway(Receipt receipt) {
        System.out.println(Message.RECEIPT_GIVEAWAYS);
        List<Order> filteredOrders = receipt.stream()
                .filter(order -> order.getAmountOfGet() > 0)
                .toList();
        filteredOrders.forEach(order -> {
            SalesProduct salesProduct = order.getSalesProduct();
            Product product = salesProduct.getProduct();
            String line = String.format(RECEIPT_GIVEAWAYS_VALUE.toString(), product.getName(), order.getAmountOfGet());
            System.out.println(line);
        });
    }

    private static void printResultOfPurchase(Receipt receipt) {
        System.out.println("====================================");
        System.out.println(
                String.format("%-10s\t%-5d\t%s", "총구매액", receipt.getTotalQuantity(), receipt.getTotalPrice()));
        System.out.println(String.format("%-10s\t%-5s\t%s", "행사할인", "",
                receipt.getTotalPolicyDiscount(SalesType.PROMOTIONAL, Order::getAmountOfGet)));
        System.out.println(String.format("%-10s\t%-5s\t%s", "멤버십할인", "", receipt.getMembershipDiscount()));
        System.out.println(String.format("%-10s\t%-5s\t%s", "내실돈", "", receipt.getFinalPrice()));
    }


}
