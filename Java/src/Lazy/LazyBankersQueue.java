package Lazy;

import Interfaces.LazyList;
import Interfaces.LazyQueue;
import Util.LazyCall;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LazyBankersQueue<T extends LazyCall<?>> implements LazyQueue<T> {
    private final int sizef;

    private final LazyList<T> f;

    private final int sizer;

    private final LazyList<T> r;

    private LazyBankersQueue(int sizef, LazyList<T> f, int sizer, LazyList<T> r) {
        this.sizef = sizef;
        this.f = f;
        this.sizer = sizer;
        this.r = r;
    }

    public LazyBankersQueue() {
        sizef = 0;
        f = new LazyPersistentList<>();
        sizer = 0;
        r = new LazyPersistentList<>();
    }

    private static <T extends LazyCall<?>> LazyCall<LazyQueue<T>> check(int sizef, LazyList<T> f, int sizer, LazyList<T> r) {
        if (sizer <= sizef) return new LazyCall<>(() -> new LazyBankersQueue<>(sizef, f, sizer, r));
        else
            return new LazyCall<>(() -> new LazyBankersQueue<>(sizef + sizer, f.concat(r.reverse().get()).get(), 0, new LazyPersistentList<>()));
    }

    @Override
    public boolean isEmpty() {
        return sizef == 0;
    }

    @Override
    public LazyCall<LazyQueue<T>> snoc(T value) {
        return null;
        //return check(sizef, new LazyCall<>(f), (sizer + 1), r.prepend(value));
    }

    @Override
    public T head() {
        if (f.isEmpty()) throw new RuntimeException("empty queue");
        return f.head();
    }

    @Override
    public LazyCall<LazyQueue<T>> tail() {
        if (f.isEmpty()) throw new RuntimeException("empty queue");
        return null;
        //return check(sizef - 1, f.tail(), sizer, new LazyCall<>(r));
    }

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

    private static final class LazyBankersQueueIterator<T extends LazyCall<?>> implements Iterator<T> {

        private LazyBankersQueue<T> bankersQueue;

        private boolean firstList = true;

        private Iterator<T> listIterator;

        LazyBankersQueueIterator(LazyBankersQueue<T> bankersQueue) {
            this.bankersQueue = bankersQueue;
            if (bankersQueue != null && !bankersQueue.isEmpty()) {
                listIterator = bankersQueue.f.iterator();
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
                listIterator = bankersQueue.r.reverse().get().iterator();
            }
            if (listIterator.hasNext()) {
                return listIterator.next();
            }
            throw new NoSuchElementException();
        }
    }
}
