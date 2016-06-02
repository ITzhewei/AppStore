package company.zzw.john.googleplay.fragment;

import android.graphics.Color;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;

import java.util.ArrayList;
import java.util.List;

import company.zzw.john.googleplay.adapter.SuperBaseAdapter;
import company.zzw.john.googleplay.base.BaseFragment;
import company.zzw.john.googleplay.base.BaseHolder;
import company.zzw.john.googleplay.base.LoadingPager;
import company.zzw.john.googleplay.domain.HomeBean;
import company.zzw.john.googleplay.holder.HomeHolder;
import company.zzw.john.googleplay.protocol.HomeProtocol;
import company.zzw.john.googleplay.utils.UIUtils;

/**
 * Created by ZheWei on 2016/5/26.
 */
public class HomeFragment extends BaseFragment {

    private ArrayList<HomeBean.ListEntity> mDatas; // listview 的数据源
    private ArrayList<String> pictures;  //轮播图

    @Override
    public LoadingPager.LoadedResult initData() {

        HomeProtocol protocol = new HomeProtocol();
        HomeBean homeBean = null;
        try {
            homeBean = protocol.loadData(0);
        } catch (Exception e) {
            e.printStackTrace();
            return LoadingPager.LoadedResult.ERROR;
        }
        LoadingPager.LoadedResult loadedResult = checkState(homeBean);
        LoadingPager.LoadedResult loadedResult1 = checkState(homeBean.list);
        // 判定是否为空
        if (loadedResult != LoadingPager.LoadedResult.SUCCESS || loadedResult1 != LoadingPager.LoadedResult.SUCCESS) {
            return LoadingPager.LoadedResult.EMPTY;
        }

        if (homeBean == null || homeBean.list == null || homeBean.list.size() == 0) {
            return LoadingPager.LoadedResult.EMPTY;
        }
        //设置数据
        mDatas = homeBean.list;
        pictures = homeBean.picture;
        return LoadingPager.LoadedResult.SUCCESS;


    }

    @Override
    public View initSuccessView() {
        //返回成功的视图
        ListView listView = new ListView(UIUtils.getContext());

        //简单的设置
        listView.setCacheColorHint(Color.TRANSPARENT);
        //        listView.setFastScrollEnabled(true); //设置右侧的滚动条可以滚动
        listView.setAdapter(new HomeAdapter(mDatas, listView));
        return listView;
    }


    private class HomeAdapter extends SuperBaseAdapter<HomeBean.ListEntity> {


        public HomeAdapter(ArrayList<HomeBean.ListEntity> dataSource, AbsListView absListView) {
            super(dataSource, absListView);
        }

        @Override
        public BaseHolder<HomeBean.ListEntity> getSpecialHolder() {

            return new HomeHolder();
        }

        @Override
        public void onNormalItemClick(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(UIUtils.getContext(), "点击了", Toast.LENGTH_SHORT).show();
            super.onNormalItemClick(parent, view, position, id);
        }

        @Override
        public List<HomeBean.ListEntity> onLoadMore() throws HttpException {
            HomeProtocol protocol = new HomeProtocol();
            HomeBean homeBean = null;
            try {
                homeBean = protocol.loadData(mDatas.size());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (homeBean == null || homeBean.list == null || homeBean.list.size() == 0) {
                return null;
            }
            return homeBean.list;
        }
    }
}
