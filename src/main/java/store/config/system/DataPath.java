package store.config.system;

import static java.lang.String.format;

public enum DataPath {

    RESOURCE_FOLDER("src/main/resources"),
    TEST_RESOURCE_FOLDER("src/test/resources"),
    PRODUCTS_FILE(format("%s/products.md", RESOURCE_FOLDER)),
    PROMOTIONS_FILE(format("%s/promotions.md", RESOURCE_FOLDER)),
    TEST_PRODUCTS_FILE(format("%s/products.md", TEST_RESOURCE_FOLDER)),
    TEST_PROMOTIONS_FILE(format("%s/promotions.md", TEST_RESOURCE_FOLDER));;

    private final String value;

    DataPath(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
