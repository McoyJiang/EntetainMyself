package material.danny_jiang.com.xinxingmovie.fragment;

import android.content.Context;
import android.net.Uri;
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
import material.danny_jiang.com.xinxingmovie.adapter.FunnyPetAdapter;
import material.danny_jiang.com.xinxingmovie.bean.FunnyHumourBean;
import material.danny_jiang.com.xinxingmovie.bean.FunnyPetBean;
import material.danny_jiang.com.xinxingmovie.network.Requestor;
import material.danny_jiang.com.xinxingmovie.utils.MyConstans;

/**
 */
public class PetFragment extends BaseFragment {

    private ListView listView;
    private List<FunnyPetBean.T1457069232830Bean> funnyPetBeans = new ArrayList<>();
    private FunnyPetAdapter adapter;

    public PetFragment() {
    }

    public static PetFragment newInstance(String url) {
        PetFragment petFragment = new PetFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        petFragment.setArguments(bundle);
        return petFragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = ((ListView) view.findViewById(R.id.list_FunnyPet));

        adapter = new FunnyPetAdapter(getActivity(), funnyPetBeans);

        listView.setAdapter(adapter);
    }

    @Override
    public void getNetwork(String url, Handler handler) {
        Requestor.requestJsonObject(url, handler, FunnyPetBean.class);
    }

    @Override
    public int getFragmentLayoutId() {
        return R.layout.fragment_pet;
    }

    @Override
    protected void processMessage(Message msg) {
        Object obj = msg.obj;
        if (obj instanceof FunnyPetBean) {
            FunnyPetBean funnyPetBean = ((FunnyPetBean) obj);

            List<FunnyPetBean.T1457069232830Bean> list = funnyPetBean.getT1457069232830();


            if (list != null && list.size() > 0) {
                funnyPetBeans.addAll(list);
                adapter.notifyDataSetChanged();
            }
        }
    }
}
