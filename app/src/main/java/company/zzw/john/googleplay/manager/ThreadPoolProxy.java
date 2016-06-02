package company.zzw.john.googleplay.manager;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池代理
 * Created by ZheWei on 2016/5/27.
 * 创建线程池,执行任务,提交任务,   (线程池由 工作线程 和 任务队列 构成)
 */
public class ThreadPoolProxy {

    private ThreadPoolExecutor mExecutor;
    private int corePoolSize;
    private int maximumPoolSize;
    private long keepAliveTime;

    public ThreadPoolProxy(int corePoolSize, int maximumPoolSize, long keepAliveTime) {
        this.corePoolSize = corePoolSize;
        this.maximumPoolSize = maximumPoolSize;
        this.keepAliveTime = keepAliveTime;
    }

    /**
     * 初始化线程池
     */
    private ThreadPoolExecutor initThreadPoolExecutor() {

        if (mExecutor == null) {
            //双重检查加锁   --->更好的单例模式
            synchronized (ThreadPoolExecutor.class) {
                if (mExecutor == null) {
                    TimeUnit unit = TimeUnit.MILLISECONDS; //毫秒
                    BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();

                    ThreadFactory threadFactory = Executors.defaultThreadFactory(); //默认的线程工厂,在jdk1.5之后提供的
                    RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy(); //丢弃任务,并抛出异常
                    mExecutor = new ThreadPoolExecutor(
                            corePoolSize,//这是核心的线程数,也就是工作线程的最大值
                            maximumPoolSize, //这是最大的线程数,也就是 加上 额外线程的最大数
                            keepAliveTime, //这是保持时间,也就是额外线程的保持时间
                            unit, //这是保持时间的单位
                            workQueue, //任务队列(阻塞队列,缓存队列)
                            threadFactory,//线程工厂
                            handler //异常捕获器 --->当额外异常超过最大线程数的时候
                    );
                }
            }

        }
        return mExecutor;
    }


    /**
     * 执行任务
     */
    public void execute(Runnable task) {
        initThreadPoolExecutor();
        mExecutor.execute(task);
    }


    /**
     * 提交任务
     */
    public Future<?> submit(Runnable task) {
        return mExecutor.submit(task);
    }

    /**
     * 移除任务
     */
    public void removeTask(Runnable task) {
        mExecutor.remove(task);
    }
}
