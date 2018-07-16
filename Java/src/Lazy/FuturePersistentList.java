package Lazy;

import Interfaces.FutureList;
import Interfaces.List;
import Util.FutureCall;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class FuturePersistentList<T> implements FutureList<T> {
    private int size;

    private T head;

    private List<T> tail;

    private FuturePersistentList(T head, FuturePersistentList<T> tail) {
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
            this.tail = new FuturePersistentList<>();
        } else {
            // normal case
            size = 1 + tail.size;
            this.head = head;
            this.tail = tail;
        }
    }

    public FuturePersistentList() {
        // empty list
    }

    @Override
    public boolean isEmpty() {
        return head == null && tail == null;
    }

    @Override
    public FutureList<T> prepend(T value) {

        return new FuturePersistentList<>(value, this);
    }

    @Override
    public FutureCall<List<T>> futureConcat(List<T> list) {
        return new FutureCall<>(() -> {
            List<T> currentList = list;
            for (T value : reverse()) {
                currentList = currentList.prepend(value);
            }
            return currentList;
        });
    }

    @Override
    public FutureCall<List<T>> futureReverse() {
        return new FutureCall<>(() -> {
            List<T> reversedList = new FuturePersistentList<>();
            for (T value : this) {
                reversedList = reversedList.prepend(value);
            }
            return reversedList;
        });
    }

    @Override
    public List<T> concat(List<T> list)
    {
        for (T value : reverse()) {
            list = list.prepend(value);
        }
        return list;
    }

    @Override
    public List<T> reverse() {
        List<T> reversedList = new FuturePersistentList<>();
        for (T value : this) {
            reversedList = reversedList.prepend(value);
        }
        return reversedList;
    }

    @Override
    public T head() {
        if (head == null) throw new RuntimeException("empty list");
        return head;
    }

    @Override
    public List<T> tail() {
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
            sb.deleteCharAt(i - 1);
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public Iterator<T> iterator() {
        return new ListIterator<>(this);
    }

    private static final class ListIterator<T> implements Iterator<T> {

        private List<T> persistentList;

        ListIterator(List<T> persistentList) {
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
            persistentList = persistentList.tail();
            return value;
        }
    }
}
