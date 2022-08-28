package utils;

import java.util.Random;
import java.util.function.Supplier;

public class P {

    // 模仿log.info的{}替代模式，格式化打印日志
    public static void info(String origin, Object... args) {
        for (Object arg : args) {
            origin = origin.replaceFirst("\\{\\}", arg.toString());
        }
        System.out.println(origin);
    }

    public static <T extends Comparable<T>> T max(T... args) {
        T result = args[0];
        for (T arg : args) {
            if (result.compareTo(arg) < 0) {
                result = arg;
            }
        }
        return result;
    }

    public static <T extends Comparable<T>> T min(T... args) {
        T result = args[0];
        for (T arg : args) {
            if (result.compareTo(arg) > 0) {
                result = arg;
            }
        }
        return result;
    }

    public static <T> void showTwoDimension(T[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                System.out.print(arr[i][j] + "\t");
            }
            System.out.println();
        }
    }

    public static <T> T[][] genTwoDimension(Supplier<T> supplier, T[][] result) {
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                result[i][j] = supplier.get();
            }
        }
        return result;
    }

    final static Random random = new Random();


    public static void main(String[] args) {
        Integer[][] a = genRobotCoinArr(10, 10);
        showTwoDimension(a);
    }

    public static Integer[][] genRobotCoinArr(int x, int y) {
        Integer[][] result = new Integer[x][y];
        Supplier<Integer> supplier = () -> random.nextBoolean() ? 1 : 0;
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                if (i == 0 || j == 0)
                    result[i][j] = 0;
                else
                    result[i][j] = supplier.get();
            }
        }
        return result;
    }

}
