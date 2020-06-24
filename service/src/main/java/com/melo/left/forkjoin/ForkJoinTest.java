package com.melo.left.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * 分治任务的线程池ForkJoinPool，分治任务ForkJoinTask
 * 类似于 ThreadPoolExecutor 和 Runnable 的关系，前者本质上是一个生产者-消费者模式的实现
 * 内部有一个任务队列，多个工作线程共享一个任务队列，只有一个任务队列
 * 提交任务到线程池
 *
 * 计算斐波纳契数列
 *
 * ForkJoin本质上也是生产者和消费者的实现，但是更加智能，
 * 其拥有多个任务队列，当通过ForkJoinPool的invoke方法或者submit方法提交任务时，
 * ForkJoinPool根据一定的路由规则把任务提交到一个任务队列中如果任务在执行过程中
 * 会创建出子任务，那么子任务会提交到工作线程对应的任务队列中。
 *
 * 任务窃取，比较主动，所有的工作线程不会闲着；任务队列采用双端队列
 */
public class ForkJoinTest {

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool(4);
        Fibonacci fibonacci = new Fibonacci(30);
        Integer invoke = forkJoinPool.invoke(fibonacci);
        System.out.println(invoke);
    }

    static class Fibonacci extends RecursiveTask<Integer> {
        final int n;
        Fibonacci(int n){
            this.n = n;
        }

        @Override
        protected Integer compute() {
            if (n <= 1) {
                return n;
            }
            Fibonacci f1 = new Fibonacci(n - 1);
            //使用了异步子任务
            f1.fork();
            Fibonacci f2 = new Fibonacci(n - 2);
            return f2.compute() + f1.join();
        }
    }

}
