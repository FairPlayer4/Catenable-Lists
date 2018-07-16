package NonLazy;

import Interfaces.List;
import Interfaces.Queue;

import java.util.Iterator;

public class BankersQueue<T> implements Queue<T>
{
    private int sizef;

    private List<T> front;

    private int sizer;

    private List<T> rear;

    private BankersQueue(int sizef, List<T> front, int sizer, List<T> rear)
    {
        this.sizef = sizef;
        this.front = front;
        this.sizer = sizer;
        this.rear = rear;
    }

    public BankersQueue()
    {
        front = new PersistentList<>();
        rear = new PersistentList<>();
    }

    private BankersQueue<T> check(int sizef, List<T> front, int sizer, List<T> rear) {
        if (sizer <= sizef) return new BankersQueue<>(sizef, front, sizer, rear);
        return new BankersQueue<>(sizef + sizer, front.concat(rear.reverse()), 0, new PersistentList<>());
    }

    @Override
    public boolean isEmpty()
    {
        return sizef == 0;
    }

    @Override
    public Queue<T> snoc(T value)
    {
        return check(sizef, front, (sizer+1), rear.prepend(value));
    }

    @Override
    public T head()
    {
        if (front.isEmpty()) throw new RuntimeException("empty queue");
        return front.head();
    }

    @Override
    public Queue<T> tail()
    {
        if (front.isEmpty()) throw new RuntimeException("empty queue");
        return check(sizef-1, front.tail(), sizer, rear);
    }

    @Override
    public Iterator<T> iterator() {
        return new BankersQueueIterator<>(this);
    }


    private static final class BankersQueueIterator<T> implements Iterator<T> {

        private BankersQueue<T> bankersQueue;

        private boolean firstList = true;

        private Iterator<T> listIterator;

        BankersQueueIterator(BankersQueue<T> bankersQueue) {
            this.bankersQueue = bankersQueue;
            if (bankersQueue != null && !bankersQueue.isEmpty()) {
                listIterator = bankersQueue.front.iterator();
            }
        }

        @Override
        public boolean hasNext() {
            if (bankersQueue != null && !bankersQueue.isEmpty() && listIterator != null) {
                if (listIterator.hasNext()) return true;
                if (firstList) {
                    firstList = false;
                    listIterator = bankersQueue.rear.reverse().iterator();
                    return listIterator.hasNext();
                }
            }
            return false;
        }

        @Override
        public T next() {
            return listIterator.next();
        }
    }
}
