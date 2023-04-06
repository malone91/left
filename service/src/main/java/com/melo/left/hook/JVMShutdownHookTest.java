package com.melo.left.hook;

/**
 * jps 手动Linux是 kill pid命令执行，然后执行勾子方法，手动Windows是 taskkill -f -pid 26240 或 tskill 36388
 * 自动 如下的loop变量监听，执行完while则执行勾子方法
 */
public class JVMShutdownHookTest {
    public static void main(String[] args) throws Exception {
        Runtime.getRuntime().addShutdownHook(new Thread(JVMShutdownHookTest::doSomething));

        int loop = 0;
        while (true) {
            System.out.println("i am running...");
            Thread.sleep(500);
            if (loop == 10) {
                break;
            }
            loop++;
        }
    }

    /**
     * 停机前处理事项
     */
    private static void doSomething() {
        System.out.println("关闭【下班搞源码】书库。");
    }

}
