package store.config.constant;

public enum Regex {
    PURCHASE_SINGLE("^\\[[^\\s*-]+-[1-9][0-9]*\\](,\\[[^\\s*-]+-[1-9][0-9]*\\])*$"),
    YES_OR_NO("^(?:Y|N)$"),
    NOT_EMPTY("^(?=.*\\S).+$"),
    LIST_FORMAT("^[^,\\s]+(\\s*,\\s*[^,\\s]+)+$"),
    NO_NEGATIVE_NUMBER("^[0-9]\\d*$");

    private final String expression;

    Regex(String expression) {
        this.expression = expression;
    }

    public String getExpression() {
        return expression;
    }

    @Override
    public String toString() {
        return expression;
    }
}
