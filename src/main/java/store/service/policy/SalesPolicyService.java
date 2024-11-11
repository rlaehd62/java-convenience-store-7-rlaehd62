package store.service.policy;

import java.util.List;
import store.config.constant.ErrorMessage;
import store.config.constant.Regex;
import store.config.system.DataPath;
import store.config.system.SystemConfig;
import store.model.DataFile;
import store.model.policy.SalesPolicy;
import store.repository.policy.SalesPolicyRepository;
import store.utility.DataReader;
import store.utility.Validator;

public class SalesPolicyService {

    private SalesPolicyRepository salesPolicyRepository;

    public SalesPolicyService(SalesPolicyRepository salesPolicyRepository) {
        this.salesPolicyRepository = salesPolicyRepository;
    }

    public void loadPolicies() {
        DataFile file = DataReader.readData(DataPath.PROMOTIONS_FILE);
        file.forEach(line -> {
            SalesPolicy policy = loadPolicy(line);
            salesPolicyRepository.save(policy);
        });
    }

    private SalesPolicy loadPolicy(String line) {
        Validator.validate(line, Regex.LIST_FORMAT, ErrorMessage.FILE_FORMAT_INVALID);
        String DELIMITER = SystemConfig.DELIMITER.getValue();
        List<String> elements = List.of(line.split(DELIMITER));
        return SalesPolicy.of(elements);
    }
}
