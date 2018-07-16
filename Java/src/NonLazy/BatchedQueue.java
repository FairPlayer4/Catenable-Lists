package NonLazy;

import Interfaces.List;
import Interfaces.Queue;

import java.util.Iterator;

public class BatchedQueue<T> implements Queue<T> {

    private final List<T> front;

    private final List<T> rear;

    private BatchedQueue(List<T> front, List<T> rear) {
        this.front = front == null ? new PersistentList<>() : front;
        this.rear = rear == null ? new PersistentList<>() : rear;
    }

    public BatchedQueue() {
        front = new PersistentList<>();
        rear = new PersistentList<>();
    }

    private Queue<T> check(List<T> front, List<T> rear) {
        if (front == null || front.isEmpty()) return new BatchedQueue<>(rear.reverse(), null);
        return new BatchedQueue<>(front, rear);
    }

    @Override
    public Queue<T> snoc(T value) {
        return check(front, rear.prepend(value));
    }

    @Override
    public boolean isEmpty() {
        return front.isEmpty();
    }

    @Override
    public T head() {
        if (front.isEmpty()) throw new RuntimeException("empty queue");
        return front.head();
    }

    @Override
    public Queue<T> tail() {
        if (front.isEmpty()) throw new RuntimeException("empty queue");
        return check(front.tail(), rear);
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

        private boolean iterateFront = true;

        private Iterator<T> listIterator;

        BatchedQueueIterator(BatchedQueue<T> batchedQueue) {
            this.batchedQueue = batchedQueue;
            if (batchedQueue != null && !batchedQueue.isEmpty()) {
                listIterator = batchedQueue.front.iterator();
            }
        }

        @Override
        public boolean hasNext() {
            if (batchedQueue != null && !batchedQueue.isEmpty() && listIterator != null) {
                if (listIterator.hasNext()) return true;
                else if (iterateFront) {
                    iterateFront = false;
                    listIterator = batchedQueue.rear.reverse().iterator();
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
