package jmh;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ClassicSort {
    private static final int N = 1_000;
    private static final int I = 150_000;

    private static List<Integer> testData = new ArrayList<>();

    public static void main(String[] args) {
        Random randomGen = new Random();
        for (int i = 0; i < N; i++) {
            testData.add(randomGen.nextInt(Integer.MAX_VALUE));
        }
        System.out.println("测试排序算法——");
        double start = System.nanoTime();

        for (int i = 0; i < I; i++) {
            List<Integer> copy = new ArrayList<>(testData);
            Collections.sort(copy);
        }
        double end = System.nanoTime();

        double timePerOperation = ((end - start) / (1_000_000_000l * I));
        System.out.println("testPerOp:" + timePerOperation);
        System.out.println("结果: " + (1 / timePerOperation) + " op/s");
    }

}
