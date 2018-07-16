package Lazy;

import Interfaces.FutureList;
import Interfaces.FutureQueue;
import Interfaces.List;
import Util.FutureCall;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class FutureBankersQueue<T> implements FutureQueue<T> {
    private final int sizef;

    private final List<T> front;

    private final int sizer;

    private final List<T> rear;

    private FutureBankersQueue(int sizef, List<T> front, int sizer, List<T> rear) {
        this.sizef = sizef;
        this.front = front;
        this.sizer = sizer;
        this.rear = rear;
        nextTail = new FutureCall<>(() -> check(sizef - 1, front.tail(), sizer, rear));
    }

    public FutureBankersQueue() {
        sizef = 0;
        front = new FuturePersistentList<>();
        sizer = 0;
        rear = new FuturePersistentList<>();
    }

    private FutureBankersQueue<T> check(int sizef, List<T> front, int sizer, List<T> rear) {
        if (sizer <= sizef) return new FutureBankersQueue<>(sizef, front, sizer, rear);
        return new FutureBankersQueue<>(sizef + sizer, front.concat(rear.reverse()), 0, new FuturePersistentList<>());
    }

    @Override
    public boolean isEmpty() {
        return sizef == 0;
    }

    @Override
    public FutureQueue<T> snoc(T value) {
        return check(sizef, front, (sizer + 1), rear.prepend(value));
    }

    @Override
    public T head() {
        if (front.isEmpty()) throw new RuntimeException("empty queue");
        return front.head();
    }

    @Override
    public FutureQueue<T> tail() {
        if (front.isEmpty()) throw new RuntimeException("empty queue");
        final FutureBankersQueue<T> result;
        result = nextTail == null ? check(sizef - 1, front.tail(), sizer, rear) : nextTail.get();
        return result;
    }

    private FutureCall<FutureBankersQueue<T>> nextTail;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(sizef + sizer);
        sb.append("[");
        for (T value : this) {
            sb.append(value);
            sb.append(", ");
        }
        int i = sb.lastIndexOf(", ");
        if (i != -1) {
            sb.deleteCharAt(i);
            sb.deleteCharAt(i + 1);
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public Iterator<T> iterator() {
        return new LazyBankersQueueIterator<>(this);
    }

    private static final class LazyBankersQueueIterator<T> implements Iterator<T> {

        private FutureBankersQueue<T> bankersQueue;

        private boolean firstList = true;

        private Iterator<T> listIterator;

        LazyBankersQueueIterator(FutureBankersQueue<T> bankersQueue) {
            this.bankersQueue = bankersQueue;
            if (bankersQueue != null && !bankersQueue.isEmpty()) {
                listIterator = bankersQueue.front.iterator();
            }
        }

        @Override
        public boolean hasNext() {
            return bankersQueue != null && !bankersQueue.isEmpty() && listIterator != null;
        }

        @Override
        public T next() {
            if (!hasNext()) throw new NoSuchElementException();
            if (!listIterator.hasNext() && firstList) {
                listIterator = bankersQueue.rear.reverse().iterator();
            }
            if (listIterator.hasNext()) {
                return listIterator.next();
            }
            throw new NoSuchElementException();
        }
    }
}
