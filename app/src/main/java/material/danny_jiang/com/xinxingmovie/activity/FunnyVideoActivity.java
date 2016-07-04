package material.danny_jiang.com.xinxingmovie.activity;

import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;

import java.util.ArrayList;
import java.util.List;

import material.danny_jiang.com.xinxingmovie.R;
import material.danny_jiang.com.xinxingmovie.bean.FunnyTitleBean;
import material.danny_jiang.com.xinxingmovie.entity.TabEntity;
import material.danny_jiang.com.xinxingmovie.fragment.FunnyHumourFragment;
import material.danny_jiang.com.xinxingmovie.fragment.FunnyRecommendFragment;
import material.danny_jiang.com.xinxingmovie.fragment.SimpleCardFragment;
import material.danny_jiang.com.xinxingmovie.network.Requestor;
import material.danny_jiang.com.xinxingmovie.network.VolleySingleTon;
import material.danny_jiang.com.xinxingmovie.utils.MyConstans;

public class FunnyVideoActivity extends BaseActivity {

    private final List<String> mTitles = new ArrayList<>();

    private ArrayList<Fragment> mFragments = new ArrayList<>();

    private SlidingTabLayout tablayout_10;
    private ViewPager vp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getNetData();

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_funny_video;
    }

    @Override
    public void processMessage(Message msg) {
        super.processMessage(msg);
        Object obj = msg.obj;

        if (obj != null && obj instanceof ArrayList) {
            Log.e("TAG", "processMessage: success");
            List<FunnyTitleBean> funnyTitleBeans = (List<FunnyTitleBean>) obj;

            if (funnyTitleBeans != null && funnyTitleBeans.size() > 0) {
                instantialViews(funnyTitleBeans);
            }

            for (FunnyTitleBean funnyTitleBean : funnyTitleBeans) {
                String tname = funnyTitleBean.getTname();
                String tid = funnyTitleBean.getTid();
                Log.e("TAG", "processMessage: tname is" + tname);
            }
        }
    }

    private void instantialViews(List<FunnyTitleBean> funnyTitleBeans) {
        for (FunnyTitleBean funnyTitleBean : funnyTitleBeans) {
            String tName = funnyTitleBean.getTname();
            mTitles.add(tName);
            //mFragments.add(SimpleCardFragment.getInstance(tName));
            if (getExactFragment(tName) != null) {
                mFragments.add(getExactFragment(tName));
            }
        }

        vp = ((ViewPager) findViewById(R.id.vp));
        vp.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        tablayout_10 = ((SlidingTabLayout) findViewById(R.id.tl_10));
        tablayout_10.setViewPager(vp);
    }

    private Fragment getExactFragment(String tName) {
        Fragment fragment = null;
        if ("推荐".equals(tName)) {
            fragment = FunnyRecommendFragment.newInstance(MyConstans.FUNNY_VIDEO_RECOMMEND);
        } else if ("搞笑".equals(tName)) {
            fragment = FunnyHumourFragment.newInstance(MyConstans.FUNNY_VIDEO_HUMOUR);
        }
        return fragment;
    }

    private void getNetData() {
        Requestor.requestJsonArray(MyConstans.FUNNY_VIDEO_TITLE, handler, FunnyTitleBean.class);
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }
}
