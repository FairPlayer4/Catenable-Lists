package Interfaces;

public interface CatenableList<T>
{
    boolean isEmpty();

    CatenableList<T> cons(T value);

    CatenableList<T> snoc(T value);

    CatenableList<T> concat(CatenableList<T> catenableList);

    T head();

    CatenableList<T> tail();

    void printWithoutConcat();

    void toStringEfficient(StringBuilder sb);

}
