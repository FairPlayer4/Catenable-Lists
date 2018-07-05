package Interfaces;

public interface List<T> extends Iterable<T>
{
    boolean isEmpty();

    // : Operator
    List<T> prepend(T value);

    List<T> concat(List<T> list);

    List<T> reverse();

    T head();

    List<T> tail();
}
