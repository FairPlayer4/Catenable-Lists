package Lazy;

import Interfaces.LazyList;
import Util.LazyCall;

import java.util.Iterator;
import java.util.NoSuchElementException;

//TODO make concat and reverse lazy
public class LazyPersistentList<T extends LazyCall<?>> implements LazyList<T> {
    private int size;

    private T head;

    private LazyCall<LazyList<T>> tail;

    private LazyPersistentList(T head, LazyPersistentList<T> tail) {
        if (head == null && (tail == null || tail.isEmpty())) {
            // empty list
        } else if (head == null) {
            // cannot add null head so set this to tail
            size = tail.size;
            this.head = tail.head;
            this.tail = tail.tail;
        } else if (tail == null) {
            // head + empty tail
            size = 1;
            this.head = head;
            this.tail = new LazyCall<>(new LazyPersistentList<>());
        } else {
            // normal case
            size = 1 + tail.size;
            this.head = head;
            this.tail = new LazyCall<>(tail);
        }
    }

    public LazyPersistentList() {
        // empty list
    }

    @Override
    public boolean isEmpty() {
        return head == null && tail == null;
    }

    @Override
    public LazyCall<LazyList<T>> prepend(T value) {

        return new LazyCall<>(new LazyPersistentList<>(value, this));
    }

    @Override
    public LazyCall<LazyList<T>> concat(LazyList<T> list) {
        return new LazyCall<>(() -> {
            LazyList<T> currentList = list;
            for (T value : reverse().get()) {
                currentList = currentList.prepend(value).get();
            }
            return currentList;
        });
    }

    @Override
    public LazyCall<LazyList<T>> reverse() {
        return new LazyCall<>(() -> {
            LazyList<T> rev = new LazyPersistentList<>();
            for (T value : this) {
                rev = rev.prepend(value).get();
            }
            return rev;
        });
    }

    @Override
    public T head() {
        if (head == null) throw new RuntimeException("empty list");
        return head;
    }

    @Override
    public LazyCall<LazyList<T>> tail() {
        if (tail == null) throw new RuntimeException("empty list");
        return tail;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(size);
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
        return new ListIterator<>(this);
    }

    private static final class ListIterator<T extends LazyCall<?>> implements Iterator<T> {

        private LazyList<T> persistentList;

        ListIterator(LazyList<T> persistentList) {
            this.persistentList = persistentList;
        }

        @Override
        public boolean hasNext() {
            return persistentList != null && !persistentList.isEmpty();
        }

        @Override
        public T next() {
            if (!hasNext()) throw new NoSuchElementException();
            T value = persistentList.head();
            persistentList = persistentList.tail().get();
            return value;
        }
    }
}
