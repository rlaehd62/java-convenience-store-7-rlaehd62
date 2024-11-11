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
        } catch (RuntimeException e) {
            exceptionHandler.accept(e);
            return runWithRetry(supplier, exceptionHandler);
        }
    }

    public static void run(Runnable runnable, Consumer<Exception> exceptionHandler) {
        try {
            runnable.run();
        } catch (RuntimeException e) {
            exceptionHandler.accept(e);
        }
    }

    public static boolean runWithReturnWithBoolean(Supplier<Boolean> supplier, Consumer<Exception> exceptionHandler) {
        try {
            return supplier.get();
        } catch (RuntimeException e) {
            exceptionHandler.accept(e);
            return runWithReturnWithBoolean(supplier, exceptionHandler);
        }
    }

    public static void runWithRetry(Supplier<Boolean> supplier, Runnable successHandler,
                                    Consumer<Exception> exceptionHandler) {
        try {
            if (supplier.get()) {
                successHandler.run();
            }
        } catch (RuntimeException e) {
            exceptionHandler.accept(e);
            runWithRetry(supplier, exceptionHandler);
        }
    }
}
