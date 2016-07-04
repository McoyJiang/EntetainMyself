package material.danny_jiang.com.xinxingmovie.fragment;


import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import material.danny_jiang.com.xinxingmovie.R;
import material.danny_jiang.com.xinxingmovie.adapter.FunnyRecommendAdapter;
import material.danny_jiang.com.xinxingmovie.bean.FunnyRecommendBean;
import material.danny_jiang.com.xinxingmovie.network.Requestor;

/**
 * A simple {@link Fragment} subclass.
 */
public class FunnyRecommendFragment extends BaseFragment {

    private ListView listView;
    private List<FunnyRecommendBean.T1457069232830Bean> funnyRecommendBeanList = new ArrayList<>();

    private FunnyRecommendAdapter adapter;


    public static FunnyRecommendFragment newInstance(String url) {
        FunnyRecommendFragment funnyRecommendFragment = new FunnyRecommendFragment();

        Bundle bundle = new Bundle();
        bundle.putString("url", url);

        funnyRecommendFragment.setArguments(bundle);

        return funnyRecommendFragment;
    }

    @Override
    public int getFragmentLayoutId() {
        return R.layout.fragment_funny_recommend;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = ((ListView) view.findViewById(R.id.list_FunnyRecommend));

        adapter = new FunnyRecommendAdapter(getActivity(), funnyRecommendBeanList);

        listView.setAdapter(adapter);
    }

    @Override
    public void getNetwork(String url, Handler handler) {
        Requestor.requestJsonObject(url, handler, FunnyRecommendBean.class);
    }

    @Override
    protected void processMessage(Message msg) {
        Object obj = msg.obj;
        if (obj instanceof FunnyRecommendBean) {
            FunnyRecommendBean funnyRecommendBean = ((FunnyRecommendBean) obj);

            List<FunnyRecommendBean.T1457069232830Bean> list = funnyRecommendBean.getT1457069232830();

            if (list != null && list.size() > 0) {
                funnyRecommendBeanList.addAll(list);
                adapter.notifyDataSetChanged();
            }
        }
    }

}
