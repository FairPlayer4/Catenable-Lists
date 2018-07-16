package NonLazy;

import Interfaces.CatenableList;
import Interfaces.Queue;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @param <T>
 */
public class CatList<T> implements CatenableList<T> {
    private T value;

    private Queue<CatList<T>> catListQueue;

    public CatList(T value) {
        this.value = value;
        //Using BatchedQueue here but any other Queue implementation can be used
        catListQueue = new BatchedQueue<>();
    }

    private CatList(T value, Queue<CatList<T>> catListQueue) {
        this.value = value;
        this.catListQueue = catListQueue;
    }

    public CatList() {
    }

    private CatList<T> link(CatList<T> catList) {
        return new CatList<>(value, catListQueue.snoc(catList));
    }

    private CatList<T> linkAll(Queue<CatList<T>> catListQueue) {
        Queue<CatList<T>> tail = catListQueue.tail();
        if (tail.isEmpty()) return catListQueue.head();
        else return catListQueue.head().link(linkAll(tail));
    }

    @Override
    public boolean isEmpty() {
        return catListQueue == null;
    }

    @Override
    public CatenableList<T> cons(T value) {
        return new CatList<>(value).concat(this);
    }

    @Override
    public CatenableList<T> snoc(T value) {
        return concat(new CatList<>(value));
    }

    @Override
    public CatenableList<T> concat(CatenableList<T> catenableList) {
        if (isEmpty()) return catenableList;
        if (catenableList == null || catenableList.isEmpty()) return this;
        if (!(catenableList instanceof CatList)) throw new RuntimeException("invalid CatenableList implementation");
        return link((CatList<T>) catenableList);
    }

    @Override
    public T head() {
        if (isEmpty()) throw new RuntimeException("empty list");
        return value;
    }

    @Override
    public CatenableList<T> tail() {
        if (isEmpty()) throw new RuntimeException("empty list");
        if (catListQueue.isEmpty()) return new CatList<>();
        else return linkAll(catListQueue);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(getStringSize() + 1);
        for (T value : this) {
            sb.append(value);
        }
        return sb.toString();
    }

    private int stringSize = -1;

    public int getStringSize() {
        if (stringSize == -1) {
            stringSize = 0;
            for (T value : this) {
                stringSize += value.toString().length();
            }
        }
        return stringSize;
    }

    @Override
    public Iterator<T> iterator() {
        return new CatListIterator<>(this);
    }

    private static final class CatListIterator<T> implements
            Iterator<T> {

        private boolean initial = true;

        private Iterator<CatList<T>> queueIterator;

        private Iterator<T> currentSubIterator;

        private CatList<T> catList;

        CatListIterator(CatList<T> catList) {
            this.catList = catList;
        }

        @Override
        public boolean hasNext() {
            if (catList == null || catList.isEmpty()) return false;
            if (initial) return true;
            if (queueIterator == null || currentSubIterator == null) return false;
            return queueIterator.hasNext() || currentSubIterator.hasNext();
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            if (initial) {
                initial = false;
                queueIterator = catList.catListQueue.iterator();
                if (queueIterator.hasNext()) {
                    currentSubIterator = queueIterator.next().iterator();
                }
                return catList.value;
            }
            if (currentSubIterator.hasNext()) {
                return currentSubIterator.next();
            } else if (queueIterator.hasNext()) {
                currentSubIterator = queueIterator.next().iterator();
                if (currentSubIterator.hasNext()) {
                    return currentSubIterator.next();
                }
            }
            throw new NoSuchElementException();
        }
    }
}
