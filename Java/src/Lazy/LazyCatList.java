package Lazy;

import Interfaces.CatenableList;
import Interfaces.Queue;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class LazyCatList<T> implements CatenableList<T> {

    private static ExecutorService executor
            = Executors.newCachedThreadPool();

    private T value;

    private Queue<Future<LazyCatList<T>>> catListQueue;

    public LazyCatList(T value) {
        this.value = value;
        catListQueue = new LazyBankersQueue<>();
    }

    public LazyCatList(T value, Queue<Future<LazyCatList<T>>> catListQueue) {
        this.value = value;
        this.catListQueue = catListQueue;
    }

    public LazyCatList() {
    }

    public static <T> LazyCatList<T> link(LazyCatList<T> catList1, Future<LazyCatList<T>> catList2) {
        return new LazyCatList<>(catList1.value, catList1.catListQueue.snoc(catList2));
    }

    private static <T> LazyCatList<T> linkAll(Queue<Future<LazyCatList<T>>> q) {
        Queue<Future<LazyCatList<T>>> tail = q.tail();
        try {
            LazyCatList<T> head = q.head().get();
            if (tail.isEmpty()) return head;
            else return link(head, executor.submit(() -> linkAll(tail)));
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return new LazyCatList<>();
    }

    @Override
    public boolean isEmpty() {
        return catListQueue == null;
    }

    @Override
    public CatenableList<T> cons(T value) {
        return new LazyCatList<>(value).concat(this);
    }

    @Override
    public CatenableList<T> snoc(T value) {
        return concat(new LazyCatList<>(value));
    }

    @Override
    public CatenableList<T> concat(CatenableList<T> catenableList) {
        if (isEmpty()) return catenableList;
        if (catenableList == null || catenableList.isEmpty()) return this;
        if (!(catenableList instanceof LazyCatList))
            throw new RuntimeException("invalid Interfaces.CatenableList implementation");
        return link(this, executor.submit(() -> (LazyCatList<T>) catenableList));
    }

    @Override
    public T head() {
        if (isEmpty()) throw new RuntimeException("empty list");
        return value;
    }

    @Override
    public CatenableList<T> tail() {
        if (isEmpty()) throw new RuntimeException("empty list");
        if (catListQueue.isEmpty()) return new LazyCatList<>();
        else return linkAll(catListQueue);
    }

    @Override
    public void toStringEfficient(StringBuilder sb) {
        if (isEmpty()) return;
        sb.append(head());
        Queue<Future<LazyCatList<T>>> r = catListQueue;
        try {
            while (!r.isEmpty()) {
                r.head().get().toStringEfficient(sb);
                r = r.tail();
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void printWithoutConcat() {
        if (isEmpty()) return;
        System.out.print(head());
        CatenableList<T> tail = tail();
        if (tail.isEmpty()) return;
        System.out.print("\n");
        tail.printWithoutConcat();
    }

    @Override
    public String toString() {
        if (isEmpty()) return "";
        StringBuilder sb = new StringBuilder(getStringSize());
        sb.append(head());
        Queue<Future<LazyCatList<T>>> r = catListQueue;
        try {
            while (!r.isEmpty()) {
                r.head().get().toStringEfficient(sb);
                r = r.tail();
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    private int stringSize = -1;

    public int getStringSize() {
        if (stringSize == -1) {
            if (isEmpty()) stringSize = 0;
            else {
                int result = value == null ? 0 : value.toString().length();
                Queue<Future<LazyCatList<T>>> r = catListQueue;
                try {
                    while (!r.isEmpty()) {
                        result += r.head().get().getStringSize();
                        r = r.tail();
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
                stringSize = result;
            }
        }
        return stringSize;
    }
}
