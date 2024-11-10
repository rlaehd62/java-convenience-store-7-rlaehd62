package store;

import store.config.SystemBean;
import store.controller.ControllerBroker;

public class Application {
    public static void main(String[] args) {

        // 시스템 Bean 설정
        SystemBean bean = SystemBean.getInstance();

        // Controller 중계기 실행
        ControllerBroker broker = new ControllerBroker();
        broker.run();

        // 시스템 Bean 초기화
        bean.clear();
    }
}