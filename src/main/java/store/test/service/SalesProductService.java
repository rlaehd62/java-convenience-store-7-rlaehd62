package store.test.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import store.config.constant.ErrorMessage;
import store.config.constant.Regex;
import store.config.system.DataPath;
import store.config.system.SystemConfig;
import store.exception.ProductException;
import store.model.DataFile;
import store.test.policy.SalesPolicy;
import store.test.product.Product;
import store.test.product.SalesProduct;
import store.test.repository.ProductRepository;
import store.test.repository.SalesPolicyRepository;
import store.test.repository.SalesProductRepository;
import store.utility.DataReader;
import store.utility.Validator;

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
        int expected = possibleSets * (policy.getTotalAmount());
        return expected;
    }

    public void runThroughSalesProducts(Consumer<SalesProduct> consumer) {
        salesProductRepository.findAll()
                .forEach(consumer);
    }

    private SalesProduct loadSalesProduct(String line) {
        Validator.validate(line, Regex.LIST_FORMAT, ErrorMessage.FILE_FORMAT_INVALID);
        String DELIMITER = SystemConfig.DELIMITER.getValue();
        List<String> elements = List.of(line.split(DELIMITER));
        String productName = elements.get(0);
        String policyName = elements.get(3);
        SalesPolicy policy = salesPolicyRepository.findPolicy(policyName);
        Optional<Product> optional = productRepository.findProduct(productName);
        optional.orElseThrow(() -> new ProductException(ErrorMessage.NO_ITEM_FOUND));
        Product product = optional.get();
        return SalesProduct.of(product, policy, elements.get(2));
    }

    public void validateProduct(String name) {
        salesProductRepository.findSalesProductsByName(name);
    }

    public void loadSalesProducts() {
        DataFile file = DataReader.readData(DataPath.PRODUCTS_FILE);
        file.forEach(line -> {
            SalesProduct salesProduct = loadSalesProduct(line);
            salesProductRepository.save(salesProduct);
        });
    }
}
