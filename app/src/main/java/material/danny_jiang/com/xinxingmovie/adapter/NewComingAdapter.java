package material.danny_jiang.com.xinxingmovie.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import material.danny_jiang.com.xinxingmovie.R;
import material.danny_jiang.com.xinxingmovie.bean.NewComingBean;

/**
 * Created by axing on 16/6/26.
 */
public class NewComingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final LayoutInflater inflater;
    private Context context;
    private List<NewComingBean> newComingBeans;

    public NewComingAdapter(Context context, List<NewComingBean> newComingBeans) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.newComingBeans = newComingBeans;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_new_coming_item, parent, false);
        return new NewComingHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.e("TAG", "onBindViewHolder: position is " + position);
        NewComingHolder newComingHolder = (NewComingHolder) holder;
        NewComingBean newComingBean = newComingBeans.get(position);
        newComingHolder.newComingTitle.setText(newComingBean.getMovieTitle());
        newComingHolder.newComingDes.setText(newComingBean.getMovieDes());
        if (newComingBean.getMovieCoverUrl() != null) {
            Picasso.with(context).load(newComingBean.getMovieCoverUrl()).into(newComingHolder.newComingCover);
        }
    }

    @Override
    public int getItemCount() {
        return newComingBeans == null ? 0 : newComingBeans.size();
    }

    public void addMovies(ArrayList<NewComingBean> newComingBeans) {
        this.newComingBeans = newComingBeans;
        notifyDataSetChanged();
    }

    class NewComingHolder extends RecyclerView.ViewHolder {

        public TextView newComingDes;
        public ImageView newComingCover;
        public TextView newComingTitle;

        public NewComingHolder(View itemView) {
            super(itemView);
            newComingTitle = ((TextView) itemView.findViewById(R.id.newComingTitle));
            newComingDes = ((TextView) itemView.findViewById(R.id.newComingDes));
            newComingCover = ((ImageView) itemView.findViewById(R.id.newComingCover));
        }

    }
}
