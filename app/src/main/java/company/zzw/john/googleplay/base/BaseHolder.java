package company.zzw.john.googleplay.base;

import android.view.View;

/**
 * Created by ZheWei on 2016/5/28.
 */
public abstract class BaseHolder<T> {
    public View mHolderView;

    public T mData;

    public BaseHolder() {
        //初始化根部局
        mHolderView = initHolderView();
        //绑定tag
        mHolderView.setTag(this);
    }

    /**
     * :::设置数据和刷新视图
     * :::需要设置数据和刷新视图的时候调用
     */
    public void setDataAndRefreshHolderView(T data) {
        //保存数据
        mData = data;
        //刷新视图
        refreshHolderView(data);
    }



    /**
     * ::初始化viewHolder的视图
     * :::初始化的时候必须被调用的
     */
    public abstract View initHolderView();

    /**
     * 刷新holderView
     * 填充数据  -->子类必须实现的方法
     */
    protected abstract void refreshHolderView(T data);
}
