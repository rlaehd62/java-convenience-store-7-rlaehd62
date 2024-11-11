package store.mock.model;

import camp.nextstep.edu.missionutils.DateTimes;
import store.model.policy.SalesPolicy;

public class FakeSalesPolicy extends SalesPolicy {
    public FakeSalesPolicy() {
        super("가짜할인", 2, 1, DateTimes.now().toLocalDate(), DateTimes.now().toLocalDate());
    }
}
