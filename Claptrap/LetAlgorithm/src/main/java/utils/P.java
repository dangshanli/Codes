package utils;

public class P {

    // 模仿log.info的{}替代模式，格式化打印日志
    public static void printInfo(String origin, Object... args) {
        for (Object arg : args) {
            origin = origin.replaceFirst("\\{\\}", arg.toString());
        }
        System.out.println(origin);
    }
}
