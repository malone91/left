package com.melo.left.netty;

/**
 * 一个线程处理多个的客户端连接，就会用到Selector选择器
 * 它能够检测多个注册的通道上是否有事件发生，
 * 多个channel以事件的方式可以注册到同一个selector上
 * 如果有事件发生，便获取事件然后针对每个事件进行相应的处理，
 * 这样一个单线程就可以去管理多个通道，也就是管理多个连接和请求
 *
 * 只有在连接/通道真正有读写事件发生时，才会进行读写，大大减少了系统开销，
 * 不必为每一个连接都创建一个线程，不用维护多个线程，还避免了多个线程上下文切换
 * 的开销
 *
 * NIO的IO线程NioEventLoop聚合了selector，同时处理成千上百个客户端连接
 * 当线程从某客户端socket通道进行读写数据时，若没有数据可用，该线程可以进行其他任务
 *
 * 线程通常将非阻塞IO的空闲时间用于在其他通道上执行IO操作，单独线程可以管理多个输入输出通道
 *
 * 读写操作都是非阻塞的，充分提升IO线程的运行效率，避免频繁IO阻塞导致的线程挂起
 *
 * 抽象类，.open获取实例对象
 * select方法：监控所有注册的通道，当其中有IO操作可以进行时，将对应的
 * SelectKey加入到内部集合中并返回，参数来设置超时时间
 * selectKeys：从内部集合中获取，是SelectorImpl实现类中的成员变量
 * 一旦监听到哪个channel有事件发生了，就会把这个SelectorKey拿到，就可以通过selectorKey
 * 反向得到这个channel  SelectorKey是跟channel关联的，有个方法叫channel
 *
 * 总体来说就是，Selector是跟Thread绑定的，调用select方法，返回一个selectKey集合,
 * 反向获取channel信息
 *
 * select方法是阻塞的，直到注册的channel通道有至少一个事件发生，才会返回
 * 非阻塞的就是select(long )带参数的
 * selectNow 非阻塞的select 如果现在没有得到，立即返回
 * selectKeys方法：从内部集合中获取所有的SelectKey无论是否有事件发生
 *
 *
 * Selector SelectionKey ServerSocketChannel SocketChannel四者关系
 * ：当客户端连接时，会通过ServerSocketChannel得到SocketChannel
 * 将得到的SocketChannel注册到selector上 SocketChannel#register(selector sel, int ops)方法，可以注册多个SocketChannel
 * 注册后返回一个SelectionKey，会和Selector关联
 * Selector会通过select方法进行监听，返回有事件发生的通道的个数
 * 进而进一步得到各个 SelectorKey（有事件发生的）
 * 通过SelectorKey 的channel 获取SocketChannel 进行业务操作
 *
 */
public class SelectorDemo {
}
