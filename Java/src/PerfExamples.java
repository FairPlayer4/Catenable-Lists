public class PerfExamples
{
    public static String string1 = new String(new char[10000]).replace('\0', '1');
    public static CatenableList<String> l1 = new CatList<>(string1);
    public static String string2 = new String(new char[10000]).replace('\0', '2');
    public static CatenableList<String> l2 = new CatList<>(string2);
    public static String string3 = new String(new char[10000]).replace('\0', '3');
    public static CatenableList<String> l3 = new CatList<>(string3);
    public static String string4 = new String(new char[10000]).replace('\0', '4');
    public static CatenableList<String> l4 = new CatList<>(string4);
    public static String string5 = new String(new char[10000]).replace('\0', '5');
    public static CatenableList<String> l5 = new CatList<>(string5);
    public static String string6 = new String(new char[10000]).replace('\0', '6');
    public static CatenableList<String> l6 = new CatList<>(string6);
    public static String string7 = new String(new char[10000]).replace('\0', '7');
    public static CatenableList<String> l7 = new CatList<>(string7);
    public static String string8 = new String(new char[10000]).replace('\0', '8');
    public static CatenableList<String> l8 = new CatList<>(string8);
    public static String string9 = new String(new char[10000]).replace('\0', '9');
    public static CatenableList<String> l9 = new CatList<>(string9);

    public static class ResultPerf<T>
    {
        public final long Runtime;
        public final CatenableList<T> CatList;

        public ResultPerf(long runtime, CatenableList<T> catList)
        {
            Runtime = runtime;
            CatList = catList;
            System.out.println("Cat List concatenation took: " + runtime + "ms");
        }
    }

    public static class ResultPerfString<T>
    {
        public final long Runtime;
        public final T ResultString;

        public ResultPerfString(long runtime, T resultString)
        {
            Runtime = runtime;
            ResultString = resultString;
            System.out.println("String concatenation took: " + runtime + "ms");
        }
    }

    public static ResultPerf<String> RunConcat(CatenableList<String> first, CatenableList<String> second) {
        long t1 = System.currentTimeMillis();
        CatenableList<String> resultList = first.concat(second);
        long t2 = System.currentTimeMillis();
        return new ResultPerf<>(t2 - t1, resultList);
    }

    public static ResultPerf<String> RunCatenableList(int times)
    {
        long t1 = System.currentTimeMillis();
        CatenableList<String> catList = new CatList<>();
        for (int i = 0; i < times; i++) {
            catList = catList.concat(l1).concat(l2).concat(l3).concat(l4).concat(l5).concat(l6).concat(l7).concat(l8)
                             .concat(l9);
        }
        long t2 = System.currentTimeMillis();
        return new ResultPerf<>(t2 - t1, catList);
    }

    public static ResultPerfString<StringBuilder> RunStringBuilderConcat(StringBuilder sb1, StringBuilder sb2) {
        long t1 = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder(sb1); // Persistence
        sb.append(sb2);
        long t2 = System.currentTimeMillis();
        return new ResultPerfString<>(t2 - t1, sb);
    }

    public static ResultPerfString<StringBuilder> RunStringBuilderConcatenation(int times)
    {
        long t1 = System.currentTimeMillis();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < times; i++) {
            stringBuilder.append(string1);
            stringBuilder.append(string2);
            stringBuilder.append(string3);
            stringBuilder.append(string4);
            stringBuilder.append(string5);
            stringBuilder.append(string6);
            stringBuilder.append(string7);
            stringBuilder.append(string8);
            stringBuilder.append(string9);
        }
        long t2 = System.currentTimeMillis();
        return new ResultPerfString<>(t2 - t1, stringBuilder);
    }

    public static ResultPerfString<String> RunBadStringConcat(String s1, String s2) {
        long t1 = System.currentTimeMillis();
        String resultString = s1 + s2;
        long t2 = System.currentTimeMillis();
        return new ResultPerfString<>(t2 - t1, resultString);
    }

    public static ResultPerfString<String> RunBadStringConcatenation(int times)
    {
        long t1 = System.currentTimeMillis();
        String resultString = "";
        for (int i = 0; i < times; i++) {
            resultString += string1 + string2 + string3 + string4 + string5 + string6 + string7 + string8 + string9;
        }
        long t2 = System.currentTimeMillis();
        return new ResultPerfString<>(t2 - t1, resultString);
    }
}
