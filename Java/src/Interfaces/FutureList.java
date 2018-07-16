package Interfaces;

import Util.FutureCall;

public interface FutureList<T> extends List<T> {

    FutureCall<List<T>> futureConcat(List<T> list);

    FutureCall<List<T>> futureReverse();

}
