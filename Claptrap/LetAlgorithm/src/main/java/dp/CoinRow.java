package dp;

import utils.P;

import java.util.HashMap;
import java.util.Map;

import static utils.P.info;

/**
 * 一排硬币，面值为正，选取其中的部分硬币，
 * 要求相邻的硬币不能同时选取，找到面值和的最大值
 */
public class CoinRow {

    /**
     * @param coins  原始的、总的coins
     * @param number 当前参与计算的硬币数
     * @return
     */
    int calculateMaxCoinValueSum(int[] coins, int number) {
        if (coins.length < number) {
            throw new IllegalArgumentException("Invalid parameters,coins.length=" + coins.length + ",number=" + number);
        }
        if (number == 0) {
            return 0;
        }
        if (number == 1) {
            return coins[0];
        }

        if (cache.containsKey(number))
            return cache.get(number);

        //正向求解
        //比起递归法的好处是步骤调用栈大小限制
        //缺点是没有递归法自然、易于理解
        for (int i = 2; i <= number; i++) {
            int r = P.max(calculateMaxCoinValueSum(coins, i - 2) + coins[i - 1], calculateMaxCoinValueSum(coins, i - 1));
            cache.put(i, r);
        }

        return cache.get(number);
    }

    static Map<Integer, Integer> cache = new HashMap<>();

    public static void main(String[] args) {

        int[] coins = {5, 1, 2, 10, 6, 2};

        int s = new CoinRow().calculateMaxCoinValueSum(coins, coins.length);
        info("s = {}", s);
        cache.forEach((k, v) -> {
            info("k={},v={}", k, v);
        });

    }
}
