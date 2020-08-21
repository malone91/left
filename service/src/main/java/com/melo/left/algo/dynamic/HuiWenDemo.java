package com.melo.left.algo.dynamic;

/**
 * opt[i, j] = opt[i-1, j] + opt[i, j-1] + ...
 * 状态转移方程
 *
 * 最优子结构，程序不需要回溯到终点，只依赖于前一个状态的最优值
 *
 * 回溯（递归）---重复计算
 * 贪心----------永远局部最优
 * DP-----------记录局部最优子结构/多种记录值，动态递推，集合了最优值。
 * 记录了每个状态
 *
 *
 * 爬楼梯：斐波那契数列
 * f(0)=1, f(1)=1,
 * f(n) = f(n-1) + f(n-2)
 * 时间复杂度o(n)
 *
 * 斐波那契数列还有logN解法
 */
public class HuiWenDemo {

    public static void main(String[] args) {
        HuiWenDemo demo = new HuiWenDemo();
        System.out.println(demo.deduceHuiWen("fffffs"));
    }

    public boolean deduceHuiWen(String s) {
        if (s.length() < 2) {
            return false;
        }
        char[] chars = s.toCharArray();
        int length = chars.length;
        int middle = length >> 1;
        int left = 0, right = length - 1;
        while (left < middle) {
            if (chars[left] != chars[right]) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
}