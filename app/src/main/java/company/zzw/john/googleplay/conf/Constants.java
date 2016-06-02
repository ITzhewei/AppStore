package company.zzw.john.googleplay.conf;

import company.zzw.john.googleplay.utils.LogUtils;

/**
 * Created by ZheWei on 2016/5/26.
 */
public class Constants {

    public static final int DEBUGLEVEL = LogUtils.LEVEL_ALL; // 显示所有的日志, OFF关闭日志
    public static final int PROTICOLOUTTIME = 5 * 60 * 1000;//协议的过期事件->缓存文件的过期事件

    /**
     * 网络请求的url
     */
    public static class URLS {
        public static final String BASE_URL = "http://1510269g0z.iask.in:29828/GooglePlayServer/";
        public static final String IMAGE_URL = BASE_URL + "image?name=";
        public static final int PAGER_SIZE = 20; // 加载更多数据的条数
    }
}
