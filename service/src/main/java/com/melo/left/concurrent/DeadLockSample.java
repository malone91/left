package com.melo.left.concurrent;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DeadLockSample extends Thread {

    private String first;
    private String second;

    public DeadLockSample(String first, String second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public void run() {
        synchronized (first) {
            System.out.println(Thread.currentThread().getName() + "获取到了第一个锁");
            synchronized (second) {
                System.out.println(Thread.currentThread().getName() + "获取到了第二个锁");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        //detect dead lock
        ThreadMXBean mxBean = ManagementFactory.getThreadMXBean();
        Runnable dlCheck = () -> {
            long[] deadlockedThreads = mxBean.findDeadlockedThreads();
            if (deadlockedThreads != null) {
                ThreadInfo[] threadInfo = mxBean.getThreadInfo(deadlockedThreads);
                System.out.println("detect dead lock threads");
                for (ThreadInfo info : threadInfo) {
                    System.out.println(info.getThreadName());
                }
            }
        };
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        // 稍等5秒，然后每10秒进行一次死锁扫描
        scheduler.scheduleAtFixedRate(dlCheck, 5L, 10L, TimeUnit.SECONDS);
        //get current process id
        String lockA = "lockA";
        String lockB = "lockB";
        DeadLockSample sample1 = new DeadLockSample(lockA, lockB);
        DeadLockSample sample2 = new DeadLockSample(lockB, lockA);
        sample1.start();
        sample2.start();
        sample1.join();
        sample2.join();
    }

    /**
     * "Thread-1" #12 prio=5 os_prio=0 tid=0x000000001a1c0000 nid=0x421c waiting for monitor entry [0x000000001aa3f000]
     java.lang.Thread.State: BLOCKED (on object monitor)
     at com.melo.left.concurrent.DeadLockSample.run(DeadLockSample.java:18)
     - waiting to lock <0x00000000d63250c0> (a java.lang.String)
     - locked <0x00000000d63250f8> (a java.lang.String)

     "Thread-0" #11 prio=5 os_prio=0 tid=0x000000001a1bc000 nid=0x666c waiting for monitor entry [0x000000001a93f000]
     java.lang.Thread.State: BLOCKED (on object monitor)
     at com.melo.left.concurrent.DeadLockSample.run(DeadLockSample.java:18)
     - waiting to lock <0x00000000d63250f8> (a java.lang.String)
     - locked <0x00000000d63250c0> (a java.lang.String)

     "Monitor Ctrl-Break" #10 daemon prio=5 os_prio=0 tid=0x000000001a192000 nid=0x2d3c runnable [0x000000001a82f000]
     java.lang.Thread.State: RUNNABLE
     at java.lang.StringBuffer.append(StringBuffer.java:367)
     - locked <0x00000000d6342b80> (a java.lang.StringBuffer)
     at java.io.BufferedReader.readLine(BufferedReader.java:370)
     - locked <0x00000000d633c490> (a java.io.InputStreamReader)
     at java.io.BufferedReader.readLine(BufferedReader.java:389)
     at com.intellij.rt.execution.application.AppMain$1.run(AppMain.java:93)
     at java.lang.Thread.run(Thread.java:748)

     "Service Thread" #9 daemon prio=9 os_prio=0 tid=0x00000000194bc800 nid=0x65a8 runnable [0x0000000000000000]
     java.lang.Thread.State: RUNNABLE

     "C1 CompilerThread2" #8 daemon prio=9 os_prio=2 tid=0x0000000018103800 nid=0x7390 waiting on condition [0x0000000000000000]
     java.lang.Thread.State: RUNNABLE

     "C2 CompilerThread1" #7 daemon prio=9 os_prio=2 tid=0x00000000180fd000 nid=0x6fd4 waiting on condition [0x0000000000000000]
     java.lang.Thread.State: RUNNABLE

     "C2 CompilerThread0" #6 daemon prio=9 os_prio=2 tid=0x00000000180f9000 nid=0x4604 waiting on condition [0x0000000000000000]
     java.lang.Thread.State: RUNNABLE

     "Attach Listener" #5 daemon prio=5 os_prio=2 tid=0x00000000180f8000 nid=0x6608 runnable [0x0000000000000000]
     java.lang.Thread.State: RUNNABLE

     "Signal Dispatcher" #4 daemon prio=9 os_prio=2 tid=0x0000000019473800 nid=0x6e60 waiting on condition [0x0000000000000000]
     java.lang.Thread.State: RUNNABLE

     "Finalizer" #3 daemon prio=8 os_prio=1 tid=0x00000000180c0000 nid=0x6670 in Object.wait() [0x000000001942f000]
     java.lang.Thread.State: WAITING (on object monitor)
     at java.lang.Object.wait(Native Method)
     - waiting on <0x00000000d6008ed0> (a java.lang.ref.ReferenceQueue$Lock)
     at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:144)
     - locked <0x00000000d6008ed0> (a java.lang.ref.ReferenceQueue$Lock)
     at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:165)
     at java.lang.ref.Finalizer$FinalizerThread.run(Finalizer.java:216)

     "Reference Handler" #2 daemon prio=10 os_prio=2 tid=0x00000000035d5000 nid=0x334c in Object.wait() [0x000000001932f000]
     java.lang.Thread.State: WAITING (on object monitor)
     at java.lang.Object.wait(Native Method)
     - waiting on <0x00000000d6006bf8> (a java.lang.ref.Reference$Lock)
     at java.lang.Object.wait(Object.java:502)
     at java.lang.ref.Reference.tryHandlePending(Reference.java:191)
     - locked <0x00000000d6006bf8> (a java.lang.ref.Reference$Lock)
     at java.lang.ref.Reference$ReferenceHandler.run(Reference.java:153)

     "main" #1 prio=5 os_prio=0 tid=0x00000000034e5800 nid=0x707c in Object.wait() [0x00000000034de000]
     java.lang.Thread.State: WAITING (on object monitor)
     at java.lang.Object.wait(Native Method)
     - waiting on <0x00000000d6325130> (a com.melo.left.concurrent.DeadLockSample)
     at java.lang.Thread.join(Thread.java:1252)
     - locked <0x00000000d6325130> (a com.melo.left.concurrent.DeadLockSample)
     at java.lang.Thread.join(Thread.java:1326)
     at com.melo.left.concurrent.DeadLockSample.main(DeadLockSample.java:30)
     at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
     at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
     at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
     at java.lang.reflect.Method.invoke(Method.java:498)
     at com.intellij.rt.execution.application.AppMain.main(AppMain.java:144)

     "VM Thread" os_prio=2 tid=0x0000000018097800 nid=0x4480 runnable

     "GC task thread#0 (ParallelGC)" os_prio=0 tid=0x00000000034fc000 nid=0x77bc runnable

     "GC task thread#1 (ParallelGC)" os_prio=0 tid=0x00000000034fd800 nid=0x765c runnable

     "GC task thread#2 (ParallelGC)" os_prio=0 tid=0x00000000034ff000 nid=0x6cb8 runnable

     "GC task thread#3 (ParallelGC)" os_prio=0 tid=0x0000000003500800 nid=0x730c runnable

     "VM Periodic Task Thread" os_prio=2 tid=0x0000000019503000 nid=0x5b64 waiting on condition

     JNI global references: 14


     Found one Java-level deadlock:
     =============================
     "Thread-1":
     waiting to lock monitor 0x00000000180bf838 (object 0x00000000d63250c0, a java.lang.String),
     which is held by "Thread-0"
     "Thread-0":
     waiting to lock monitor 0x000000001a1c6478 (object 0x00000000d63250f8, a java.lang.String),
     which is held by "Thread-1"

     Java stack information for the threads listed above:
     ===================================================
     "Thread-1":
     at com.melo.left.concurrent.DeadLockSample.run(DeadLockSample.java:18)
     - waiting to lock <0x00000000d63250c0> (a java.lang.String)
     - locked <0x00000000d63250f8> (a java.lang.String)
     "Thread-0":
     at com.melo.left.concurrent.DeadLockSample.run(DeadLockSample.java:18)
     - waiting to lock <0x00000000d63250f8> (a java.lang.String)
     - locked <0x00000000d63250c0> (a java.lang.String)

     Found 1 deadlock.

     Heap
     PSYoungGen      total 37888K, used 5909K [0x00000000d6000000, 0x00000000d8a00000, 0x0000000100000000)
     eden space 32768K, 18% used [0x00000000d6000000,0x00000000d65c5430,0x00000000d8000000)
     from space 5120K, 0% used [0x00000000d8500000,0x00000000d8500000,0x00000000d8a00000)
     to   space 5120K, 0% used [0x00000000d8000000,0x00000000d8000000,0x00000000d8500000)
     ParOldGen       total 86016K, used 0K [0x0000000082000000, 0x0000000087400000, 0x00000000d6000000)
     object space 86016K, 0% used [0x0000000082000000,0x0000000082000000,0x0000000087400000)
     Metaspace       used 3430K, capacity 4494K, committed 4864K, reserved 1056768K
     class space    used 368K, capacity 386K, committed 512K, reserved 1048576K
     */
}