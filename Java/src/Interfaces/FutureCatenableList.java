package Interfaces;

import Util.FutureCall;

public interface FutureCatenableList<T> extends CatenableList<T> {

    FutureCall<CatenableList<T>> futureTail();

}
