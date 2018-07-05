package Interfaces;

import Util.LazyCall;

public interface LazyQueue<T extends LazyCall<?>> extends Iterable<T> {

    boolean isEmpty();

    LazyCall<LazyQueue<T>> snoc(T value);

    T head();

    LazyCall<LazyQueue<T>> tail();

}
