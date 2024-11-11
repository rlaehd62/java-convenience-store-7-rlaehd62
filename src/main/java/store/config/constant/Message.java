package store.config.constant;

import static java.lang.String.format;

public enum Message {
    CONVENIENCE_NAME("W편의점"),
    PURCHASE_EXAMPLE("[사이다-2],[감자칩-1]"),
    PURCHASE_ITEM(format("구매하실 상품명과 수량을 입력해 주세요. (예: %s)", PURCHASE_EXAMPLE)),
    GREETING(format("안녕하세요. %s입니다.", CONVENIENCE_NAME)),
    MORE_QUANTITY("현재 %s은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)"),
    CONVENIENCE_INTRO("현재 보유하고 있는 상품입니다."),
    MEMBERSHIP("멤버십 할인을 받으시겠습니까? (Y/N)"),
    PROMOTION_DISCOUNT("현재 %s %s개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)");

    private final String consoleMessage;

    Message(String consoleMessage) {
        this.consoleMessage = consoleMessage;
    }

    public String getConsoleMessage() {
        return consoleMessage;
    }

    @Override
    public String toString() {
        return consoleMessage;
    }
}
