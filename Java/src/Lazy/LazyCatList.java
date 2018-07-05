package Lazy;

import Interfaces.LazyCatenableList;
import Interfaces.LazyQueue;
import Util.LazyCall;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LazyCatList<T extends LazyCall<?>> implements LazyCatenableList<T> {

    private final T value;

    private final LazyQueue<LazyCall<LazyCatenableList<T>>> lazyCatListQueue;

    public LazyCatList(T value) {
        this.value = value;
        lazyCatListQueue = new LazyBankersQueue<>();
    }

    private LazyCatList(T value, LazyQueue<LazyCall<LazyCatenableList<T>>> lazyCatListQueue) {
        this.value = value;
        this.lazyCatListQueue = lazyCatListQueue;
    }

    public LazyCatList() {
        value = null;
        lazyCatListQueue = null;
    }

    private static <T extends LazyCall<?>> LazyCall<LazyCatenableList<T>> link(LazyCatList<T> lazyCatList, LazyCall<LazyCatenableList<T>> lazyCatListFuture) {
        return new LazyCall<>(() -> new LazyCatList<>(lazyCatList.value, lazyCatList.lazyCatListQueue.snoc(lazyCatListFuture).get()));
    }

    private static <T extends LazyCall<?>> LazyCall<LazyCatenableList<T>> linkAll(LazyQueue<LazyCall<LazyCatenableList<T>>> lazyCatListFutureQueue) {
        return new LazyCall<>(() -> {
            LazyCatenableList<T> check = lazyCatListFutureQueue.head().get();
            if (!(check instanceof LazyCatList)) throw new RuntimeException("invalid LazyCatenableList implementation");
            LazyCatList<T> head = (LazyCatList<T>) check;
            LazyQueue<LazyCall<LazyCatenableList<T>>> tail = lazyCatListFutureQueue.tail().get();
            return tail.isEmpty() ? head : link(head, linkAll(tail)).get();
        });
    }

    @Override
    public boolean isEmpty() {
        return lazyCatListQueue == null;
    }

    @Override
    public LazyCall<LazyCatenableList<T>> cons(T value) {
        return new LazyCatList<>(value).concat(this);
    }

    @Override
    public LazyCall<LazyCatenableList<T>> snoc(T value) {
        return concat(new LazyCatList<>(value));
    }

    @Override
    public LazyCall<LazyCatenableList<T>> concat(LazyCatenableList<T> catenableList) {
            if (isEmpty()) return new LazyCall<>(catenableList);
            if (catenableList == null || catenableList.isEmpty()) return new LazyCall<>(this);
            return link(this, new LazyCall<>(catenableList));
    }

    @Override
    public T head() {
        if (isEmpty()) throw new RuntimeException("empty list");
        return value;
    }

    @Override
    public LazyCall<LazyCatenableList<T>> tail() {
        if (isEmpty()) throw new RuntimeException("empty list");
        if (lazyCatListQueue.isEmpty()) return new LazyCall<>(new LazyCatList<>());
        else return linkAll(lazyCatListQueue);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
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
        return new LazyCatListIterator<>(this);
    }

    private static final class LazyCatListIterator<T extends LazyCall<?>> implements
            Iterator<T> {

        private boolean initial = true;

        private Iterator<LazyCall<LazyCatenableList<T>>> futureLazyCatListIterator;

        private Iterator<T> currentLazyCatListIterator;

        private final LazyCatList<T> lazyCatList;

        public LazyCatListIterator(LazyCatList<T> lazyCatList) {
            this.lazyCatList = lazyCatList;
        }

        @Override
        public boolean hasNext() {
            if (lazyCatList == null || lazyCatList.isEmpty()) return false;
            if (initial) return true;
            if (futureLazyCatListIterator == null || currentLazyCatListIterator == null) return false;
            return futureLazyCatListIterator.hasNext() || currentLazyCatListIterator.hasNext();
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            if (initial) {
                initial = false;
                futureLazyCatListIterator = lazyCatList.lazyCatListQueue.iterator();
                if (futureLazyCatListIterator.hasNext()) {
                    currentLazyCatListIterator = futureLazyCatListIterator.next().get().iterator();
                }
                return lazyCatList.value;
            }
            if (currentLazyCatListIterator.hasNext()) {
                return currentLazyCatListIterator.next();
            } else if (futureLazyCatListIterator.hasNext()) {
                currentLazyCatListIterator = futureLazyCatListIterator.next().get().iterator();
                return currentLazyCatListIterator.next();
            }
            throw new NoSuchElementException();
        }
    }
}
