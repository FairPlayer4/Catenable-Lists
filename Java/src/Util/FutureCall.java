package Util;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class FutureCall<T> {

    private boolean available;

    private T object;

    private Future<T> future;

    public FutureCall(T object) {
        this.object = object;
        available = true;
    }

    public FutureCall(Callable<T> callable) {
        future = TaskUtils.EXECUTOR.submit(callable);
    }

    public T get() {
        if (available) return object;
        object = TaskUtils.safeGet(future);
        available = true;
        return object;
    }

    @Override
    public String toString() {
        return get().toString();
    }

}
