package material.danny_jiang.com.xinxingmovie.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import material.danny_jiang.com.xinxingmovie.R;
import material.danny_jiang.com.xinxingmovie.adapter.FunnyHumourAdapter;
import material.danny_jiang.com.xinxingmovie.bean.FunnyHumourBean;
import material.danny_jiang.com.xinxingmovie.network.Requestor;

/**
 * A simple {@link Fragment} subclass.
 */
public class FunnyHumourFragment extends BaseFragment {

    private ListView listView;
    private List<FunnyHumourBean.T1457069041911Bean> funnyHumourBeans = new ArrayList<>();
    private FunnyHumourAdapter adapter;

    public static FunnyHumourFragment newInstance(String url) {
        FunnyHumourFragment funnyHumourFragment = new FunnyHumourFragment();

        Bundle bundle = new Bundle();
        bundle.putString("url", url);

        funnyHumourFragment.setArguments(bundle);

        return funnyHumourFragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = ((ListView) view.findViewById(R.id.list_FunnyHumour));

        adapter = new FunnyHumourAdapter(getActivity(), funnyHumourBeans);

        listView.setAdapter(adapter);
    }

    @Override
    public void getNetwork(String url, Handler handler) {
        Requestor.requestJsonObject(url, handler, FunnyHumourBean.class);
    }

    @Override
    public int getFragmentLayoutId() {
        return R.layout.fragment_funny_humour;
    }

    @Override
    protected void processMessage(Message msg) {
        Object obj = msg.obj;
        if (obj instanceof FunnyHumourBean) {
            FunnyHumourBean funnyRecommendBean = ((FunnyHumourBean) obj);

            List<FunnyHumourBean.T1457069041911Bean> list = funnyRecommendBean.getT1457069041911();

            if (list != null && list.size() > 0) {
                funnyHumourBeans.addAll(list);
                adapter.notifyDataSetChanged();
            }
        }
    }

}
