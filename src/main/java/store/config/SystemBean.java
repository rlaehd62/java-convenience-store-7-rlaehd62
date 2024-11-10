package store.config;

public class SystemBean {
    private static SystemBean instance = null;

    private SystemBean() {
        RepositoryBean.getInstance();
        ServiceBean.getInstance();
    }

    public static SystemBean getInstance() {
        if (instance == null) {
            instance = new SystemBean();
        }
        return instance;
    }

    public void clear() {
        RepositoryBean.getInstance()
                .clear();
        ServiceBean.getInstance()
                .clear();
        instance = null;
    }
}
