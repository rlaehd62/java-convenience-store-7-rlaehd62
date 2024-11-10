package store.test.service;

import java.util.List;
import store.config.constant.ErrorMessage;
import store.config.constant.Regex;
import store.config.system.DataPath;
import store.config.system.SystemConfig;
import store.model.DataFile;
import store.test.policy.SalesPolicy;
import store.test.product.Product;
import store.test.product.SalesProduct;
import store.test.product.SalesType;
import store.test.repository.ProductRepository;
import store.test.repository.SalesPolicyRepository;
import store.test.repository.SalesProductRepository;
import store.utility.DataReader;
import store.utility.Validator;

public class ProductService {
    private final ProductRepository productRepository;
    private final SalesProductRepository salesProductRepository;
    private final SalesPolicyRepository salesPolicyRepository;

    public ProductService(ProductRepository productRepository, SalesProductRepository salesProductRepository, SalesPolicyRepository salesPolicyRepository) {
        this.productRepository = productRepository;
        this.salesProductRepository = salesProductRepository;
        this.salesPolicyRepository = salesPolicyRepository;
    }

    private Product loadProduct(String line) {
        Validator.validate(line, Regex.LIST_FORMAT, ErrorMessage.FILE_FORMAT_INVALID);
        String DELIMITER = SystemConfig.DELIMITER.getValue();
        List<String> elements = List.of(line.split(DELIMITER));
        return Product.of(elements);
    }

    private void createSalesProducts(Product product, SalesType type) {
        SalesProduct salesProduct = SalesProduct.of(product, salesPolicyRepository.DEFAULT_POLICY(), type,"0");
        salesProductRepository.save(salesProduct);
    }

    public void loadsProducts() {
        DataFile file = DataReader.readData(DataPath.PRODUCTS_FILE);
        file.forEach(line -> {
            Product product = loadProduct(line);
            productRepository.save(product);
            createSalesProducts(product, SalesType.PROMOTIONAL);
            createSalesProducts(product, SalesType.NORMAL);
        });
    }
}
