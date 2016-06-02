package company.zzw.john.googleplay.utils;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;

import company.zzw.john.googleplay.base.BaseApplication;

/**
 * Created by ZheWei on 2016/5/26.
 */
public class UIUtils  {

    /**得到上下文*/
    public static Context getContext(){
        return BaseApplication.getmContext();
    }

    /**得到Resource对象*/
    public static Resources getResource(){
        return   getContext().getResources();
    }

    /**得到String.xml的字符串*/
    public static String getString(int resId){
        return getResource().getString(resId);
    }
    /**得到String.xml的字符串数组*/
    public static String[] getStringArr(int resId){
        return  getResource().getStringArray(resId);
    }
    /** 得到color.xml中的颜色*/
    public static int getColor(int resId){
        return getResource().getColor(resId);
    }

    /**得到应用程序的包名*/
    public static String getpackageName(){
        return getContext().getPackageName();
    }

    /**得到主线程的id*/
    public static long getMainThreadId(){
        return BaseApplication.getmMainThreadId();
    }

    /**得到主线程的handler*/
    public static Handler getHandler(){
        return BaseApplication.getHandler();
    }

    /**安全的执行一个任务*/
    public static void postTaskSafely(Runnable task){
        //当前线程的id
        int curThreadid = android.os.Process.myTid();
        //如果当前线程是主线程
        if (curThreadid==getMainThreadId()){
            task.run();
        }else {
            //如果当前线程不是主线程
            getHandler().post(task);
        }
    }



}
