package Util;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public final class TaskUtils {

    public static final ExecutorService EXECUTOR
            = Executors.newSingleThreadExecutor();

    public static <T> T safeGet(Future<T> future) {
        try {
            T f = future.get();
            return f;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Future failed!");
    }
}
