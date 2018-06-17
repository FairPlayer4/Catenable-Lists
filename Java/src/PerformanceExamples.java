public class PerformanceExamples
{

    private static final int charNumber = 10000;

    private static final String string1 = new String(new char[charNumber]).replace('\0', '1');
    private static final CatenableList<String> l1 = new CatList<>(string1);
    private static final String string2 = new String(new char[charNumber]).replace('\0', '2');
    private static final CatenableList<String> l2 = new CatList<>(string2);
    private static final String string3 = new String(new char[charNumber]).replace('\0', '3');
    private static final CatenableList<String> l3 = new CatList<>(string3);
    private static final String string4 = new String(new char[charNumber]).replace('\0', '4');
    private static final CatenableList<String> l4 = new CatList<>(string4);
    private static final String string5 = new String(new char[charNumber]).replace('\0', '5');
    private static final CatenableList<String> l5 = new CatList<>(string5);
    private static final String string6 = new String(new char[charNumber]).replace('\0', '6');
    private static final CatenableList<String> l6 = new CatList<>(string6);
    private static final String string7 = new String(new char[charNumber]).replace('\0', '7');
    private static final CatenableList<String> l7 = new CatList<>(string7);
    private static final String string8 = new String(new char[charNumber]).replace('\0', '8');
    private static final CatenableList<String> l8 = new CatList<>(string8);
    private static final String string9 = new String(new char[charNumber]).replace('\0', '9');
    private static final CatenableList<String> l9 = new CatList<>(string9);

    public static void main(String[] args)
    {
        long totalRuntimeFirst = 0;
        long totalRuntimeSecond = 0;
        long totalRuntimeThird = 0;
        long firstRuntime = 0;
        long secondRuntime = 0;
        for (int i = 0; i < 10; i++) {
            ResultPerf<String> test1 = RunCatenableList(100);
            ResultPerf<String> test2 = RunCatenableList(100);
            ResultPerf<String> test3 = RunCatenableList(100);
            ResultPerf<String> test4 = RunCatenableList(100);
            ResultPerf<String> test5 = RunConcat(test1.CatList, test2.CatList);
            ResultPerf<String> test6 = RunConcat(test3.CatList, test4.CatList);
            ResultPerf<String> test7 = RunConcat(test5.CatList, test6.CatList);
            totalRuntimeFirst += (test1.Runtime + test2.Runtime + test3.Runtime + test4.Runtime + test5.Runtime + test6.Runtime + test7.Runtime);
            System.out.println(
                    "Total Cat List Runtime: " + (test1.Runtime + test2.Runtime + test3.Runtime + test4.Runtime + test5.Runtime + test6.Runtime + test7.Runtime) + "ms");

            ResultPerfString<StringBuilder> testsb1 = PerformanceExamples
                    .RunStringBuilderConcatenation(100);
            ResultPerfString<StringBuilder> testsb2 = PerformanceExamples
                    .RunStringBuilderConcatenation(100);
            ResultPerfString<StringBuilder> testsb3 = PerformanceExamples
                    .RunStringBuilderConcatenation(100);
            ResultPerfString<StringBuilder> testsb4 = PerformanceExamples
                    .RunStringBuilderConcatenation(100);
            ResultPerfString<StringBuilder> testsb5 = PerformanceExamples
                    .RunStringBuilderConcat(testsb1.ResultString, testsb2.ResultString);
            ResultPerfString<StringBuilder> testsb6 = PerformanceExamples
                    .RunStringBuilderConcat(testsb3.ResultString, testsb4.ResultString);
            ResultPerfString<StringBuilder> testsb7 = PerformanceExamples
                    .RunStringBuilderConcat(testsb5.ResultString, testsb6.ResultString);
            totalRuntimeSecond += (testsb1.Runtime + testsb2.Runtime + testsb3.Runtime + testsb4.Runtime + testsb5.Runtime + testsb6.Runtime + testsb7.Runtime);
            System.out.println(
                    "Total StringBuilder Concatenation Runtime: " + (testsb1.Runtime + testsb2.Runtime + testsb3.Runtime + testsb4.Runtime + testsb5.Runtime + testsb6.Runtime + testsb7.Runtime) + "ms");

//            ResultPerfString<String> tests1 = RunBadStringConcatenation(100);
//            ResultPerfString<String> tests2 = RunBadStringConcatenation(100);
//            ResultPerfString<String> tests3 = RunBadStringConcatenation(100);
//            ResultPerfString<String> tests4 = RunBadStringConcatenation(100);
//            ResultPerfString<String> tests5 = PerformanceExamples
//                    .RunBadStringConcat(tests1.ResultString, tests2.ResultString);
//            ResultPerfString<String> tests6 = PerformanceExamples
//                    .RunBadStringConcat(tests3.ResultString, tests4.ResultString);
//            ResultPerfString<String> tests7 = PerformanceExamples
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
    

    private static class ResultPerf<T>
    {
        final long Runtime;
        final CatenableList<T> CatList;

        ResultPerf(long runtime, CatenableList<T> catList)
        {
            Runtime = runtime;
            CatList = catList;
            System.out.println("Cat List concatenation took: " + runtime + "ms");
        }
    }

    private static class ResultPerfString<T>
    {
        final long Runtime;
        final T ResultString;

        ResultPerfString(long runtime, T resultString)
        {
            Runtime = runtime;
            ResultString = resultString;
            System.out.println("String concatenation took: " + runtime + "ms");
        }
    }

    private static ResultPerf<String> RunConcat(CatenableList<String> first, CatenableList<String> second) {
        long t1 = System.currentTimeMillis();
        CatenableList<String> resultList = first.concat(second);
        long t2 = System.currentTimeMillis();
        return new ResultPerf<>(t2 - t1, resultList);
    }

    private static ResultPerf<String> RunCatenableList(int times)
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

    private static ResultPerfString<StringBuilder> RunStringBuilderConcat(StringBuilder sb1, StringBuilder sb2) {
        long t1 = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder(sb1); // Persistence
        sb.append(sb2);
        long t2 = System.currentTimeMillis();
        return new ResultPerfString<>(t2 - t1, sb);
    }

    private static ResultPerfString<StringBuilder> RunStringBuilderConcatenation(int times)
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

    private static ResultPerfString<String> RunBadStringConcat(String s1, String s2) {
        long t1 = System.currentTimeMillis();
        String resultString = s1 + s2;
        long t2 = System.currentTimeMillis();
        return new ResultPerfString<>(t2 - t1, resultString);
    }

    private static ResultPerfString<String> RunBadStringConcatenation(int times)
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
