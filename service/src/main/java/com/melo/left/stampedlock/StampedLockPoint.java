package com.melo.left.stampedlock;

import java.util.concurrent.locks.StampedLock;

/**
 * 大量读写的场景
 *  StampedLock 提供的乐观读，是允许一个线程获取写锁的，也就是说不是所有的写操作都被阻塞。
 *
 *  是否会想到数据库的乐观锁呢？
 *  select id, ..., version from t where id = 999
 *  update t set version = version + 1 where id = 999 and version = 9
 *  这个version字段好比StampedLock中的stamp
 *
 *  如果是集群部署，需要使用数据库的乐观锁
 *
 *  StampLock不支持重入。悲观读锁、写锁都不支持条件变量
 *  使用 StampedLock 一定不要调用中断操作，
 *  如果需要支持中断功能，
 *  一定使用可中断的悲观读锁 readLockInterruptibly() 和写锁 writeLockInterruptibly()
 *  否则会造成CPU飙升
 *
 * 使用final是个好习惯，告诉编译器通过指令重拍使劲优化
 *
 * volatile无法保证两个变量的一致性
 */
public class StampedLockPoint {

    private int x, y;
    final StampedLock stampedLock = new StampedLock();

    /**
     * 读模板
     *
     * 计算到原点的距离
     * 无此升级则需要在一个循环里反复执行乐观读，直到执行乐观读期间没有写操作
     *
     * 乐观读的主要目的是数据读取过程中是否发生了变化，
     * 如果该数据还需要进行后面的赋值操作，那么肯定是无法保证原子性的，只能通过加锁或cas保证
     * @return
     */
    double calculateDistanceFromOrigin() {
        //该操作是无锁的
        long stamp = stampedLock.tryOptimisticRead();
        int curX = x;
        int curY = y;
        //判断执行读操作期间是否存在写操作，如果存在则validate返回false
        if (!stampedLock.validate(stamp)) {
            //乐观读升级为悲观读
            stamp = stampedLock.readLock();
            try {
                curX = x;
                curY = y;
            } finally {
                stampedLock.unlockRead(stamp);
            }
        }

        return Math.sqrt(curX * curX + curY * curY);
    }

    public void writeTemplate() {
        long stamp = stampedLock.writeLock();
        try {
            //write shared var
        } finally {
            stampedLock.unlockWrite(stamp);
        }
    }
}
