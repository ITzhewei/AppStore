package company.zzw.john.googleplay.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import company.zzw.john.googleplay.R;
import company.zzw.john.googleplay.adapter.Mainadapter_State;
import company.zzw.john.googleplay.base.BaseFragment;
import company.zzw.john.googleplay.factory.FragmentFactory;
import company.zzw.john.googleplay.utils.UIUtils;

public class MainActivity extends ActionBarActivity {

    @ViewInject(R.id.tabs)
    private PagerSlidingTabStrip tabs;

    @ViewInject(R.id.vp_)
    private ViewPager vp_;

    @ViewInject(R.id.dl_main)
    private DrawerLayout dl_main;
    private ActionBar mActionBar;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);
        initActionbar();
        initView();
        initData();
        initListener();
    }

    /**初始化 顶部 菜单*/
    private void initActionbar() {
        mActionBar = getSupportActionBar();
        mActionBar.setLogo(R.mipmap.ic_launcher); //设置logo
        mActionBar.setTitle("GooglePlay");

        //显示返回按钮
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mToggle = new ActionBarDrawerToggle(this, dl_main, R.string.open, R.string.close);
        //同步监听
        mToggle.syncState();
        //绑定
        dl_main.setDrawerListener(mToggle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                mToggle.onOptionsItemSelected(item);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView() {

    }

    private void initData() {

        //设置viewpager的adapter
        String[] mMainTitles = UIUtils.getStringArr(R.array.main_titles);
        //        vp_.setAdapter(new MainAdapter_normal(mMainTitles));
        //        vp_.setAdapter(new MainAdapter(getSupportFragmentManager(),mMainTitles));
        vp_.setAdapter(new Mainadapter_State(getSupportFragmentManager(), mMainTitles));
        //绑定pagerSlidingtabs
        tabs.setViewPager(vp_);
    }

    private void initListener() {
        //设置tab的事件
        tabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //完成数据的加载
                BaseFragment fragment = FragmentFactory.getFragment(position);
                if (fragment != null) {
                    fragment.getLoadingpager().loadData();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
