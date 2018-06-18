package Interfaces;

public interface Queue<T>
{

    boolean isEmpty();

    Queue<T> snoc(T value);

    T head();

    Queue<T> tail();

    void printWithoutConcat();

    void toStringEfficient(StringBuilder sb);

}
