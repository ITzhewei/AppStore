package company.zzw.john.googleplay.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import company.zzw.john.googleplay.R;
import company.zzw.john.googleplay.base.BaseHolder;
import company.zzw.john.googleplay.conf.Constants;
import company.zzw.john.googleplay.domain.HomeBean;
import company.zzw.john.googleplay.utils.BitmapHelper;
import company.zzw.john.googleplay.utils.StringUtils;
import company.zzw.john.googleplay.utils.UIUtils;

/**
 * Created by ZheWei on 2016/5/28.
 */
public class HomeHolder extends BaseHolder<HomeBean.ListEntity> {


    @ViewInject(R.id.item_appinfo_iv_icon)
    ImageView mIvIcon;

    @ViewInject(R.id.item_appinfo_rb_stars)
    RatingBar mRbStars;

    @ViewInject(R.id.item_appinfo_tv_des)
    TextView mTvDes;

    @ViewInject(R.id.item_appinfo_tv_size)
    TextView mTvSize;

    @ViewInject(R.id.item_appinfo_tv_title)
    TextView mTvTitle;


    public View initHolderView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.item_app_info, null);
        //初始化孩子对象
        ViewUtils.inject(this, view);
        return view;
    }

    @Override
    protected void refreshHolderView(HomeBean.ListEntity data) {
        //设置数据
        mTvTitle.setText(data.name);
        mTvDes.setText(data.des);
        mTvSize.setText(StringUtils.formatFileSize(data.size));
        //设置默认图片
        mIvIcon.setImageResource(R.mipmap.ic_default);
        //加载网络图片
        BitmapHelper.display(mIvIcon, Constants.URLS.IMAGE_URL + data.iconUrl);

        mRbStars.setRating(data.stars);
    }


}
