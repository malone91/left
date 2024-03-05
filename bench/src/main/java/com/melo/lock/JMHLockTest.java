package com.melo.lock;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
public class JMHLockTest {

    private final Lock lock = new ReentrantLock();
    private static int count;

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder().include(JMHLockTest.class.getSimpleName())
                .forks(1)
                .warmupIterations(5)
                .measurementIterations(5)
                .build();
        new Runner(options).run();
    }

    @Benchmark
    @Threads(50)
    public void add() {
        for (int i = 0; i < 500000; i++) {
            lock.lock();
            try {
                count++;
            } finally {
                lock.unlock();
            }
        }
    }
}
