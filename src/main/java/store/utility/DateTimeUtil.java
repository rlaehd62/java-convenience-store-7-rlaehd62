package store.utility;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDateTime;

public class DateTimeUtil {
    private static DateTimeUtil dateTimeUtil;
    private LocalDateTime fixedLocalDateTime;
    private boolean isFixedMode = false;


    private DateTimeUtil() {
    }

    public static synchronized DateTimeUtil getInstance() {
        if (dateTimeUtil == null) {
            dateTimeUtil = new DateTimeUtil();
        }
        return dateTimeUtil;
    }

    public LocalDateTime now() {
        if (isFixedMode) {
            return fixedLocalDateTime;
        }
        return DateTimes.now();
    }

    public void fixedMode(LocalDateTime localDateTime) {
        isFixedMode = true;
        this.fixedLocalDateTime = localDateTime;
    }

    public void resetFixedMode() {
        isFixedMode = false;
    }

}
