package NonLazy;

import Interfaces.CatenableList;
import Interfaces.Queue;

/**
 * @param <T>
 */
public class CatList<T> implements CatenableList<T> {
    private T value;

    private Queue<CatList<T>> catListQueue;

    public CatList(T value) {
        this.value = value;
        catListQueue = new BankersQueue<>();
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
    public void toStringEfficient(StringBuilder sb) {
        if (isEmpty()) return;
        sb.append(head());
        Queue<CatList<T>> r = catListQueue;
        while (!r.isEmpty()) {
            r.head().toStringEfficient(sb);
            r = r.tail();
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
        Queue<CatList<T>> r = catListQueue;
        while (!r.isEmpty()) {
            r.head().toStringEfficient(sb);
            r = r.tail();
        }
        return sb.toString();
    }

    private int stringSize = -1;

    public int getStringSize() {
        if (stringSize == -1) {
            if (isEmpty()) stringSize = 0;
            else {
                int result = value == null ? 0 : value.toString().length();
                Queue<CatList<T>> r = catListQueue;
                while (!r.isEmpty()) {
                    result += r.head().getStringSize();
                    r = r.tail();
                }
                stringSize = result;
            }
        }
        return stringSize;
    }

}
