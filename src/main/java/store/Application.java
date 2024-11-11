package store;

import store.config.SystemBean;
import store.controller.ControllerBroker;

public class Application {
    public static void main(String[] args) {

        SystemBean bean = SystemBean.getInstance();

        ControllerBroker broker = new ControllerBroker();
        broker.run();

        bean.clear();
    }
}