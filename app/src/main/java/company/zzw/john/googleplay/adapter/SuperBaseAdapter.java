package company.zzw.john.googleplay.adapter;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.lidroid.xutils.exception.HttpException;

import java.util.ArrayList;
import java.util.List;

import company.zzw.john.googleplay.base.BaseHolder;
import company.zzw.john.googleplay.conf.Constants;
import company.zzw.john.googleplay.factory.ThreadPoolFactory;
import company.zzw.john.googleplay.holder.LoadMoreHolder;
import company.zzw.john.googleplay.utils.UIUtils;

/**
 * Created by ZheWei on 2016/5/28.
 */
public abstract class SuperBaseAdapter<T> extends BaseAdapter implements AdapterView.OnItemClickListener {

    public ArrayList<T> mDataSource = new ArrayList<>(); //利用泛型
    private static final int VIEWTYPE_LOADINGMORE = 0;
    private static final int VIEWTYPE_NORMAL = 1;
    private LoadMoreHolder mLoadMoreHolder;
    private LoadMoreTask mLoadMoreTask;
    private AbsListView mAbsListView;

    public SuperBaseAdapter(ArrayList<T> dataSource, AbsListView absListView) {
        mDataSource = dataSource;
        absListView.setOnItemClickListener(this);
    }

    @Override
    public int getCount() {
        if (mDataSource != null) {
            return mDataSource.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (mDataSource != null) {
            return mDataSource.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //    listview里边可以显示几种viewType
    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount() + 1;//2
    }

    @Override
    public int getItemViewType(int position) {
        //如果滑到底部,那么显示的view就是 加载更多
        if (position == getCount() - 1) {  //滑到底部
            return VIEWTYPE_LOADINGMORE;
        } else {
            return VIEWTYPE_NORMAL;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseHolder<T> holder = null;

        if (convertView == null) {
            if (getItemViewType(position) == VIEWTYPE_LOADINGMORE) { //如果是加载更多那么就显示 加载更多的view
                holder = (BaseHolder<T>) getLoadingMoreHOlder();
            } else {
                holder = getSpecialHolder();
            }
        } else {
            holder = (BaseHolder) convertView.getTag();
        }
        //数据展示
        if (getItemViewType(position) == VIEWTYPE_LOADINGMORE) {
            // 加载更多视图
            if (hasLoadMore()) {
                perFormLoadingMore();
            } else {
                mLoadMoreHolder.setDataAndRefreshHolderView(LoadMoreHolder.STATE_NONE);
            }
        } else {
            // 普通情况 ,还是和之前一样
            holder.setDataAndRefreshHolderView(mDataSource.get(position));
        }

        return holder.mHolderView;
    }


    /**
     * 处理item的点击事件
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (getItemViewType(position) == VIEWTYPE_LOADINGMORE) {
            //重新加载更多
            perFormLoadingMore();
        } else {
            onNormalItemClick(parent, view, position, id);
        }
    }

    /**
     * 普通的item的点击事件
     * 如果子类需要处理item的点击事件,就复写这个方法
     */
    public void onNormalItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    /**
     * 默认返回true
     * 子类可以复写该方法,如果子类返回false,说明没有加载更多的条目,就不会加载更多
     *
     * @return
     */
    private boolean hasLoadMore() {
        return true;
    }


    /**
     * 抽象方法 必须实现  返回具体的holder的子类
     *
     * @return
     */
    protected abstract BaseHolder<T> getSpecialHolder();

    /**
     * @return 返回一个加载更多的 holder
     */
    @NonNull
    private LoadMoreHolder getLoadingMoreHOlder() {
        if (mLoadMoreHolder == null) {
            mLoadMoreHolder = new LoadMoreHolder();
        }
        return mLoadMoreHolder;
    }

    /**
     * 滑动到底部之后请求加载更多数据
     */
    private void perFormLoadingMore() {
        if (mLoadMoreTask == null) {
            //先进行当前视图为加载中
            mLoadMoreHolder.setDataAndRefreshHolderView(LoadMoreHolder.STATE_LOADING);
            //开启一个线程池中的线程去加载数据
            mLoadMoreTask = new LoadMoreTask();
            ThreadPoolFactory.getNormalThreadPool().execute(mLoadMoreTask);
        }
    }

    /**
     * 进行加载更多数据的任务
     */
    private class LoadMoreTask implements Runnable {
        @Override
        public void run() {
            //真正的请求网络加载更多数据
            List<T> moreDatas = null;
            int state = LoadMoreHolder.STATE_LOADING;
            try {
                moreDatas = onLoadMore();
                //得到返回数据,并处理结果
                if (moreDatas == null) {
                    state = LoadMoreHolder.STATE_NONE;
                } else {
                    //加入规定每页返回20条
                    // 如果大于20条,继续进行加载,小鱼20条返回值
                    if (moreDatas.size() < Constants.URLS.PAGER_SIZE) {
                        state = LoadMoreHolder.STATE_NONE;
                    } else {

                    }
                }
            } catch (HttpException e) {
                e.printStackTrace();
                state = LoadMoreHolder.STATE_RETRY;
            }

            /**在主线程中进行刷新视图*/
            final int tempState = state;
            final List<T> finalMoreDatas = moreDatas;
            UIUtils.postTaskSafely(new Runnable() {
                @Override
                public void run() {
                    //刷新loadmore视图
                    mLoadMoreHolder.setDataAndRefreshHolderView(tempState);
                    //刷新listiew视图,返回加载更多之后得到的数据,mDatas.addAll()
                    if (finalMoreDatas != null) {
                        mDataSource.addAll(finalMoreDatas); // 数据源进行更新
                        //刷新listview
                        notifyDataSetChanged();
                    }
                }
            });

            mLoadMoreTask = null;
        }

    }

    /**
     * 子类实现的加载更多数据的方法
     * 可有可无,如果子类需要加载更多就调用这个方法
     */
    public List<T> onLoadMore() throws HttpException {
        return null;
    }
}


