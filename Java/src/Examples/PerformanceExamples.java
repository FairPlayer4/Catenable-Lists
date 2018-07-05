package Examples;

import Interfaces.CatenableList;
import NonLazy.CatList;

public class PerformanceExamples
{

    private static final int charNumber = 1000;

    private static final int numberOfRuns = 100;

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
        long catListRuntime = 0;
        long stringBuilderRuntime = 0;
        long badStringConcatenationRuntime = 0;
        long firstRuntime = 0;
        long secondRuntime = 0;
        for (int i = 0; i < runs; i++) {
            ResultPerf<String> test1 = RunCatenableList(numberOfRuns);
            ResultPerf<String> test2 = RunCatenableList(numberOfRuns);
            ResultPerf<String> test3 = RunCatenableList(numberOfRuns);
            ResultPerf<String> test4 = RunCatenableList(numberOfRuns);
            ResultPerf<String> test5 = RunConcat(test1.CatList, test2.CatList);
            ResultPerf<String> test6 = RunConcat(test3.CatList, test4.CatList);
            ResultPerf<String> test7 = RunConcat(test5.CatList, test6.CatList);
            catListRuntime += (test1.Runtime + test2.Runtime + test3.Runtime + test4.Runtime + test5.Runtime + test6.Runtime + test7.Runtime);
            System.out.println(
                    "CatList Concatentation Runtime for one iteration: " + (test1.Runtime + test2.Runtime + test3.Runtime + test4.Runtime + test5.Runtime + test6.Runtime + test7.Runtime) + "ns");

            ResultPerfString<StringBuilder> testsb1 = PerformanceExamples
                    .RunStringBuilderConcatenation(numberOfRuns);
            ResultPerfString<StringBuilder> testsb2 = PerformanceExamples
                    .RunStringBuilderConcatenation(numberOfRuns);
            ResultPerfString<StringBuilder> testsb3 = PerformanceExamples
                    .RunStringBuilderConcatenation(numberOfRuns);
            ResultPerfString<StringBuilder> testsb4 = PerformanceExamples
                    .RunStringBuilderConcatenation(numberOfRuns);
            ResultPerfString<StringBuilder> testsb5 = PerformanceExamples
                    .RunStringBuilderConcat(testsb1.ResultString, testsb2.ResultString);
            ResultPerfString<StringBuilder> testsb6 = PerformanceExamples
                    .RunStringBuilderConcat(testsb3.ResultString, testsb4.ResultString);
            ResultPerfString<StringBuilder> testsb7 = PerformanceExamples
                    .RunStringBuilderConcat(testsb5.ResultString, testsb6.ResultString);
            stringBuilderRuntime += (testsb1.Runtime + testsb2.Runtime + testsb3.Runtime + testsb4.Runtime + testsb5.Runtime + testsb6.Runtime + testsb7.Runtime);
            System.out.println(
                    "StringBuilder Concatenation Runtime for one iteration: " + (testsb1.Runtime + testsb2.Runtime + testsb3.Runtime + testsb4.Runtime + testsb5.Runtime + testsb6.Runtime + testsb7.Runtime) + "ns");

            ResultPerfString<String> tests1 = RunBadStringConcatenation(numberOfRuns);
            ResultPerfString<String> tests2 = RunBadStringConcatenation(numberOfRuns);
            ResultPerfString<String> tests3 = RunBadStringConcatenation(numberOfRuns);
            ResultPerfString<String> tests4 = RunBadStringConcatenation(numberOfRuns);
            ResultPerfString<String> tests5 = Examples.PerformanceExamples
                    .RunBadStringConcat(tests1.ResultString, tests2.ResultString);
            ResultPerfString<String> tests6 = Examples.PerformanceExamples
                    .RunBadStringConcat(tests3.ResultString, tests4.ResultString);
            ResultPerfString<String> tests7 = Examples.PerformanceExamples
                    .RunBadStringConcat(tests5.ResultString, tests6.ResultString);
            badStringConcatenationRuntime += (tests1.Runtime + tests2.Runtime + tests3.Runtime + tests4.Runtime + tests5.Runtime + tests6.Runtime + tests7.Runtime);
            System.out.println(
                    "Bad String Concatenation Runtime for one iteration: " + (tests1.Runtime + tests2.Runtime + tests3.Runtime + tests4.Runtime + tests5.Runtime + tests6.Runtime + tests7.Runtime) + "ns");

            long t1 = System.nanoTime();
            System.out.println("Total Size of the String: " + ((CatList<String>)test7.CatList).getStringSize()); // Call to size is cached but counted in the benchmark
            String catListStringCreation = test7.CatList.toString();
            long t2 = System.nanoTime();
            String stringBuilderStringCreation = testsb7.ResultString.toString();
            long t3 = System.nanoTime();
            firstRuntime += t2 - t1;
            secondRuntime += t3 - t2;
            System.out.println("CatList String Creation took: " + firstRuntime + "ns");
            System.out.println("StringBuilder String Creation took: " + secondRuntime + "ns");
            boolean finalStringEquals = catListStringCreation.equals(stringBuilderStringCreation) && catListStringCreation.equals(tests7.ResultString);
            System.out.println("All created strings (CatList, StringBuilder, Bad String Concatenation) are equal: " + finalStringEquals);
            System.out.println("!!!Total Size of the String: " + catListStringCreation.length());
        }
        System.out.println("Total CatList Concatenation Runtime for all iterations: " + (catListRuntime) / milliDiv + "ms");
        System.out.println("Total StringBuilder Concatentation Runtime for all iterations: " + (stringBuilderRuntime) / milliDiv + "ms");
        System.out.println("CatList String Creation Runtime: " + firstRuntime / milliDiv + "ms");
        System.out.println("StringBuilder String Creation Runtime: " + secondRuntime / milliDiv + "ms");
        System.out.println("Total CatList Runtime (String -> All Concatenations -> String): " + (catListRuntime + firstRuntime) / milliDiv + "ms");
        System.out.println("Total StringBuilder Runtime (String -> All Concatenations -> String): " + (stringBuilderRuntime + secondRuntime) / milliDiv + "ms");
        System.out.println("Total Bad String Concatenation Runtime (String -> All Concatenations -> String): " + (badStringConcatenationRuntime) / milliDiv + "ms");

