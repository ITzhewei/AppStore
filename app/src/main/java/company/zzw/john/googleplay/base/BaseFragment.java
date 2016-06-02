package company.zzw.john.googleplay.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Map;

import company.zzw.john.googleplay.utils.UIUtils;

/**
 * Created by ZheWei on 2016/5/27.
 */
public abstract class BaseFragment extends Fragment {

    private LoadingPager mLoadingpager;

    public LoadingPager getLoadingpager() {
        return mLoadingpager;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mLoadingpager == null) {
            mLoadingpager = new LoadingPager(UIUtils.getContext()) {
                @Override
                public LoadedResult initData() {
                    return BaseFragment.this.initData();
                }

                @Override
                public View initSuccessView() {
                    return BaseFragment.this.initSuccessView();
                }
            };
        }
        //        } else  {
        //            ViewGroup parent = (ViewGroup) mLoadingpager.getParent();
        //            parent.removeView(mLoadingpager);
        //        }
        return mLoadingpager;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * :::真正的加载数据,必须实现的方法,但是不知道怎么实现,让子类实现,定义为抽象的
     * :::正在加载数据完成之后,并且数据加载成功,我们必须告知具体的成功视图
     *
     * @return 返回的状态码
     */
    public abstract LoadingPager.LoadedResult initData();

    /**
     * :::返回成功视图
     * :::正真正加载数据完成之后,并且数据加载成功,我们必须告知具体的成功视图
     *
     * @return
     */
    public abstract View initSuccessView();


    /**
     * @param obj ---> 网络数据json化之后的bean类
     * @return
     */
    public LoadingPager.LoadedResult checkState(Object obj) {
        if (obj == null) {
            return LoadingPager.LoadedResult.EMPTY;
        }
        //list
        if (obj instanceof List) {
            if (((List) obj).size() == 0) {
                return LoadingPager.LoadedResult.EMPTY;
            }
        }
        //map
        if (obj instanceof Map) {
            if (((Map) obj).size() == 0) {
                return LoadingPager.LoadedResult.EMPTY;
            }
        }
        return LoadingPager.LoadedResult.SUCCESS;
    }
}
