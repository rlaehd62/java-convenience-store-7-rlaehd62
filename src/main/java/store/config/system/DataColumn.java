package store.config.system;

import java.util.Arrays;
import java.util.Optional;
import store.config.constant.Regex;

public enum DataColumn {

    NAME("name", Regex.NOT_EMPTY),
    PROMOTION("promotion", Regex.NOT_EMPTY),
    BUY("buy", Regex.NO_NEGATIVE_NUMBER),
    PRICE("price", Regex.NO_NEGATIVE_NUMBER),
    QUANTITY("quantity", Regex.NO_NEGATIVE_NUMBER);

    private final String columnName;
    private final Regex restriction;

    DataColumn(String columnName, Regex restriction) {
        this.columnName = columnName;
        this.restriction = restriction;
    }

    public static Optional<DataColumn> of(String columnName) {
        return Arrays.stream(values())
                .filter(column -> column.columnName.equalsIgnoreCase(columnName))
                .findFirst();
    }

    public String getColumnName() {
        return columnName;
    }

    public Regex getRestriction() {
        return restriction;
    }
}
