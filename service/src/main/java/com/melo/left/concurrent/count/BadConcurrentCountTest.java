package com.melo.left.concurrent.count;

/**
 * 如上面代码中的count += 1，
 * 至少需要三条 CPU 指令。
 * 指令 1：首先，需要把变量 count 从内存加载到 CPU 的寄存器；
 * 指令 2：之后，在寄存器中执行 +1 操作；
 * 指令 3：最后，将结果写入内存（缓存机制导致可能写入的是 CPU 缓存而不是内存）。
 */
public class BadConcurrentCountTest {

    private long count = 0;

    private void add10K() {
        int idx = 0;
        while (idx++ < 10000) {
            count++;
        }
    }

    public static long calculate() throws InterruptedException {
        final BadConcurrentCountTest test = new BadConcurrentCountTest();
        Thread thread1 = new Thread(() -> {
           test.add10K();
        });

        Thread thread2 = new Thread(() -> {
            test.add10K();
        });

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        return test.count;
    }

    public static void main(String[] args) throws InterruptedException {
        long count = BadConcurrentCountTest.calculate();
        System.out.println(count);
    }
}
