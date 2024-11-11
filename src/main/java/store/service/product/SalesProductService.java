package store.service.product;

import java.util.List;
import java.util.function.Consumer;
import store.config.system.DataPath;
import store.config.system.SystemConfig;
import store.model.DataFile;
import store.model.policy.SalesPolicy;
import store.model.product.SalesProduct;
import store.model.product.SalesType;
import store.repository.policy.SalesPolicyRepository;
import store.repository.product.ProductRepository;
import store.repository.product.SalesProductRepository;
import store.utility.DataReader;

public class SalesProductService {

    private final SalesPolicyRepository salesPolicyRepository;
    private final ProductRepository productRepository;
    private final SalesProductRepository salesProductRepository;

    public SalesProductService(SalesPolicyRepository salesPolicyRepository, ProductRepository productRepository,
                               SalesProductRepository salesProductRepository) {
        this.salesPolicyRepository = salesPolicyRepository;
        this.productRepository = productRepository;
        this.salesProductRepository = salesProductRepository;
    }

    public int optimizeQuantityForPromotion(SalesProduct salesProduct, int amountOfBuy) {
        if (amountOfBuy >= salesProduct.getQuantity()) {
            return amountOfBuy;
        }
        SalesPolicy policy = salesProduct.getPolicy();
        int r = amountOfBuy % policy.getTotalAmount();
        int addition = r / policy.getAmountOfBuy();
        return amountOfBuy + addition;
    }

    public int getPossibleAmount(SalesProduct salesProduct, int amountOfBuy) {
        SalesPolicy policy = salesProduct.getPolicy();
        amountOfBuy = Math.min(salesProduct.getQuantity(), amountOfBuy);
        int possibleSets = amountOfBuy / (policy.getTotalAmount());
        int expectedQuantity = possibleSets * (policy.getTotalAmount());
        int remainingQuantity = salesProduct.getQuantity() - expectedQuantity;
        if (remainingQuantity > 0 && (amountOfBuy - expectedQuantity) > remainingQuantity) {
            return expectedQuantity + remainingQuantity;
        }
        return possibleSets * (policy.getTotalAmount());
    }

    public void runThroughSalesProducts(Consumer<SalesProduct> consumer) {
        salesProductRepository.findAll()
                .forEach(consumer);
    }

    private void updateScalesProduct(String line) {
        String DELIMITER = SystemConfig.DELIMITER.getValue();
        List<String> elements = List.of(line.split(DELIMITER));
        String productName = elements.get(0);
        String policyName = elements.get(3);
        SalesPolicy policy = salesPolicyRepository.findPolicy(policyName);
        SalesType type = SalesType.getType(policy);
        SalesProduct salesProduct = salesProductRepository.findSalesProductsByName(productName, type);
        salesProduct.setQuantity(Integer.parseInt(elements.get(2)));
        salesProduct.setPolicy(policy);
    }

    public void loadSalesProducts() {
        DataFile file = DataReader.readData(DataPath.PRODUCTS_FILE);
        file.forEach(this::updateScalesProduct);
    }
}
