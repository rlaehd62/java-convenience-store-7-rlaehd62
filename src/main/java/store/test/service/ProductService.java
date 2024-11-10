package store.test.service;

import java.util.List;
import store.config.constant.ErrorMessage;
import store.config.constant.Regex;
import store.config.system.DataPath;
import store.config.system.SystemConfig;
import store.model.DataFile;
import store.test.product.Product;
import store.test.repository.ProductRepository;
import store.utility.DataReader;
import store.utility.Validator;

public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    private Product loadProduct(String line) {
        Validator.validate(line, Regex.LIST_FORMAT, ErrorMessage.FILE_FORMAT_INVALID);
        String DELIMITER = SystemConfig.DELIMITER.getValue();
        List<String> elements = List.of(line.split(DELIMITER));
        return Product.of(elements);
    }

    public void loadsProducts() {
        DataFile file = DataReader.readData(DataPath.PRODUCTS_FILE);
        file.forEach(line -> {
            Product product = loadProduct(line);
            productRepository.save(product);
        });
    }
}
