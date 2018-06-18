package NonLazy;

import Interfaces.List;

public class PersistentList<T> implements List<T>
{
    private int size;

    private T head;

    private PersistentList<T> tail;

    public PersistentList(T head, PersistentList<T> tail) {
        if (head == null && (tail == null || tail.isEmpty())) {
            // empty list
        }
        else if (head == null) {
            // cannot add null head so set this to tail
            size = tail.size;
            this.head = tail.head;
            this.tail = tail.tail;
        }
        else if (tail == null) {
            // head + empty tail
            size = 1;
            this.head = head;
            this.tail = new PersistentList<>();
        }
        else {
            // normal case
            size = 1 + tail.size;
            this.head = head;
            this.tail = tail;
        }
    }

    public PersistentList() {
        // empty list
    }

    @Override
    public boolean isEmpty() {
        return head == null && tail == null;
    }

    @Override
    public List<T> prepend(T value) {
        return new PersistentList<>(value, this);
    }

    @Override
    public List<T> concat(List<T> list)
    {
        List<T> currentList = reverse();
        while (!currentList.isEmpty()) {
            list = list.prepend(currentList.head());
            currentList = currentList.tail();
        }
        return list;
    }

    @Override
    public List<T> reverse() {
        PersistentList<T> org = this;
        List<T> rev = new PersistentList<>();
        while (org.tail != null) {
            rev = rev.prepend(org.head);
            org = org.tail;
        }
        return rev;
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
    public String toString()
    {
        if (head == null) return "";
        if (tail == null || tail.isEmpty()) return head.toString();
        return head + ",\n" + tail;
    }
}
