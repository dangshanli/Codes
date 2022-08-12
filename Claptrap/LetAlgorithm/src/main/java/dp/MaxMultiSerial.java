package dp;

import lombok.AllArgsConstructor;
import lombok.ToString;
import utils.P;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

// 一个长度为N的数组 找出其中任意（N-1）个的乘积 并比较大小
public class MaxMultiSerial {

    //暴力法 时间复杂度 O(n^2)
    double maxNumber(double[] origin) {
        double max = -1;
        int excludeIndex = 0;
        for (int i = 0; i < origin.length; i++) {
            double v = 1;
            for (int j = 0; j < origin.length; j++) {
                if (j == i)
                    continue;
                v *= origin[j];
            }
            if (v > max) {
                max = v;
                excludeIndex = i;
            }
        }
        P.printInfo("excludeIndex={}", excludeIndex);
        return max;
    }

    WrapperMax maxSubSerial2(double[] origin, int start, int end) {
        if (end == start)
            throw new RuntimeException("end must be greater than start");

        if (end - start == 1) {
            if (origin[start] >= origin[end])
                return new WrapperMax(origin[start], origin[end], origin[start] * origin[end]);
            else
                return new WrapperMax(origin[end], origin[start], origin[start] * origin[end]);
        }

        if (end - start == 2) {
            double a = origin[start] * origin[end];
            double b = origin[start] * origin[start + 1];
            double c = origin[start + 1] * origin[end];
            return new WrapperMax(max(a, b, c), min(a, b, c), origin[start] * origin[start + 1] * origin[end]);
        }

        if (map.containsKey(String.format("%s:%s", start, end))) {// 没屌用 这里只是简单的分治
            P.printInfo("{}","hint");
            return map.get(String.format("%s:%s", start, end));
        }

        int mid = start + (end - start) / 2;
        WrapperMax maxA = maxSubSerial2(origin, start, mid);
        WrapperMax maxB = maxSubSerial2(origin, mid + 1, end);
        double m1 = maxA.max * maxB.allMulti;
        double m2 = maxA.min * maxB.allMulti;
        double m3 = maxB.max * maxA.allMulti;
        double m4 = maxB.min * maxA.allMulti;
        double all = maxA.allMulti * maxB.allMulti;
        double max = max(m1, m2, m3, m4);
        double min = min(m1, m2, m3, m4);

        WrapperMax result = new WrapperMax(max, min, all);
        map.put(String.format("%s:%s", start, end), result);
        return result;
    }

    Map<String, WrapperMax> map = new HashMap<String, WrapperMax>();

    double max(double... values) {
        double max = values[0];
        for (int i = 1; i < values.length; i++) {
            if (values[i] > max)
                max = values[i];
        }

        return max;
    }

    double min(double... values) {
        double min = values[0];
        for (int i = 1; i < values.length; i++) {
            if (values[i] < min)
                min = values[i];
        }
        return min;
    }

    @AllArgsConstructor
    @ToString
    static class WrapperMax {
        double max;//全组合方式中的最大值
        double min;//全组合方式的最小值
        double allMulti;//全部值的相乘
    }

    public static void main(String[] args) {

        int len = 150000;
        double[] rr = new double[len];
        Random randomGen = new Random();
        for (int i = 0; i < len; i++) {
//            rr[i] = randomGen.nextInt(2) * (randomGen.nextBoolean() ? 1 : -1);
            rr[i] = randomGen.nextDouble() + 1.1;
        }
        System.out.println();
        long start = System.currentTimeMillis();
        WrapperMax max = new MaxMultiSerial().maxSubSerial2(rr, 0, rr.length - 1);
        P.printInfo("max result = {}", max);
        P.printInfo("{}",System.currentTimeMillis()-start);
        System.out.println("----------------------------------------------------------------");
        start = System.currentTimeMillis();
        double result = new MaxMultiSerial().maxNumber(rr);
        P.printInfo("max result = {}", result);
        P.printInfo("{}",System.currentTimeMillis()-start);
//        max result = MaxMultiSerial.WrapperMax(max=Infinity, min=Infinity, allMulti=Infinity)
//        319 ms
//        ----------------------------------------------------------------
//        excludeIndex=0
//        max result = Infinity
//        59964ms
    }
}
