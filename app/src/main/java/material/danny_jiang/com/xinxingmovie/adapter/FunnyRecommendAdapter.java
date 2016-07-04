package material.danny_jiang.com.xinxingmovie.adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.VideoView;
import material.danny_jiang.com.xinxingmovie.R;
import material.danny_jiang.com.xinxingmovie.bean.FunnyRecommendBean;

/**
 * Created by axing on 16/7/1.
 */
public class FunnyRecommendAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    private Context context;
    private List<FunnyRecommendBean.T1457069232830Bean> funnyRecommendBeans;

    private int currentPlayPosition = -1;
    private MediaPlayer mp;

    public FunnyRecommendAdapter(Context context, List<FunnyRecommendBean.T1457069232830Bean> funnyRecommendBeans) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.funnyRecommendBeans = funnyRecommendBeans;

        mp = new MediaPlayer();
        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                Log.e("TAG", "onPrepared: ");
                mp.start();
            }
        });
    }

    @Override
    public int getCount() {
        return funnyRecommendBeans == null ? 0 : funnyRecommendBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return funnyRecommendBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.funny_recommend_item, null);

            holder = new ViewHolder();
            holder.title = ((TextView) convertView.findViewById(R.id.funnyRecommendTitle));
            holder.image = ((ImageView) convertView.findViewById(R.id.funnyRecommendCover));
            holder.play = ((ImageView) convertView.findViewById(R.id.funnyRecommendPlay));
            holder.videoView = ((SurfaceView) convertView.findViewById(R.id.funnyRecommendVideo));

            convertView.setTag(holder);
        } else {
            holder = ((ViewHolder) convertView.getTag());
        }

        FunnyRecommendBean.T1457069232830Bean bean = funnyRecommendBeans.get(position);

        String title = bean.getTitle();
        String cover = bean.getCover();
        final String videiUrl = bean.getMp4_url();

        holder.title.setText(title);
        Picasso.with(context)
                .load(cover)
                .into(holder.image);

        ViewGroup.LayoutParams params = holder.videoView.getLayoutParams();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = 250;
        // 3.2 将网络返回的宽高设置到SurfaceView以及ImageView上
        holder.videoView.setLayoutParams(params);
        holder.image.setLayoutParams(params);

        Object tag = holder.image.getTag();
        if (tag != null) {
            int newTag = (int) tag;
            if (newTag == currentPlayPosition && newTag != position) {
                mp.stop();
                currentPlayPosition = -1;
            }
        }
        holder.image.setTag(position);

        if (currentPlayPosition == position) { //说明用户点击了position对应的item
            //隐藏ImageView， 并调用SurfaceView进行播放视频，
            holder.image.setVisibility(View.INVISIBLE);
            holder.play.setVisibility(View.INVISIBLE);
            holder.videoView.setVisibility(View.VISIBLE);
            try {
                mp.reset();
                mp.setDataSource(context, Uri.parse(videiUrl));
                mp.setDisplay(holder.videoView.getHolder());
                mp.prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            holder.image.setVisibility(View.VISIBLE);
            holder.play.setVisibility(View.VISIBLE);
            holder.videoView.setVisibility(View.INVISIBLE);
        }

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断当前是否有视频在播放，如果有则停止MediaPlayer
                if (mp != null && mp.isPlaying()) {
                    mp.stop();
                }
                //赋值currentPlayPosition， 重新刷新ListView
                currentPlayPosition = position;
                //刷新列表
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    class ViewHolder {
        TextView title;
        ImageView image;
        ImageView play;
        SurfaceView videoView;
    }
}
