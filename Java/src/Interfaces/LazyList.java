package Interfaces;

import Util.LazyCall;

public interface LazyList<T extends LazyCall<?>> extends Iterable<T> {

    boolean isEmpty();

    // : Operator
    LazyCall<LazyList<T>> prepend(T value);

    LazyCall<LazyList<T>> concat(LazyList<T> list);

    LazyCall<LazyList<T>> reverse();

    T head();

    LazyCall<LazyList<T>> tail();
}
