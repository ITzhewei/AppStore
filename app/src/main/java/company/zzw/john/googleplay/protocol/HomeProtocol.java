package company.zzw.john.googleplay.protocol;

import com.google.gson.Gson;

import company.zzw.john.googleplay.domain.HomeBean;

/**
 * 协议进行简单的封装
 * <p>
 * Created by ZheWei on 2016/6/1.
 */
public class HomeProtocol extends BaseProtocol<HomeBean> {

    @Override
    public String getInterfaceKey() {
        return "home";
    }

    @Override
    protected HomeBean parseBean(String result) {
        Gson gson = new Gson();
        HomeBean homeBean = gson.fromJson(result, HomeBean.class);
        return homeBean;
    }
}
