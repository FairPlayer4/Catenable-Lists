package NonLazy;

import Interfaces.List;
import Interfaces.Queue;

public class BatchedQueue<T> implements Queue<T>
{

    private List<T> firstList;

    private List<T> secondList;

    private BatchedQueue(List<T> firstList, List<T> secondList)
    {
        this.firstList = firstList;
        if (this.firstList == null) this.firstList = new PersistentList<>();
        this.secondList = secondList;
        if (this.secondList == null) this.secondList = new PersistentList<>();
    }

    public BatchedQueue()
    {
        firstList = new PersistentList<>();
        secondList = new PersistentList<>();
    }

    private static <T> Queue<T> check(List<T> firstList, List<T> secondList)
    {
        if (firstList == null || firstList.isEmpty()) return new BatchedQueue<>(secondList.reverse(), null);
        return new BatchedQueue<>(firstList, secondList);
    }

    @Override
    public Queue<T> snoc(T value)
    {
        return check(firstList, secondList.prepend(value));
    }

    @Override
    public boolean isEmpty()
    {
        return firstList.isEmpty();
    }

    @Override
    public T head()
    {
        if (firstList.isEmpty()) throw new RuntimeException("empty queue");
        return firstList.head();
    }

    @Override
    public Queue<T> tail()
    {
        if (firstList.isEmpty()) throw new RuntimeException("empty queue");
        return check(firstList.tail(), secondList);
    }

    @Override
    public void printWithoutConcat() {
        if (isEmpty()) return;
        System.out.print(head());
        Queue<T> tail = tail();
        if (tail.isEmpty()) return;
        System.out.print(",\n");
        tail.printWithoutConcat();
    }

    @Override
    public void toStringEfficient(StringBuilder sb)
    {
        //TODO
    }

    @Override
    public String toString() {
        if (isEmpty()) return "";
        Queue<T> tail = tail();
        if (tail.isEmpty()) return head().toString();
        return head() + "\n" + tail();
    }

}
