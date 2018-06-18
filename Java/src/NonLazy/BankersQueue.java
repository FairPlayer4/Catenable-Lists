package NonLazy;

import Interfaces.List;
import Interfaces.Queue;

public class BankersQueue<T> implements Queue<T>
{
    private int sizef;

    private List<T> f;

    private int sizer;

    private List<T> r;

    public BankersQueue(int sizef, List<T> f, int sizer, List<T> r)
    {
        this.sizef = sizef;
        this.f = f;
        this.sizer = sizer;
        this.r = r;
    }

    public BankersQueue()
    {
        f = new PersistentList<>();
        r = new PersistentList<>();
    }

    private static <T> BankersQueue<T> check(int sizef, List<T> f, int sizer, List<T> r) {
        if (sizer <= sizef) return new BankersQueue<>(sizef, f, sizer, r);
        else return new BankersQueue<>(sizef + sizer, f.concat(r.reverse()), 0, new PersistentList<>());
    }

    @Override
    public boolean isEmpty()
    {
        return sizef == 0;
    }

    @Override
    public Queue<T> snoc(T value)
    {
        return check(sizef, f, (sizer+1), r.prepend(value));
    }

    @Override
    public T head()
    {
        if (f.isEmpty()) throw new RuntimeException("empty queue");
        return f.head();
    }

    @Override
    public Queue<T> tail()
    {
        if (f.isEmpty()) throw new RuntimeException("empty queue");
        return check(sizef-1, f.tail(), sizer, r);
    }

    @Override
    public void printWithoutConcat()
    {
        //TODO
    }

    @Override
    public void toStringEfficient(StringBuilder sb)
    {
        //TODO
    }
}
