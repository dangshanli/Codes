package dp;

import lombok.Getter;
import lombok.Setter;
import utils.P;

import static utils.P.max;

// 机器人吃硬币
// 一个网格 部分格子摆上硬币 一个机器人从左上角 吃到右下角
// 迟到一个硬币加一分 每次要么下移 要么右移 问：对于确定的硬币网格，走怎样的路线吃到的硬币最多 时多少
public class RobotCoinCollection {

    @Getter
    @Setter
    Integer[][] grid;//硬币网格 有硬币的置为1 没硬币的置为0

    @Getter
    Integer[][] F;// 缓存

    int n;
    int m;

    public RobotCoinCollection(int n, int m) {
        F = new Integer[m + 1][n + 1];
        grid = P.genRobotCoinArr(m + 1, n + 1);
        this.n = n;
        this.m = m;
    }

    //这里的元素n = grid[0].length-1; m = grid.length-1
    int go() {
        F[1][1] = grid[1][1];
        for (int j = 2; j < grid[0].length; j++) {// 求出第一行的全部元素值
            F[1][j] = F[1][j - 1] + grid[1][j];
        }

        for (int i = 2; i < grid.length; i++) {
            F[i][1] = F[i - 1][1] + grid[i][1];
            for (int j = 2; j < grid[0].length; j++) {
                F[i][j] = max(F[i - 1][j], F[i][j - 1]) + grid[i][j];
            }
        }
        return F[m][n];
    }

    public static void main(String[] args) {
        RobotCoinCollection coll = new RobotCoinCollection(6, 5);
        P.showTwoDimension(coll.getGrid());
        P.showTwoDimension(coll.getF());
        int a = coll.go();
        System.out.println("a=" + a);
        P.showTwoDimension(coll.getF());
    }
}
