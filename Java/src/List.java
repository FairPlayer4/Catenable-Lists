public interface List<T>
{
    boolean isEmpty();

    // : Operator
    List<T> prepend(T value);

    List<T> reverse();

    T head();

    List<T> tail();
}
