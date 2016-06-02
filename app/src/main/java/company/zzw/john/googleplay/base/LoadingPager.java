package company.zzw.john.googleplay.base;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import company.zzw.john.googleplay.R;
import company.zzw.john.googleplay.factory.ThreadPoolFactory;
import company.zzw.john.googleplay.utils.UIUtils;

/**
 * Created by ZheWei on 2016/5/27.
 */

/**
 * //页面显示分析
 * //Fragment共性-->页面共性-->视图的展示
 * /**
 * 任何应用其实就只有4种页面类型
 * ① 加载页面
 * ② 错误页面
 * ③ 空页面
 * ④ 成功页面
 * <p/>
 * ①②③三种页面一个应用基本是固定的
 * 每一个fragment/activity对应的页面④就不一样
 * 进入应用的时候显示①,②③④需要加载数据之后才知道显示哪个
 */
public abstract class LoadingPager extends FrameLayout {

    public static final int STATE_LOADING = 0; //记载状态
    public static final int STATE_EMPTY = 1;   //空状态
    public static final int STATE_ERROR = 2;  //错误状态
    public static final int STATE_SUCCESS = 3; //成功状态
    private static final int STATE_UNKNOW = -1;
    private int mCurState = STATE_UNKNOW;//默认情况下

    private View mLoadingView;
    private View mErrorView;
    private View mEmptyView;
    private View mSuccessView;

    public LoadingPager(Context context) {
        super(context);
        initCommonView();
    }

    /**
     * ::: 初始化常规视图
     * :::LoadingPager初始化的时候
     */
    private void initCommonView() {
        // ① 加载页面
        mLoadingView = View.inflate(UIUtils.getContext(), R.layout.pager_loading, null);
        this.addView(mLoadingView);
        //② 错误页面
        mErrorView = View.inflate(UIUtils.getContext(), R.layout.pager_error, null);
        mErrorView.findViewById(R.id.error_btn_retry).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //重新加载数据
                loadData();
            }
        });
        this.addView(mErrorView);
        //③ 空页面
        mEmptyView = View.inflate(UIUtils.getContext(), R.layout.pager_empty, null);
        this.addView(mEmptyView);

        refreshUI();
    }

    /**
     * 根据当前状态显示不同的view
     * 1.LoadingPager初始化的时候
     * 2.真正加载数据刷新完成的时候,刷新一次UI
     */
    private void refreshUI() {
        //控制Loading视图
        mLoadingView.setVisibility((mCurState == STATE_LOADING) || (mCurState == STATE_UNKNOW) ? VISIBLE : GONE);
        //控制Empty视图
        mEmptyView.setVisibility((mCurState == STATE_EMPTY) ? VISIBLE : GONE);
        //控制error视图
        mErrorView.setVisibility((mCurState == STATE_ERROR) ? VISIBLE : GONE);

        //控制success的视图
        if (mSuccessView == null && mCurState == STATE_SUCCESS) {
            //创建成功视图
            mSuccessView = initSuccessView();
            this.addView(mSuccessView);
        }
        if (mSuccessView != null) {
            mSuccessView.setVisibility((mCurState == STATE_SUCCESS) ? VISIBLE : GONE);
        }


    }


    // 数据加载的流程

    /**
     ① 触发加载  	进入页面开始加载/点击某一个按钮的时候加载
     ② 异步加载数据  -->显示加载视图
     ③ 处理加载结果
     ① 成功-->显示成功视图
     ② 失败
     ① 数据为空-->显示空视图
     ② 数据加载失败-->显示加载失败的视图
     */

    /**
     * :::触发加载数据
     * :::暴露给外界调用, 其实就是外界 触发加载数据
     */
    public void loadData() {
        if (mCurState != STATE_SUCCESS && mCurState != STATE_LOADING) {
            mCurState = STATE_LOADING;
            refreshUI();
            //异步加载数据
//            new Thread(new LoadDataTask()).start();
            //使用线程池来管理 线程
            ThreadPoolFactory.getNormalThreadPool().execute(new LoadDataTask());
        }
    }

    private class LoadDataTask implements Runnable {
        @Override
        public void run() {
            //② 异步加载数据
            LoadedResult tempState = initData();
            //处理加载的结果
            mCurState = tempState.getState();
            //刷新UI
            UIUtils.postTaskSafely(new Runnable() {
                @Override
                public void run() {
                    refreshUI();
                }
            });
        }
    }

    /**
     * desc::: 真正的加载数据,必须实现的方法,但是不知道怎么实现,让子类实现,定义为抽象的
     * call::: loadData()方法被调用的时候
     *
     * @return int  返回的状态码
     */
    public abstract LoadedResult initData();

    /**
     * :::返回成功视图
     * :::真正加载数据完成之后,并且数据加载成功,我们必须告知具体的成功视图
     *
     * @return
     */
    public abstract View initSuccessView();

    /**
     * 定义一个枚举类型
     * 使得initData()方法的返回值只能是 枚举 的 三个int值
     */
    public enum LoadedResult {
        SUCCESS(STATE_SUCCESS), ERROR(STATE_ERROR), EMPTY(STATE_EMPTY);
        int state;

        public int getState() {
            return state;
        }

        LoadedResult(int state) {
            this.state = state;
        }
    }
}
