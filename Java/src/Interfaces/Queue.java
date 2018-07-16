package Interfaces;

public interface Queue<T> extends Iterable<T> {
    boolean isEmpty();

    Queue<T> snoc(T value);

    T head();

    Queue<T> tail();
}
