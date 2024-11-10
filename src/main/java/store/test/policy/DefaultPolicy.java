package store.test.policy;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;

public class DefaultPolicy extends SalesPolicy {
    public DefaultPolicy(String name) {
        super(name, 1, 0, LocalDate.from(DateTimes.now()), LocalDate.from(DateTimes.now()));
    }

    @Override
    public int extractAmountOfGet(int n) {
        return 0;
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public boolean isNULL() {
        return true;
    }
}
