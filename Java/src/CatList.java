public class CatList<T> implements CatenableList<T>
{
    private T value;

    private Queue<CatList<T>> catListQueue;

    @Override
    public boolean isEmpty()
    {
        return value == null && (catListQueue == null || catListQueue.isEmpty());
    }



    public CatList(T value)
    {
        this.value = value;
        catListQueue = new BatchedQueue<>();
    }

    public CatList(T value, Queue<CatList<T>> catListQueue)
    {
        this.value = value;
        this.catListQueue = catListQueue;
    }

    public CatList()
    {
    }

    public boolean taildone = false;

    @Override
    public CatenableList<T> cons(T value)
    {
        return new CatList<>(value).concat(this);
    }

    @Override
    public CatenableList<T> snoc(T value)
    {
        return concat(new CatList<>(value));
    }

    @Override
    public CatenableList<T> concat(CatenableList<T> catenableList)
    {
        if (isEmpty()) return catenableList;
        if (catenableList == null || catenableList.isEmpty()) return this;
        return link(this, catenableList);
    }

    @Override
    public T head()
    {
        if (isEmpty()) throw new RuntimeException("empty list");
        return value;
    }

    @Override
    public CatenableList<T> tail()
    {
        if (isEmpty()) throw new RuntimeException("empty list");
        if (catListQueue.isEmpty()) return new CatList<>();
        else return linkAll(catListQueue);
    }

    public static <T> CatenableList<T> link(CatList<T> catList1, CatenableList<T> catenableList)
    {
        if (!(catenableList instanceof CatList)) throw new RuntimeException("invalid CatenableList implementation");
        CatList<T> catList = (CatList<T>) catenableList;
        return new CatList<>(catList1.value, catList1.catListQueue.snoc(catList));
    }

    private static <T> CatenableList<T> linkAll(Queue<CatList<T>> q)
    {
        Queue<CatList<T>> tail = q.tail();
        if (tail.isEmpty()) return q.head();
        else return link(q.head(), linkAll(tail));
    }

    @Override
    public String toString()
    {
        if (isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        CatenableList<T> tail = tail();
        sb.append(head());
        if (tail.isEmpty()) return sb.toString();
        tail.toStringEfficient(sb);
        return sb.toString();
    }

    @Override
    public void toStringEfficient(StringBuilder sb)
    {
        if (isEmpty()) return;
        CatenableList<T> tail = tail();
        if (tail.isEmpty()) {
            sb.append(head());
        }
        else {
            sb.append(head());
            tail.toStringEfficient(sb);
        }
    }

    @Override
    public void printWithoutConcat()
    {
        if (isEmpty()) return;
        System.out.print(head());
        CatenableList<T> tail = tail();
        if (tail.isEmpty()) return;
        System.out.print("\n");
        tail.printWithoutConcat();
    }

}
