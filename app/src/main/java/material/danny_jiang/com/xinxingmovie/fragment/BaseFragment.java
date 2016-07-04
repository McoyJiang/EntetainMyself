package material.danny_jiang.com.xinxingmovie.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import material.danny_jiang.com.xinxingmovie.R;
import material.danny_jiang.com.xinxingmovie.network.Requestor;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment extends Fragment {

    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null) {
                Log.e("TAG", "handleMessage: fragment msg is " + msg);
                processMessage(msg);
            }
        }
    };

    private String url = "";
    protected int pageIndex = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        url = arguments.getString("url");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(getFragmentLayoutId(), container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getNetwork(String.format(url, pageIndex), handler);
    }

    public abstract void getNetwork(String url, Handler handler);

    public abstract int getFragmentLayoutId();

    protected abstract void processMessage(Message msg);



}
