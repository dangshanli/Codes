package utils;

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
}
