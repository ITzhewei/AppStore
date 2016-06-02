package company.zzw.john.googleplay.holder;

import android.view.View;
import android.widget.LinearLayout;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import company.zzw.john.googleplay.R;
import company.zzw.john.googleplay.utils.UIUtils;

/**
 * Created by ZheWei on 2016/5/29.
 */
public class LoadMoreHolder extends company.zzw.john.googleplay.base.BaseHolder<Integer> {

    @ViewInject(R.id.item_loadmore_container_loading)
    LinearLayout mLoading;

    @ViewInject(R.id.item_loadmore_container_retry)
    LinearLayout mRetry;

    public static final int STATE_LOADING = 0;
    public static final int STATE_RETRY = 1;
    public static final int STATE_NONE = 2;


    @Override
    public View initHolderView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.item_loadmore, null);

        ViewUtils.inject(this, view);
        return view;
    }

    @Override
    public void refreshHolderView(Integer state) {
        mLoading.setVisibility(View.GONE);
        mRetry.setVisibility(View.GONE);
        switch (state){
            case STATE_LOADING:
                mLoading.setVisibility(View.VISIBLE);
                break;
            case STATE_RETRY:
                mRetry.setVisibility(View.VISIBLE);
                break;
            case STATE_NONE:
                break;
            default:
                break;
        }
    }
}
