package company.zzw.john.googleplay.factory;

import company.zzw.john.googleplay.manager.ThreadPoolProxy;

/**
 * Created by ZheWei on 2016/5/28.
 */
public class ThreadPoolFactory  {

     static ThreadPoolProxy mNormalThreadPool;
     static ThreadPoolProxy mDownLoadThreadPool;

    /**单例模式-->双重检查加锁的非常好的单例*/
    /**得到普通的线程池*/
    public static ThreadPoolProxy getNormalThreadPool(){
        if (mNormalThreadPool==null){
            synchronized (ThreadPoolProxy.class){
                if (mNormalThreadPool==null){
                    mNormalThreadPool=new ThreadPoolProxy(5,5,3000);//线程池,
                }
            }
        }
        return mNormalThreadPool;
    }

    /**得到下载任务的线程池,默认最多同时开启三个线程进行下载任务*/
    public static ThreadPoolProxy getmDownLoadThreadPool(){
        if (mDownLoadThreadPool==null){
            synchronized (ThreadPoolProxy.class){
                if (mDownLoadThreadPool==null){
                    mDownLoadThreadPool=new ThreadPoolProxy(3,3,3000);
                }
            }
        }
        return mDownLoadThreadPool;
    }

}
