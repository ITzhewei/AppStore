package company.zzw.john.googleplay.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import company.zzw.john.googleplay.factory.FragmentFactory;

/**
 * Created by ZheWei on 2016/5/26.
 */
public class MainAdapter extends FragmentPagerAdapter {

    private String[] mMainTitles;

    public MainAdapter(FragmentManager fm, String[] mainTitles) {
        super(fm);
        mMainTitles = mainTitles;
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentFactory.getFragment(position);
    }

    @Override
    public int getCount() {
        if (mMainTitles!=null){
            return mMainTitles.length;
        }
        return 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mMainTitles[position];
    }
}
