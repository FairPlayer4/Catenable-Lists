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
        catListQueue = new BatchedQueue<>();
    }

    private CatList(T value, Queue<CatList<T>> catListQueue) {
        this.value = value;
        this.catListQueue = catListQueue;
    }

    public CatList() {
    }

    private static <T> CatList<T> link(CatList<T> catList1, CatList<T> catList2) {
        return new CatList<>(catList1.value, catList1.catListQueue.snoc(catList2));
    }

    private static <T> CatList<T> linkAll(Queue<CatList<T>> catListQueue) {
        Queue<CatList<T>> tail = catListQueue.tail();
        if (tail.isEmpty()) return catListQueue.head();
        else return link(catListQueue.head(), linkAll(tail));
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
        return link(this, (CatList<T>) catenableList);
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
        //sb.append("[");
        for (T value : this) {
            //System.out.println(value);
            sb.append(value);
            //sb.append(", ");
        }
        //int i = sb.lastIndexOf(", ");
        //if (i != -1) {
        //    sb.deleteCharAt(i);
        //    sb.deleteCharAt(i - 1);
        //}
        //sb.append("]");
        return sb.toString();
    }

    private int stringSize = -1;

    public int getStringSize() {
        if (stringSize == -1) {
            stringSize = 0;//2;
            for (T value : this) {
                stringSize += value.toString().length();// + 2;
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
