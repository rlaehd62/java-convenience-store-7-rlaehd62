package store.controller;

import store.config.ServiceBean;
import store.test.service.SalesProductService;
import store.view.OutputView;

public class FrontController {

    private SalesProductService salesProductService;

    public FrontController() {
        ServiceBean bean = ServiceBean.getInstance();
        salesProductService = bean.getSalesProductService();
    }

    public void showFront() {
        OutputView.printGreeting();
        OutputView.of("");
        salesProductService.runThroughSalesProducts(product -> OutputView.of(product.toString()));
    }
}
