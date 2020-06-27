package com.melo.left.threadlocal;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * DateTimeFormatter
 * 或者
 * privatestatic ThreadLocal<DateFormat> threadLocal
 * = new ThreadLocal<DateFormat>()
 * {@Overrideprotected DateFormat initialValue()
 * {returnnew SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); } };
 *
 *  SimpleDateFormat 中的format方法在执行过程中，
 *  会使用一个成员变量calendar来保存时间。
 *  拿到的calendar.getTime得到的时间就是线程B改过之后的。
 *
 *  假设一个线程A刚执行完calendar.setTime把时间设置成2020-05-07，
 *  这个线程还没执行完，线程B又执行了calendar.setTime把时间改成了2020-06-07。
 *  这时候线程A继续往下执行，
 *
 *  SimpleDateFormat 是线程不安全的，
 *  这也是第二点原因，
 *  这个在JDK文档中已经明确表明了SimpleDateFormat不应该用在多线程场景中

 除了format方法以外，SimpleDateFormat的parse方法也有同样的问题。

 所以，不要把SimpleDateFormat作为一个共享变量使用
 * SimpleDateFormat不是线程安全的
 * ThreadLocal让不同的线程有不同的变量
 *
 * 避免共享变量的两种解决方案，在高并发情况下，使用局部变量会频繁创建对象，
 * 使用threadlocal也是针对线程创建新变量，都是针对线程维度，
 * threadlocal并未体现出什么优势，为什么还要用threadlocal
 *
 * threadlocal=线程数，局部变量=调用量，差距太大了
 *
 * ThreadLocal内的变量是线程级别的，
 * 而异步编程意味着线程不同，不同线程的变量不可以共享
 */
public class SafeDateFormat {

    static final ThreadLocal<DateFormat> tl = ThreadLocal.withInitial(
            () -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    );

    static DateFormat get() {
        return tl.get();
    }
}
