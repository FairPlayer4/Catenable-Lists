package Examples;

import Interfaces.CatenableList;
import Interfaces.FutureCatenableList;
import Lazy.FutureCatList;
import Util.FutureCall;

public class LazyPerformanceExamples {
    /*private static final int charNumber = 100;

    private static final int numberOfRuns = 100;

    private static final String string1 = new String(new char[charNumber]).replace('\0', '1');
    private static final FutureCatenableList<FutureCall<String>> l1 = new FutureCatList<>(new FutureCall<>(string1));
    private static final String string2 = new String(new char[charNumber]).replace('\0', '2');
    private static final FutureCatenableList<FutureCall<String>> l2 = new FutureCatList<>(new FutureCall<>(string2));
    private static final String string3 = new String(new char[charNumber]).replace('\0', '3');
    private static final FutureCatenableList<FutureCall<String>> l3 = new FutureCatList<>(new FutureCall<>(string3));
    private static final String string4 = new String(new char[charNumber]).replace('\0', '4');
    private static final FutureCatenableList<FutureCall<String>> l4 = new FutureCatList<>(new FutureCall<>(string4));
    private static final String string5 = new String(new char[charNumber]).replace('\0', '5');
    private static final FutureCatenableList<FutureCall<String>> l5 = new FutureCatList<>(new FutureCall<>(string5));
    private static final String string6 = new String(new char[charNumber]).replace('\0', '6');
    private static final FutureCatenableList<FutureCall<String>> l6 = new FutureCatList<>(new FutureCall<>(string6));
    private static final String string7 = new String(new char[charNumber]).replace('\0', '7');
    private static final FutureCatenableList<FutureCall<String>> l7 = new FutureCatList<>(new FutureCall<>(string7));
    private static final String string8 = new String(new char[charNumber]).replace('\0', '8');
    private static final FutureCatenableList<FutureCall<String>> l8 = new FutureCatList<>(new FutureCall<>(string8));
    private static final String string9 = new String(new char[charNumber]).replace('\0', '9');
    private static final FutureCatenableList<FutureCall<String>> l9 = new FutureCatList<>(new FutureCall<>(string9));

    public static void main(String[] args)
    {
        long LazyCatListRuntime = 0;
        long stringBuilderRuntime = 0;
        long badStringConcatenationRuntime = 0;
        long firstRuntime = 0;
        long secondRuntime = 0;
        for (int i = 0; i < 10; i++) {
            System.out.println("First!");
            ResultPerf<FutureCall<String>> test1 = RunLazyCatenableList(numberOfRuns);
            System.out.println("First Finished!");
            /*ResultPerf<FutureCall<String>> test2 = RunLazyCatenableList(numberOfRuns);
            ResultPerf<FutureCall<String>> test3 = RunLazyCatenableList(numberOfRuns);
            ResultPerf<FutureCall<String>> test4 = RunLazyCatenableList(numberOfRuns);
            ResultPerf<FutureCall<String>> test5 = RunConcat(test1.FutureCatList, test2.FutureCatList);
            ResultPerf<FutureCall<String>> test6 = RunConcat(test3.FutureCatList, test4.FutureCatList);
            ResultPerf<FutureCall<String>> test7 = RunConcat(test5.FutureCatList, test6.FutureCatList);
            LazyCatListRuntime += (test1.Runtime + test2.Runtime + test3.Runtime + test4.Runtime + test5.Runtime + test6.Runtime + test7.Runtime);
            System.out.println(
                    "FutureCatList Concatentation Runtime for one iteration: " + (test1.Runtime + test2.Runtime + test3.Runtime + test4.Runtime + test5.Runtime + test6.Runtime + test7.Runtime) + "ms");

            ResultPerfString<StringBuilder> testsb1 = RunStringBuilderConcatenation(numberOfRuns);
            ResultPerfString<StringBuilder> testsb2 = RunStringBuilderConcatenation(numberOfRuns);
            ResultPerfString<StringBuilder> testsb3 = RunStringBuilderConcatenation(numberOfRuns);
            ResultPerfString<StringBuilder> testsb4 = RunStringBuilderConcatenation(numberOfRuns);
            ResultPerfString<StringBuilder> testsb5 = RunStringBuilderConcat(testsb1.ResultString, testsb2.ResultString);
            ResultPerfString<StringBuilder> testsb6 = RunStringBuilderConcat(testsb3.ResultString, testsb4.ResultString);
            ResultPerfString<StringBuilder> testsb7 = RunStringBuilderConcat(testsb5.ResultString, testsb6.ResultString);
            stringBuilderRuntime += (testsb1.Runtime + testsb2.Runtime + testsb3.Runtime + testsb4.Runtime + testsb5.Runtime + testsb6.Runtime + testsb7.Runtime);
            System.out.println(
                    "StringBuilder Concatenation Runtime for one iteration: " + (testsb1.Runtime + testsb2.Runtime + testsb3.Runtime + testsb4.Runtime + testsb5.Runtime + testsb6.Runtime + testsb7.Runtime) + "ms");

            ResultPerfString<String> tests1 = RunBadStringConcatenation(numberOfRuns);
            ResultPerfString<String> tests2 = RunBadStringConcatenation(numberOfRuns);
            ResultPerfString<String> tests3 = RunBadStringConcatenation(numberOfRuns);
            ResultPerfString<String> tests4 = RunBadStringConcatenation(numberOfRuns);
            ResultPerfString<String> tests5 = RunBadStringConcat(tests1.ResultString, tests2.ResultString);
            ResultPerfString<String> tests6 = RunBadStringConcat(tests3.ResultString, tests4.ResultString);
            ResultPerfString<String> tests7 = RunBadStringConcat(tests5.ResultString, tests6.ResultString);
            badStringConcatenationRuntime += (tests1.Runtime + tests2.Runtime + tests3.Runtime + tests4.Runtime + tests5.Runtime + tests6.Runtime + tests7.Runtime);
            System.out.println(
                    "Bad String Concatenation Runtime for one iteration: " + (tests1.Runtime + tests2.Runtime + tests3.Runtime + tests4.Runtime + tests5.Runtime + tests6.Runtime + tests7.Runtime) + "ms");

            long t1 = System.currentTimeMillis();
            String LazyCatListStringCreation = test7.FutureCatList.toString();
            long t2 = System.currentTimeMillis();
            String stringBuilderStringCreation = testsb7.ResultString.toString();
            long t3 = System.currentTimeMillis();
            firstRuntime += t2 - t1;
            secondRuntime += t3 - t2;
            System.out.println("FutureCatList String Creation took: " + firstRuntime);
            System.out.println("StringBuilder String Creation took: " + secondRuntime);
            boolean finalStringEquals = LazyCatListStringCreation.equals(stringBuilderStringCreation) && LazyCatListStringCreation.equals(tests7.ResultString);
            System.out.println("All created strings (FutureCatList, StringBuilder, Bad String Concatenation) are equal: " + finalStringEquals);*/
        }/*
        System.out.println("Total FutureCatList Concatenation Runtime for all iterations: " + (LazyCatListRuntime) + "ms");
        System.out.println("Total StringBuilder Concatentation Runtime for all iterations: " + (stringBuilderRuntime) + "ms");
        System.out.println("FutureCatList String Creation Runtime: " + firstRuntime + "ms");
        System.out.println("StringBuilder String Creation Runtime: " + secondRuntime + "ms");
        System.out.println("Total FutureCatList Runtime (String -> All Concatenations -> String): " + (LazyCatListRuntime + firstRuntime) + "ms");
        System.out.println("Total StringBuilder Runtime (String -> All Concatenations -> String): " + (stringBuilderRuntime + secondRuntime) + "ms");
        System.out.println("Total Bad String Concatenation Runtime (String -> All Concatenations -> String): " + (badStringConcatenationRuntime) + "ms");
    }


    private static class ResultPerf<T extends FutureCall<?>>
    {
        final long Runtime;
        final FutureCatenableList<T> LazyCatList;

        ResultPerf(long runtime, FutureCatenableList<T> lazyCatList)
        {
            Runtime = runtime;
            LazyCatList = lazyCatList;
            System.out.println("FutureCatList concatenation took: " + runtime + "ms");
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

    private static ResultPerf<FutureCall<String>> RunConcat(FutureCatenableList<FutureCall<String>> first, FutureCatenableList<FutureCall<String>> second) {
        long t1 = System.currentTimeMillis();
        FutureCatenableList<FutureCall<String>> resultList = first.concat(second).get();
        long t2 = System.currentTimeMillis();
        return new ResultPerf<>(t2 - t1, resultList);
    }

    private static ResultPerf<FutureCall<String>> RunLazyCatenableList(int times)
    {
        long t1 = System.currentTimeMillis();
        CatenableList<FutureCall<String>> LazyCatList = new FutureCatList<>();
        for (int i = 0; i < times; i++) {
            LazyCatList = LazyCatList.concat(l1).concat(l2).concat(l3).concat(l4).concat(l5).concat(l6).concat(l7).concat(l8).concat(l9);
        }
        long t2 = System.currentTimeMillis();
        return new ResultPerf<>(t2 - t1, LazyCatList);
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
        String resultString = s1 + s2; // Is transformed to StringBuilder by compiler
        long t2 = System.currentTimeMillis();
        return new ResultPerfString<>(t2 - t1, resultString);
    }

    private static ResultPerfString<String> RunBadStringConcatenation(int times)
    {
        long t1 = System.currentTimeMillis();
        String resultString = "";
        for (int i = 0; i < times; i++) {
            resultString += string1 + string2 + string3 + string4 + string5 + string6 + string7 + string8 + string9; // Is transformed to StringBuilder by compiler
        }
        long t2 = System.currentTimeMillis();
        return new ResultPerfString<>(t2 - t1, resultString);
    }
}*/
