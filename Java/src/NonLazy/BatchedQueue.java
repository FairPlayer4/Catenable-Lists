package NonLazy;

import Interfaces.List;
import Interfaces.Queue;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class BatchedQueue<T> implements Queue<T> {

    private final List<T> firstList;

    private final List<T> secondList;

    private BatchedQueue(List<T> firstList, List<T> secondList) {
        this.firstList = firstList == null ? new PersistentList<>() : firstList;
        this.secondList = secondList == null ? new PersistentList<>() : secondList;
    }

    public BatchedQueue() {
        firstList = new PersistentList<>();
        secondList = new PersistentList<>();
    }

    private static <T> Queue<T> check(List<T> firstList, List<T> secondList) {
        if (firstList == null || firstList.isEmpty()) return new BatchedQueue<>(secondList.reverse(), null);
        return new BatchedQueue<>(firstList, secondList);
    }

    @Override
    public Queue<T> snoc(T value) {
        return check(firstList, secondList.prepend(value));
    }

    @Override
    public boolean isEmpty() {
        return firstList.isEmpty();
    }

    @Override
    public T head() {
        if (firstList.isEmpty()) throw new RuntimeException("empty queue");
        return firstList.head();
    }

    @Override
    public Queue<T> tail() {
        if (firstList.isEmpty()) throw new RuntimeException("empty queue");
        return check(firstList.tail(), secondList);
    }

    @Override
    public String toString() {
        if (isEmpty()) return "";
        Queue<T> tail = tail();
        if (tail.isEmpty()) return head().toString();
        return head() + "\n" + tail();
    }

    @Override
    public Iterator<T> iterator() {
        return new BatchedQueueIterator<>(this);
    }


    private static final class BatchedQueueIterator<T> implements Iterator<T> {

        private BatchedQueue<T> batchedQueue;

        private boolean firstList = true;

        private Iterator<T> listIterator;

        BatchedQueueIterator(BatchedQueue<T> batchedQueue) {
            this.batchedQueue = batchedQueue;
            if (batchedQueue != null && !batchedQueue.isEmpty()) {
                listIterator = batchedQueue.firstList.iterator();
            }
        }

        @Override
        public boolean hasNext() {
            if (batchedQueue != null && !batchedQueue.isEmpty() && listIterator != null) {
                if (listIterator.hasNext()) return true;
                else if (firstList) {
                    firstList = false;
                    listIterator = batchedQueue.secondList.reverse().iterator();
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
