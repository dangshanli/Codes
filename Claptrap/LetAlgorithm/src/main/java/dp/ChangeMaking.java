package dp;

import lombok.*;
import utils.P;

import static utils.P.info;

// 找零
// 面对确定面值的零钱硬币，需要找零n元。求解怎样的零钱组合，使得恰好找零n元，且硬币数最小。
//假设每种面值的硬币数无限
public class ChangeMaking {
    @Getter
    @Setter
    private int amount;//当前计算的总额度

    private Integer[] cache;// 每个额度对应的最低硬币数

    final int[] changes = new int[3];// 零钱面值

    public ChangeMaking(int amount) {
        this.amount = amount;
        cache = new Integer[amount+1];//0 1 2 ... amount 共amount+1个索引
        cache[0] = 0;// 0元需要0个硬币
        initChange();
    }

    private void initChange() {
        changes[0] = 1;
        changes[1] = 3;
        changes[2] = 4;
    }

    int change(Integer amount) {
        if (amount == null) {
            amount = this.amount;
        }

        if (cache[amount] != null) {
            return cache[amount];//index == amount-1
        }

        for (int i = 1; i <= amount; i++) {//逐个增加总额数
            int j = 0;// 零钱槽位
            int temp = Integer.MAX_VALUE;// 最小硬币数
            while (j < changes.length && changes[j] <= i) {// 零钱槽位不越界 && 当前槽位的面值小于等于当前总额
                temp = P.min(temp, cache[i - changes[j]]);//因为是正向递推 因此这里的cache[index]一定有值 除非我们的递推关系搞错了
                j++;
            }
            cache[i] = (++temp);
        }

        return cache[amount];
    }

    public Integer[] getCache() {
        return cache;
    }

    public static void main(String[] args) {
        ChangeMaking making = new ChangeMaking(1000);
        int a = making.change(null);
        info("amount ={},coin num ={}", 6, a);
        for (int i : making.getCache()) {
            System.out.print(i+"\t");
        }
        System.out.println();

    }
}
