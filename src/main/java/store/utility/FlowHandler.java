package store.utility;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class FlowHandler {

    private FlowHandler() {
        // 의도와 다르게 생성되는 것을 방지
    }

    public static boolean runWithRetry(Supplier<Boolean> supplier, Consumer<Exception> exceptionHandler) {
        try {
            return supplier.get();
        } catch (Exception e) {
            exceptionHandler.accept(e);
            return runWithRetry(supplier, exceptionHandler);
        }
    }

    public static void run(Runnable runnable, Consumer<Exception> exceptionHandler) {
        try {
            runnable.run();
        } catch (Exception e) {
            exceptionHandler.accept(e);
        }
    }

    public static void runWithRetry(Supplier<Boolean> supplier, Runnable successHandler,
                                    Consumer<Exception> exceptionHandler) {
        try {
            if (supplier.get()) {
                successHandler.run();
            }
        } catch (Exception e) {
            exceptionHandler.accept(e);
            runWithRetry(supplier, exceptionHandler);
        }
    }

    public static void runWithRetry(Supplier<Boolean> supplier, Runnable successHandler,
                                    Runnable failureHandler,
                                    Consumer<Exception> exceptionHandler) {
        try {
            if (supplier.get()) {
                successHandler.run();
                return;
            }

            failureHandler.run();
        } catch (Exception e) {
            exceptionHandler.accept(e);
            runWithRetry(supplier, exceptionHandler);
        }
    }
}
