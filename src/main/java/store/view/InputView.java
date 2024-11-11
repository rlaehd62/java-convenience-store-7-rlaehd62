package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.config.constant.Message;
import store.model.order.Order;
import store.model.product.Product;
import store.model.product.SalesProduct;

public class InputView {
    public static String readOrder() {
        System.out.println();
        System.out.println(Message.PURCHASE_ITEM);
        return Console.readLine();
    }

    public static String readForMembershipDiscount() {
        System.out.println();
        System.out.println("멤버십 할인을 받으시겠습니까? (Y/N)");
        return Console.readLine();
    }

    public static String readForGiveAways(String name) {
        System.out.println();
        System.out.printf(Message.MORE_QUANTITY.toString(), name);
        System.out.println();
        return Console.readLine();
    }

    public static String readForPromotion(Order order) {
        SalesProduct salesProduct = order.getSalesProduct();
        Product product = salesProduct.getProduct();
        String name = product.getName();
        int quantity = order.getAmountOfBuy();
        System.out.println();
        System.out.println(String.format("현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)", name, quantity));
        return Console.readLine();
    }

    public static String readForRetry() {
        System.out.println();
        System.out.println("감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)");
        return Console.readLine();
    }
}
