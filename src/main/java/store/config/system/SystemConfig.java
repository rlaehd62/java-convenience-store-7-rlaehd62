package store.config.system;

public enum SystemConfig {
    NULL("null"),
    BAR("-"),
    DELIMITER(",");

    private final String value;

    SystemConfig(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
