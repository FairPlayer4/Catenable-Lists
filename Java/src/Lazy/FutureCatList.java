package Lazy;

import Interfaces.CatenableList;
import Interfaces.FutureCatenableList;
import Interfaces.Queue;
import Util.FutureCall;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class FutureCatList<T> implements FutureCatenableList<T> {

    private final T value;

    private final Queue<CatenableList<T>> catListQueue;

    public FutureCatList(T value) {
        this.value = value;
        catListQueue = new FutureBankersQueue<>();
    }

    private FutureCatList(T value, Queue<CatenableList<T>> catListQueue) {
        this.value = value;
        this.catListQueue = catListQueue;
    }

    public FutureCatList() {
        value = null;
        catListQueue = null;
    }

    private FutureCatenableList<T> link(CatenableList<T> lazyCatList) {
        return new FutureCatList<>(value, catListQueue.snoc(lazyCatList));
    }

    private CatenableList<T> linkAll(Queue<CatenableList<T>> lazyCatListQueue) {
        CatenableList<T> check = lazyCatListQueue.head();
        if (!(check instanceof FutureCatList)) throw new RuntimeException("invalid FutureCatenableList implementation");
        FutureCatList<T> head = (FutureCatList<T>) check;
        Queue<CatenableList<T>> tail = lazyCatListQueue.tail();
        return tail.isEmpty() ? head : head.linkAll(tail);
    }

    @Override
    public boolean isEmpty() {
        return catListQueue == null;
    }

    @Override
    public CatenableList<T> cons(T value) {
        return new FutureCatList<>(value).concat(this);
    }

    @Override
    public CatenableList<T> snoc(T value) {
        return concat(new FutureCatList<>(value));
    }

    @Override
    public CatenableList<T> concat(CatenableList<T> catenableList) {
            if (isEmpty()) return catenableList;
            if (catenableList == null || catenableList.isEmpty()) return this;
            return link(catenableList);
    }

    @Override
    public T head() {
        if (isEmpty()) throw new RuntimeException("empty list");
        return value;
    }

    @Override
    public CatenableList<T> tail() {
        if (isEmpty()) throw new RuntimeException("empty list");
        if (catListQueue.isEmpty()) return new FutureCatList<>();
        else return linkAll(catListQueue);
    }

    @Override
    public FutureCall<CatenableList<T>> futureTail() {
        if (isEmpty()) throw new RuntimeException("empty list");
        if (catListQueue.isEmpty()) return new FutureCall<>(new FutureCatList<>());
        else return new FutureCall<>(linkAll(catListQueue));
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

    private static final class LazyCatListIterator<T> implements
            Iterator<T> {

        private boolean initial = true;

        private Iterator<CatenableList<T>> futureLazyCatListIterator;

        private Iterator<T> currentLazyCatListIterator;

        private final FutureCatList<T> lazyCatList;

        public LazyCatListIterator(FutureCatList<T> lazyCatList) {
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
                futureLazyCatListIterator = lazyCatList.catListQueue.iterator();
                if (futureLazyCatListIterator.hasNext()) {
                    currentLazyCatListIterator = futureLazyCatListIterator.next().iterator();
                }
                return lazyCatList.value;
            }
            if (currentLazyCatListIterator.hasNext()) {
                return currentLazyCatListIterator.next();
            } else if (futureLazyCatListIterator.hasNext()) {
                currentLazyCatListIterator = futureLazyCatListIterator.next().iterator();
                return currentLazyCatListIterator.next();
            }
            throw new NoSuchElementException();
        }
    }
}
