public class CatList<T> implements CatenableList<T>
{
    private T value;

    private Queue<CatList<T>> catListQueue;

    @Override
    public boolean isEmpty()
    {
        return value == null && (catListQueue == null || catListQueue.isEmpty());
    }

    public static <T> CatenableList<T> link(CatList<T> list, CatenableList<T> list2)
    {
        return new CatList<>(list.value, list.catListQueue.snoc(((CatList<T>) list2)));
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
    public CatenableList<T> concat(CatenableList<T> list)
    {
        if (isEmpty()) return list;
        if (list == null || list.isEmpty()) return this;
        return link(this, list);
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

    private static <T> CatenableList<T> linkAll(Queue<CatList<T>> q)
    {
        if (q.tail().isEmpty()) return q.head();
        else return link(q.head(), linkAll(q.tail()));
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

    public static void main(String[] args)
    {
        long totalRuntimeFirst = 0;
        long totalRuntimeSecond = 0;
        long totalRuntimeThird = 0;
        long firstRuntime = 0;
        long secondRuntime = 0;
        for (int i = 0; i < 10; i++) {
            PerfExamples.ResultPerf<String> test1 = PerfExamples.RunCatenableList(100);
            PerfExamples.ResultPerf<String> test2 = PerfExamples.RunCatenableList(100);
            PerfExamples.ResultPerf<String> test3 = PerfExamples.RunCatenableList(100);
            PerfExamples.ResultPerf<String> test4 = PerfExamples.RunCatenableList(100);
            PerfExamples.ResultPerf<String> test5 = PerfExamples.RunConcat(test1.CatList, test2.CatList);
            PerfExamples.ResultPerf<String> test6 = PerfExamples.RunConcat(test3.CatList, test4.CatList);
            PerfExamples.ResultPerf<String> test7 = PerfExamples.RunConcat(test5.CatList, test6.CatList);
            totalRuntimeFirst += (test1.Runtime + test2.Runtime + test3.Runtime + test4.Runtime + test5.Runtime + test6.Runtime + test7.Runtime);
            System.out.println(
                    "Total Cat List Runtime: " + (test1.Runtime + test2.Runtime + test3.Runtime + test4.Runtime + test5.Runtime + test6.Runtime + test7.Runtime) + "ms");

            PerfExamples.ResultPerfString<StringBuilder> testsb1 = PerfExamples.RunStringBuilderConcatenation(100);
            PerfExamples.ResultPerfString<StringBuilder> testsb2 = PerfExamples.RunStringBuilderConcatenation(100);
            PerfExamples.ResultPerfString<StringBuilder> testsb3 = PerfExamples.RunStringBuilderConcatenation(100);
            PerfExamples.ResultPerfString<StringBuilder> testsb4 = PerfExamples.RunStringBuilderConcatenation(100);
            PerfExamples.ResultPerfString<StringBuilder> testsb5 = PerfExamples
                    .RunStringBuilderConcat(testsb1.ResultString, testsb2.ResultString);
            PerfExamples.ResultPerfString<StringBuilder> testsb6 = PerfExamples
                    .RunStringBuilderConcat(testsb3.ResultString, testsb4.ResultString);
            PerfExamples.ResultPerfString<StringBuilder> testsb7 = PerfExamples
                    .RunStringBuilderConcat(testsb5.ResultString, testsb6.ResultString);
            totalRuntimeSecond += (testsb1.Runtime + testsb2.Runtime + testsb3.Runtime + testsb4.Runtime + testsb5.Runtime + testsb6.Runtime + testsb7.Runtime);
            System.out.println(
                    "Total StringBuilder Concatenation Runtime: " + (testsb1.Runtime + testsb2.Runtime + testsb3.Runtime + testsb4.Runtime + testsb5.Runtime + testsb6.Runtime + testsb7.Runtime) + "ms");

//            PerfExamples.ResultPerfString<String> tests1 = PerfExamples.RunBadStringConcatenation(100);
//            PerfExamples.ResultPerfString<String> tests2 = PerfExamples.RunBadStringConcatenation(100);
//            PerfExamples.ResultPerfString<String> tests3 = PerfExamples.RunBadStringConcatenation(100);
//            PerfExamples.ResultPerfString<String> tests4 = PerfExamples.RunBadStringConcatenation(100);
//            PerfExamples.ResultPerfString<String> tests5 = PerfExamples
//                    .RunBadStringConcat(tests1.ResultString, tests2.ResultString);
//            PerfExamples.ResultPerfString<String> tests6 = PerfExamples
//                    .RunBadStringConcat(tests3.ResultString, tests4.ResultString);
//            PerfExamples.ResultPerfString<String> tests7 = PerfExamples
//                    .RunBadStringConcat(tests5.ResultString, tests6.ResultString);
//            totalRuntimeThird += (tests1.Runtime + tests2.Runtime + tests3.Runtime + tests4.Runtime + tests5.Runtime + tests6.Runtime + tests7.Runtime);
//            System.out.println(
//                    "Total String Concatenation Runtime: " + (tests1.Runtime + tests2.Runtime + tests3.Runtime + tests4.Runtime + tests5.Runtime + tests6.Runtime + tests7.Runtime) + "ms");

            long t1 = System.currentTimeMillis();
            String testString = test7.CatList.toString();
            long t2 = System.currentTimeMillis();
            String testsbString = testsb7.ResultString.toString();
            long t3 = System.currentTimeMillis();
            firstRuntime += t2 - t1;
            secondRuntime += t3 - t2;
        }
        System.out.println("First Runtime: " + (totalRuntimeFirst) + "ms");
        System.out.println("Second Runtime: " + (totalRuntimeSecond) + "ms");
        System.out.println("String creation runtime first: " + firstRuntime + "ms");
        System.out.println("String creation runtime second: " + secondRuntime + "ms");
        System.out.println("First Total Runtime: " + (totalRuntimeFirst + firstRuntime) + "ms");
        System.out.println("Second Total Runtime: " + (totalRuntimeSecond + secondRuntime) + "ms");
        System.out.println("Third Total Runtime: " + (totalRuntimeThird) + "ms");
    }

}
