package store.model.product;

import store.model.policy.SalesPolicy;

public enum SalesType {
    NORMAL("일반 상품"),

    PROMOTIONAL("행사 상품");

    final String type;

    SalesType(String type) {
        this.type = type;
    }

    public static SalesType getType(SalesPolicy policy) {
        if (policy.isNULL()) {
            return NORMAL;
        }

        return PROMOTIONAL;
    }
}
