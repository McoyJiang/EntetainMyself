package material.danny_jiang.com.xinxingmovie.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import material.danny_jiang.com.xinxingmovie.R;
import material.danny_jiang.com.xinxingmovie.activity.MovieDetailActivity;
import material.danny_jiang.com.xinxingmovie.adapter.NewComingAdapter;
import material.danny_jiang.com.xinxingmovie.bean.FunnyTitleBean;
import material.danny_jiang.com.xinxingmovie.bean.NewComingBean;
import material.danny_jiang.com.xinxingmovie.listener.RecyclerTouchListener;
import material.danny_jiang.com.xinxingmovie.network.Requestor;
import material.danny_jiang.com.xinxingmovie.network.VolleySingleTon;
import material.danny_jiang.com.xinxingmovie.utils.MyConstans;
import material.danny_jiang.com.xinxingmovie.view.SpaceItemDecoration;

/**
 * Created by axing on 16/6/26.
 */
public class NewComingFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.rv_newComingFrag)
    RecyclerView recyclerView;
    private RecyclerTouchListener recyclerTouchListener;
    @Bind(R.id.srl_newComingFrag)
    SwipeRefreshLayout swipeRefreshLayout;

    private ArrayList<NewComingBean> newComingBeans = new ArrayList<>();
    private NewComingAdapter newComingAdapter;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            Object obj = msg.obj;
            if (obj instanceof ArrayList) {
                newComingBeans = (ArrayList<NewComingBean>) obj;
                newComingAdapter.addMovies(newComingBeans);
            } else {
                Toast.makeText(getActivity(), "加载失败", Toast.LENGTH_SHORT).show();
            }


            if (swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_new_coming_frag, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(Color.GREEN, Color.RED, Color.BLUE);
        swipeRefreshLayout.setRefreshing(true);
        // 这句话是为了，第一次进入页面的时候显示加载进度条
        swipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));

        newComingAdapter = new NewComingAdapter(getActivity(), newComingBeans);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new SpaceItemDecoration(10));

        recyclerTouchListener = new RecyclerTouchListener(getActivity(), recyclerView);
//        recyclerTouchListener.setIndependentViews(R.id.newComingCover)
//                .setViewsToFade(R.id.newComingCover);
        recyclerTouchListener.setClickable(new RecyclerTouchListener.OnRowClickListener() {
            @Override
            public void onRowClicked(int position) {
                Toast.makeText(getActivity(), "Row " + (position + 1) + " clicked!",
                        Toast.LENGTH_SHORT).show();
                NewComingBean newComingBean = newComingBeans.get(position);
                Log.e("TAG", "onRowClicked: bean is " + newComingBean.getMovieTitle());

                Intent detailIntent = new Intent(getActivity(), MovieDetailActivity.class);
                detailIntent.putExtra("movieName", newComingBeans.get(position).getMovieTitle());
                startActivity(detailIntent);
            }

            @Override
            public void onIndependentViewClicked(int independentViewID, int position) {
                Toast.makeText(getActivity(), "Button in row " + (position + 1) + " clicked!",
                        Toast.LENGTH_SHORT).show();
            }
        });
        recyclerTouchListener.setSwipeOptionViews(R.id.add, R.id.edit, R.id.change);
        recyclerTouchListener.setSwipeable(R.id.rowFG, R.id.rowBG, new RecyclerTouchListener.OnSwipeOptionsClickListener() {
            @Override
            public void onSwipeOptionClicked(int viewID, int position) {
                String message = "";
                if (viewID == R.id.add) {
                    message += "Add";
                } else if (viewID == R.id.edit) {
                    message += "Edit";
                } else if (viewID == R.id.change) {
                    message += "Change";
                }
                message += " clicked for row " + (position + 1);
                Toast.makeText(getActivity(), "message is " + message, Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setAdapter(newComingAdapter);

        recyclerView.addOnItemTouchListener(recyclerTouchListener);

        getNetworkData();
    }

    private void getNetworkData() {
        Requestor.requestNewComingMovie(VolleySingleTon.newInstance().getRequestQueue(),
                MyConstans.getNewComingUrl(), handler);
    }

    @Override
    public void onRefresh() {
        getNetworkData();
    }
}
