package company.zzw.john.googleplay.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

/**
 * 定义一个全局的盒子,里边放置的对象,属性,方法都是全局可用的
 * Created by ZheWei on 2016/5/26.
 */
public class BaseApplication extends Application {

    //得到上下文
    private static  Context mContext;
    //得到主线程
    private  static Thread mMainThread;
    //得到主线程的id
    private static long mMainThreadId;
    //得到主线程的looper
    private static Looper mLooper;
    //得到handler
    private static Handler mHandler;

    @Override
    public void onCreate() { //程序的入口, 优先于四大组件
        //初始化一些常用的属性
        //上下文
        mContext = getApplicationContext();

        //主线程     //线程池
        mMainThread = Thread.currentThread();

        //主线程id
        mMainThreadId = android.os.Process.myTid();
        //android.os.Process.myTid() --->线程的id
        // android.os.Process.myUid()--->user的id
        // android.os.Process.myPid()--->进程的id

        //主线程looper
        mLooper = getMainLooper();

        //消息机制, 主线程
        mHandler = new Handler();

        super.onCreate();
    }




    public static Context getmContext() {
        return mContext;
    }

    public static Thread getmMainThread() {
        return mMainThread;
    }

    public static long getmMainThreadId() {
        return mMainThreadId;
    }

    public static Looper getmLooper() {
        return mLooper;
    }

    public static Handler getHandler() {
        return mHandler;
    }
}
