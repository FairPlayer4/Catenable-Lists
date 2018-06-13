public interface CatenableList<T> extends Queue<T>
{
    @Override
    boolean isEmpty();

    CatenableList<T> cons(T value);

    @Override
    CatenableList<T> snoc(T value);

    CatenableList<T> concat(CatenableList<T> list);

    @Override
    T head();

    @Override
    CatenableList<T> tail();

    @Override
    void printWithoutConcat();

    @Override
    void toStringEfficient(StringBuilder sb);

}
