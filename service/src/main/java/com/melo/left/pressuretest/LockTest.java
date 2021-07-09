package com.melo.left.pressuretest;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//测试方法平均执行时间
@BenchmarkMode({Mode.AverageTime})
//输出结果的时间单位
@OutputTimeUnit(TimeUnit.MILLISECONDS)
//预热次数 JIT机制
@Warmup(iterations = 3, time = 5, timeUnit = TimeUnit.MILLISECONDS)
//测试次数
@Measurement(iterations = 1, batchSize = 1000)
//每个测试进程的测试线程数量
@Threads(2)
//JMH fork出指定个数的进程测试
@Fork(1)
//所有线程共享实例
@State(Scope.Benchmark)
public class LockTest {

    private Lock lock = new ReentrantLock();
    private long lockIndex = 0;
    private long noLockIndex = 0;
    private long syncIndex = 0;
    private AtomicLong atomicLong = new AtomicLong();

    public static void main(String[] args) throws RunnerException {
        String simpleName = LockTest.class.getSimpleName();
        Options options = new OptionsBuilder()
                .include(simpleName)
                .build();
        new Runner(options).run();
    }

    /**
     * 标记了才会参与基准测试，方法必须public
     */
    @Benchmark
    public void measureLock() {
        lock.lock();
        lockIndex++;
        lock.unlock();
    }

    @Benchmark
    public void measureSync() {
        synchronized (this) {
            syncIndex++;
        }
    }

    @Benchmark
    public void measureCAS() {
        atomicLong.incrementAndGet();
    }

    @Benchmark
    public void measureNoLock() {
        noLockIndex++;
    }
}
