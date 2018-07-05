package Interfaces;

import Util.LazyCall;

public interface LazyCatenableList<T extends LazyCall<?>> extends Iterable<T> {
    boolean isEmpty();

    LazyCall<LazyCatenableList<T>> cons(T value);

    LazyCall<LazyCatenableList<T>> snoc(T value);

    LazyCall<LazyCatenableList<T>> concat(LazyCatenableList<T> catenableList);

    T head();

    LazyCall<LazyCatenableList<T>> tail();
}
