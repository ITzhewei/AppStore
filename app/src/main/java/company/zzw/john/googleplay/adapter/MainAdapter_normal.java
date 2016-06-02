package company.zzw.john.googleplay.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import company.zzw.john.googleplay.utils.UIUtils;

/**
 * Created by ZheWei on 2016/5/26.
 */
public class MainAdapter_normal extends PagerAdapter {

    private String[] mMainTitles;

    public MainAdapter_normal(String[] mainTitles) {
        mMainTitles = mainTitles;
    }

    @Override
    public int getCount() {
        if (mMainTitles != null) {
            return mMainTitles.length;
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        TextView tv = new TextView(UIUtils.getContext());
        tv.setText(mMainTitles[position]);
        container.addView(tv);
        return tv;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    /**必须重写,不然会报空指针*/
    @Override
    public CharSequence getPageTitle(int position) {
        return mMainTitles[position];
    }
}
