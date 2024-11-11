package store.config.constant;


public enum Message {
    CONVENIENCE_SYMBOL("W"),
    CONVENIENCE_NAME(String.format("%s편의점", CONVENIENCE_SYMBOL)),
    RECEIPT_CONVENIENCE_NAME(String.format("==============%s 편의점================", CONVENIENCE_SYMBOL)),
    RECEIPT_GIVEAWAYS("=============증\t\t정==============="),
    RECEIPT_GIVEAWAYS_VALUE("%-10s\t%-2d"),

    RECEIPT_HEAD_COLUMN(String.format("%-10s\t%-2s\t%-10s", "상품명", "수량", "금액")),
    RECEIPT_HEAD_VALUE("%-10s\t%-5s\t%,d"),
    RECEIPT_MONEY_VALUE("%-10s\t%-5d\t%s"),
    PRODUCT_WITHOUT_QUANTITY("- %s 재고 없음 %s"),
    PRODUCT_WITH_QUANTITY("- %s %s개 %s"),
    PURCHASE_EXAMPLE("[사이다-2],[감자칩-1]"),

    PURCHASE_ITEM(String.format("구매하실 상품명과 수량을 입력해 주세요. (예: %s)", PURCHASE_EXAMPLE)),
    GREETING(String.format("안녕하세요. %s입니다.", CONVENIENCE_NAME)),
    MORE_QUANTITY("현재 %s은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)"),
    CONVENIENCE_INTRO("현재 보유하고 있는 상품입니다."),
    MEMBERSHIP("멤버십 할인을 받으시겠습니까? (Y/N)"),

    CONTINUE("감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)"),

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
