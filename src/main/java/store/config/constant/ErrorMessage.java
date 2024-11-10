package store.config.constant;

import static java.lang.String.format;

public enum ErrorMessage {
    PREFIX("[ERROR]"),
    ETC(PREFIX + " %s"),
    CONVENIENCE_INVALID_INPUT(format("%s 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.", PREFIX)),
    ETC_INVALID_INPUT(format("%s 잘못된 입력입니다. 다시 입력해 주세요.", PREFIX)),
    NO_ITEM_FOUND(format("%s 존재하지 않는 상품입니다. 다시 입력해 주세요.", PREFIX)),
    QUANTITY_OVERFLOW(format("%s 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.", PREFIX)),
    QUANTITY_UNDERFLOW(format("%s 재고 수량이 부족하여 구매할 수 없습니다. 다시 입력해 주세요.", PREFIX)),
    FILE_NOT_FOUND(format("%s 해당 데이터 파일을 찾을 수 없습니다.", PREFIX)),
    FILE_FORMAT_INVALID(format("%s 해당 파일의 형식이 올바르지 않습니다, 다시 확인해주세요.", PREFIX)),
    NOT_POSITIVE_NUMBER(format("%s 0 또는 음수는 허용되지 않습니다, 다시 입력해주세요.", PREFIX)),
    FILE_VALUE_INVALID(format("%s 해당 파일 내의 허용되지 않은 값이 존재합니다, 다시 확인해주세요.", PREFIX)),
    PROMOTION_NOT_FOUND(format("%s 해당 프로모션을 찾을 수 없습니다.", PREFIX)),
    PRODUCT_READ_FAILURE(format("%s 등록된 제품을 불러오는데 실패했습니다.", PREFIX)),
    PROMOTION_READ_FAILURE(format("%s 등록된 프로모션을 불러오는데 실패했습니다.", PREFIX)),
    YN_FAILURE(format("%s 올바른 선택지를 입력해주세요.", PREFIX));

    private final String throwingMessage;

    ErrorMessage(String throwingMessage) {
        this.throwingMessage = throwingMessage;
    }

    @Override
    public String toString() {
        return throwingMessage;
    }
}
