package temp;

import java.lang.reflect.Field;

public class Son extends Father {
    public static void main(String[] args) {
        System.out.println(new Son().getName());
        Field[] fields = Son.class.getFields();
        for (Field f : fields) {
            f.setAccessible(true);
            System.out.println(f.getName());
        }
    }
}
