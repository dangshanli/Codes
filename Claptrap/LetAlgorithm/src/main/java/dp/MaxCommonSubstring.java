package dp;

import lombok.AllArgsConstructor;
import lombok.Data;

// 最大乘积子串寻找
public class MaxCommonSubstring {

    //暴力法 O(n^2)
    // 穷尽全部的子串
    MaxResult maxSubstring1(double[] input) {
        MaxResult max = new MaxResult(1, 0, 0);
        for (int i = 0; i < input.length; i++) {
            double x = 1;
            for (int j = i; j < input.length; j++) {
                x *= input[j];// 计算当前字串的大小
                if (x > max.getResult()) {//对比 大于则赋值
                    max.setResult(x);
                    max.setStart(i);
                    max.setEnd(j);
                }
            }
        }
        return max;
    }

    //DP
    //double[] input = new double[]{-2.5, 4, 0, 3, 0.5, 8, -1};
    double maxSubstring3(double[] input) {
        double maxEnd = input[0];
        double minEnd = input[0];
        double maxResult = input[0];
        System.out.println(input[0]);
        for (int i = 1; i < input.length; ++i) {
            double end1 = maxEnd * input[i];
            double end2 = minEnd * input[i];
            maxEnd = max(max(end1, end2), input[i]);
            minEnd = min(min(end1, end2), input[i]);
            maxResult = max(maxResult, maxEnd);
            printInfo("input[{}]={},end1={}, end2={},maxEnd={},minEnd={},maxResult={}",
                    i, String.valueOf(input[i]), end1, end2, maxEnd, minEnd, maxResult);
        }
        return maxResult;
    }

    // index 0->len-1
    //递归方式
    End maxSubstring4(double[] input, int index) {
        if (index == 0) {
            return new End(input[0], input[0], input[0]);
        }

        End end = maxSubstring4(input, index - 1);
        double maxEnd = max(max(end.maxEnd * input[index], end.minEnd * input[index]), input[index]);
        double minEnd = min(min(end.maxEnd * input[index], end.minEnd * input[index]), input[index]);
        double maxResult = max(end.maxResult, maxEnd);
        End result = new End(maxEnd, minEnd, maxResult);
        System.out.println(result);
        return result;
    }

    void printInfo(String origin, Object... args) {
        for (int i = 0; i < args.length; i++) {
            origin = origin.replaceFirst("\\{\\}", args[i].toString());
        }
        System.out.println(origin);
    }

    double max(double x, double y) {
        return x > y ? x : y;
    }

    double min(double x, double y) {
        return x > y ? y : x;
    }

    @Data
    @AllArgsConstructor
    static class End {
        double maxEnd;
        double minEnd;
        double maxResult;
    }

    @AllArgsConstructor
    @Data
    static class MaxResult {
        double result;//字串乘积
        int start;//index起始
        int end;// index结尾
    }

    public static void main(String[] args) {
        double[] input = new double[]{-2.5, 4, 0, 3, 0.5, 8, -1};
//        System.out.println(new MaxCommonSubstring().maxSubstring1(input));
        System.out.println(new MaxCommonSubstring().maxSubstring3(input));
        System.out.println(new MaxCommonSubstring().maxSubstring4(input, input.length - 1));
    }

}
