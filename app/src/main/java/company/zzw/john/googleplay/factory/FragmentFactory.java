package company.zzw.john.googleplay.factory;

import android.util.SparseArray;

import company.zzw.john.googleplay.base.BaseFragment;
import company.zzw.john.googleplay.fragment.AppFragment;
import company.zzw.john.googleplay.fragment.CategoryFragment;
import company.zzw.john.googleplay.fragment.HomeFragment;
import company.zzw.john.googleplay.fragment.HotFragment;
import company.zzw.john.googleplay.fragment.RecommendFragment;
import company.zzw.john.googleplay.fragment.SubjectFragment;
import company.zzw.john.googleplay.fragment.gameFragment;

/**
 * Created by ZheWei on 2016/5/26.
 */
public class FragmentFactory {

    /**
     * <string-array name="main_titles">
     * <item>首页</item>
     * <item>应用</item>
     * <item>游戏</item>
     * <item>专题</item>
     * <item>推荐</item>
     * <item>分类</item>
     * <item>排行</item>
     * </string-array>
     */
    public static final int FRAGMENT_HOME = 0;
    public static final int FRAGMENT_APP = 1;
    public static final int FRAGMENT_GAME = 2;
    public static final int FRAGMENT_SUBJECT = 3;
    public static final int FRAGMENT_RECOMMEND = 4;
    public static final int FRAGMENT_CATEGORY = 5;
    public static final int FRAGMENT_HOT = 6;

    public static SparseArray<BaseFragment> cachesFragments = new SparseArray<>();

    public static BaseFragment getFragment(int position) {


        //        Map<Integer , Fragment> cachesFragmentMap = new HashMap<>();

        BaseFragment fragment = null;
        BaseFragment cacheFragment = cachesFragments.get(position);
        if (cacheFragment != null) {
            return cacheFragment;
        }

        //如果缓存里边有对应的fragment,我们就从这里边直接取出来,
        //        if (cachesFragmentMap.containsKey(position)){
        //            fragment=cachesFragmentMap.get(position);
        //            return fragment;
        //        }
        switch (position) {
            case FRAGMENT_HOME://主页
                fragment = new HomeFragment();
                break;
            case FRAGMENT_APP: //应用
                fragment = new AppFragment();
                break;
            case FRAGMENT_GAME://游戏
                fragment = new gameFragment();
                break;
            case FRAGMENT_SUBJECT://专题
                fragment = new SubjectFragment();
                break;
            case FRAGMENT_RECOMMEND://推荐
                fragment = new RecommendFragment();
                break;
            case FRAGMENT_CATEGORY://分类
                fragment = new CategoryFragment();
                break;
            case FRAGMENT_HOT://排行
                fragment = new HotFragment();
                break;
        }
        //保存对应的fragment
        cachesFragments.put(position, fragment);
        return fragment;
    }
}
