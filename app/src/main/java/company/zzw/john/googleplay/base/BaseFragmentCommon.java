package company.zzw.john.googleplay.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ZheWei on 2016/5/27.
 */
public abstract class BaseFragmentCommon extends Fragment {

    //共有的属性
    //共有的方法

    @Override
    public void onCreate(Bundle savedInstanceState) {
        init();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initView(inflater,container,savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        initData();
        initListener();
        super.onActivityCreated(savedInstanceState);
    }

    public void init() {

    }

    //抽象方法,子类必须实现的方法
    public abstract View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    public void initData() {

    }

    public void initListener() {

    }
}
