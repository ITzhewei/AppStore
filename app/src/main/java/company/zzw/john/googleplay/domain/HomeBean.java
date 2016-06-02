package company.zzw.john.googleplay.domain;

import java.util.ArrayList;

/**
 * Created by ZheWei on 2016/5/28.
 */
public class HomeBean {


    public ArrayList<ListEntity> list;
    public ArrayList<String> picture;

    public class ListEntity {
        public String des;            //应用的 描述
        public String downloadUrl;//应用的下载地址
        public String iconUrl;//应用的图片
        public long id;         //应用的 id
        public String name;    //应用的名字
        public String packageName;   //应用的包名
        public long size;     //应用的大小
        public float stars;      //应用的评分
    }
}
