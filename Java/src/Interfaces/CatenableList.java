package Interfaces;

public interface CatenableList<T> extends Iterable<T>
{
    boolean isEmpty();

    CatenableList<T> cons(T value);

    CatenableList<T> snoc(T value);

    CatenableList<T> concat(CatenableList<T> catenableList);

    T head();

    CatenableList<T> tail();

}
