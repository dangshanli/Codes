package recursive;

import java.util.Comparator;

/**
 * 给定一个数组 判定其是否有序
 * 使用递归法
 */
public class OrderedArray {

    public static <T> boolean isOrdered(T[] arr, Comparator<T> comparator) {
        if (arr.length == 1) {
            return true;//有序
        }
        Object[] desc = new Object[arr.length - 1];
        System.arraycopy(arr, 1, desc, 0, arr.length - 1);
        if (isOrdered((T[]) desc, comparator) &&//子序列有序
                comparator.compare(arr[0], arr[1]) < 0) {//当前序列第一个值比子序列最小的值还小，整个序列正序
            return true;
        }
        return false;
    }

    public static <T> boolean isOrderedInt(Integer[] arr) {
        return isOrdered(arr, Comparator.comparingInt(o -> o));
    }

    public static <T> boolean isOrderedIntReversed(Integer[] arr) {
        return isOrdered(arr, (o1, o2) -> o2 - o1);
    }

    public static void main(String[] args) {
        Integer[] ints = {7, 6, 5, 3, 2, 1};
        Integer[] ints1 = {1, 2, 4, 7, 9, 10, 55};
        Integer[] ints2 = {1, 2, 8, 7, 9, 10, 33};
        System.out.println(isOrderedInt(ints1));
        System.out.println(isOrderedIntReversed(ints));
        System.out.println(isOrderedInt(ints2));
    }
}
