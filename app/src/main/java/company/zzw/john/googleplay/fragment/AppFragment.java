package company.zzw.john.googleplay.fragment;

import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

import company.zzw.john.googleplay.R;
import company.zzw.john.googleplay.base.BaseFragment;
import company.zzw.john.googleplay.base.LoadingPager;
import company.zzw.john.googleplay.utils.UIUtils;

/**
 * Created by ZheWei on 2016/5/26.
 */
public class AppFragment extends BaseFragment {

    @Override
    public LoadingPager.LoadedResult initData() {
        //执行耗时操作
        SystemClock.sleep(1000);
        LoadingPager.LoadedResult[] arr = {LoadingPager.LoadedResult.SUCCESS, LoadingPager.LoadedResult.ERROR, LoadingPager.LoadedResult.ERROR};
        Random random = new Random();
        int index = random.nextInt(arr.length);
        return arr[index];
    }

    @Override
    public View initSuccessView() {
        TextView tv = new TextView(UIUtils.getContext());
        tv.setText(this.getClass().getSimpleName());
        tv.setTextColor(UIUtils.getColor(R.color.colorPrimaryDark));
        return tv;
    }
}