        System.out.println("Average CatList Concatenation Runtime for all iterations: " + (catListRuntime) / milliDivAverage + "ms");
        System.out.println("Average StringBuilder Concatentation Runtime for all iterations: " + (stringBuilderRuntime) / milliDivAverage + "ms");
        System.out.println("Average String Creation Runtime: " + firstRuntime / milliDivAverage + "ms");
        System.out.println("Average String Creation Runtime: " + secondRuntime / milliDivAverage + "ms");
        System.out.println("Average CatList Runtime (String -> All Concatenations -> String): " + (catListRuntime + firstRuntime) / milliDivAverage + "ms");
        System.out.println("Average StringBuilder Runtime (String -> All Concatenations -> String): " + (stringBuilderRuntime + secondRuntime) / milliDivAverage + "ms");
        System.out.println("Average Bad String Concatenation Runtime (String -> All Concatenations -> String): " + (badStringConcatenationRuntime) / milliDivAverage + "ms");
    }

    private static final double milliDiv = 1000000;

    private static final int runs = 100;

    private static final double milliDivAverage = milliDiv * 100;

    private static class ResultPerf<T>
    {
        final long Runtime;
        final CatenableList<T> CatList;

        ResultPerf(long runtime, CatenableList<T> catList)
        {
            Runtime = runtime;
            CatList = catList;
            System.out.println("CatList concatenation took: " + runtime + "ns");
        }
    }

    private static class ResultPerfString<T>
    {
        final long Runtime;
        final T ResultString;

        ResultPerfString(long runtime, T resultString, boolean stringBuilder)
        {
            Runtime = runtime;
            ResultString = resultString;
            String string = stringBuilder ? "StringBuilder" : "Bad String";
            System.out.println(string + " concatenation took: " + runtime + "ns");
        }
    }

    private static ResultPerf<String> RunConcat(CatenableList<String> first, CatenableList<String> second) {
        long t1 = System.nanoTime();
        CatenableList<String> resultList = first.concat(second);
        long t2 = System.nanoTime();
        return new ResultPerf<>(t2 - t1, resultList);
    }

    private static ResultPerf<String> RunCatenableList(int times)
    {
        long t1 = System.nanoTime();
        CatenableList<String> catList = new CatList<>();
        for (int i = 0; i < times; i++) {
            catList = catList.concat(l1).concat(l2).concat(l3).concat(l4).concat(l5).concat(l6).concat(l7).concat(l8)
                             .concat(l9);
        }
        long t2 = System.nanoTime();
        return new ResultPerf<>(t2 - t1, catList);
    }

    private static ResultPerfString<StringBuilder> RunStringBuilderConcat(StringBuilder sb1, StringBuilder sb2) {
        long t1 = System.nanoTime();
        StringBuilder sb = new StringBuilder(sb1); // Persistence
        sb.append(sb2);
        long t2 = System.nanoTime();
        return new ResultPerfString<>(t2 - t1, sb, true);
    }

    private static ResultPerfString<StringBuilder> RunStringBuilderConcatenation(int times)
    {
        long t1 = System.nanoTime();
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
        long t2 = System.nanoTime();
        return new ResultPerfString<>(t2 - t1, stringBuilder, true);
    }

    private static ResultPerfString<String> RunBadStringConcat(String s1, String s2) {
        long t1 = System.nanoTime();
        String resultString = s1 + s2; // Is transformed to StringBuilder by compiler
        long t2 = System.nanoTime();
        return new ResultPerfString<>(t2 - t1, resultString,false);
    }

    private static ResultPerfString<String> RunBadStringConcatenation(int times)
    {
        long t1 = System.nanoTime();
        String resultString = "";
        for (int i = 0; i < times; i++) {
            resultString += string1 + string2 + string3 + string4 + string5 + string6 + string7 + string8 + string9; // Is transformed to StringBuilder by compiler
        }
        long t2 = System.nanoTime();
        return new ResultPerfString<>(t2 - t1, resultString, false);
    }
}
