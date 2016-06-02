package company.zzw.john.googleplay.utils;

import android.view.View;

import com.lidroid.xutils.BitmapUtils;

/**
 * Created by ZheWei on 2016/5/29.
 */
public class BitmapHelper {
    public static BitmapUtils bitmapUtils;

    static {
        if (bitmapUtils == null) {
            bitmapUtils = new BitmapUtils(UIUtils.getContext());
        }
    }

    public static <T extends View> void display(T container, String uri) {
        bitmapUtils.display(container, uri);
    }
}
