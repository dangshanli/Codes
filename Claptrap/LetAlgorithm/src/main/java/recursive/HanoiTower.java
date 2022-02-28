package recursive;

/**
 * 汉诺塔问题（递归算法）：
 * 有3个柱子 其中一个柱子上装载6个圆盘 圆盘从小打到依次自上而下堆叠 形成圆锥形
 * 把整个堆叠圆盘放到另一个柱子上
 * <p>
 * 规则：
 * ① 一次移动一个盘子
 * ② 每次只能将柱子最上方的盘子移动到其他柱子上
 * ③ 不能出现大盘子在小盘子之上
 * <p>
 * 思路：
 * 1、将柱子(num_1)上的1->(n-1)个盘子移动至辅助柱子(num_2)
 * 2、将柱子(num_1)上的最大的盘子移动至目标柱子(num_3)
 * 3、将辅助柱子(num_2)上的盘子移动至目标柱子(num_3)上
 * 其中第三步又相当于一个大小为n-1的新汉诺塔问题，
 * 原来的原来辅助柱子变为来源柱子，来源柱子变为辅助柱子
 */
public class HanoiTower {

    void movePansHanoi(String from, String to, String auxPan, int panSize) {
        if (panSize == 1) {
            System.out.println("move [1号盘] from [" + from + "] to [" + to + "]");
            return;
        }
        movePansHanoi(from, auxPan, to, panSize - 1);
        System.out.printf("move [%s号盘] from [%s] to [%s]\n", panSize, from, to);
        movePansHanoi(auxPan, to, from, panSize - 1);
    }

    public static void main(String[] args) {
        new HanoiTower().movePansHanoi("起源", "目标", "辅助", 3);
    }
}
