package material.danny_jiang.com.xinxingmovie.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.support.annotation.IntRange;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.volokh.danylo.video_player_manager.manager.PlayerItemChangeListener;
import com.volokh.danylo.video_player_manager.manager.SingleVideoPlayerManager;
import com.volokh.danylo.video_player_manager.manager.VideoPlayerManager;
import com.volokh.danylo.video_player_manager.meta.MetaData;
import com.volokh.danylo.video_player_manager.ui.MediaPlayerWrapper;
import com.volokh.danylo.video_player_manager.ui.SimpleMainThreadMediaPlayerListener;
import com.volokh.danylo.video_player_manager.ui.UniversalMediaController;
import com.volokh.danylo.video_player_manager.ui.VideoPlayerView;
import com.volokh.danylo.video_player_manager.utils.Logger;

import java.util.List;

import material.danny_jiang.com.xinxingmovie.R;
import material.danny_jiang.com.xinxingmovie.bean.FunnyHumourBean;
import material.danny_jiang.com.xinxingmovie.fragment.FunnyHumourFragment;
import material.danny_jiang.com.xinxingmovie.utils.SquareUtils;
import material.danny_jiang.com.xinxingmovie.view.PieImageView;

/**
 * Created by axing on 16/7/2.
 */
public class FunnyHumourAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    private Context context;
    private List<FunnyHumourBean.T1457069041911Bean> funnyHumourBeans;

    private VideoPlayerManager<MetaData> mVideoPlayerManager = new SingleVideoPlayerManager(new PlayerItemChangeListener() {
        @Override
        public void onPlayerItemChanged(MetaData metaData) {
            Log.e("TAG", "onPlayerItemChanged: ");
        }
    });

    public FunnyHumourAdapter(Context context, List<FunnyHumourBean.T1457069041911Bean> funnyHumourBeans) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.funnyHumourBeans = funnyHumourBeans;
    }

    @Override
    public int getCount() {
        return funnyHumourBeans == null ? 0 : funnyHumourBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return funnyHumourBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        HumourHolder holder = null;

        final HumourHolder finalHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.funny_humour_item, null);

            holder = new HumourHolder();
            finalHolder = holder;
            holder.text = ((TextView) convertView.findViewById(R.id.funnyHumourTitle));
            holder.pieImageView = ((PieImageView) convertView.findViewById(R.id.funnyHumourCover));
            holder.play = ((ImageView) convertView.findViewById(R.id.funnyHumourPlay));
            holder.controller = ((UniversalMediaController) convertView.findViewById(R.id.media_controller));
            holder.videoPlayerView = ((VideoPlayerView) convertView.findViewById(R.id.funnyHumourVideo));
            holder.controller.setMediaPlayer(holder.videoPlayerView);
            holder.videoPlayerView.setMediaController(holder.controller);
            holder.videoPlayerView.addMediaPlayerListener(new SimpleMainThreadMediaPlayerListener() {

                @Override
                public void onVideoStoppedMainThread() {
                    super.onVideoStoppedMainThread();
                    Log.e("TAG", "onVideoStoppedMainThread: ");
                    finalHolder.pieImageView.setVisibility(View.VISIBLE);
                    finalHolder.play.setVisibility(View.VISIBLE);

                    /**
                     * controller需要reset，放置此Controller继续发送progress的Message
                     */
//                    finalHolder.controller.reset();
                }

                @Override
                public void onVideoPreparedMainThread() {
                    Log.e("TAG", "onVideoPreparedMainThread: ");
                    super.onVideoPreparedMainThread();
                }

                @Override
                public void onVideoCompletionMainThread() {
                    super.onVideoCompletionMainThread();
                    Log.e("TAG", "onVideoCompletionMainThread: ");
                    finalHolder.pieImageView.setVisibility(View.VISIBLE);
                    finalHolder.play.setVisibility(View.VISIBLE);
                    currentPlayPosition = -1;
                }

                @Override
                public void onInfo(int what, int extra) {
                    super.onInfo(what, extra);
                    Log.e("TAG", "onInfo: what is " + what + " extra is " + extra);
                    switch (what) {
                        case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                            Log.e("TAG", "printInfo: MEDIA_INFO_BUFFERING_START");
                            break;
                        case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                            Log.e("TAG", "printInfo: MEDIA_INFO_BUFFERING_END");
                            break;
                    }
                }
            });
            convertView.setTag(holder);
        } else {
            holder = ((HumourHolder) convertView.getTag());
            finalHolder = holder;
        }

        FunnyHumourBean.T1457069041911Bean bean = funnyHumourBeans.get(position);
        final String imageUrl = bean.getCover();
        String title = bean.getTitle();
        final String mp4_url = bean.getMp4_url();

        holder.text.setText(title);

        Picasso picasso = SquareUtils.getPicasso(context, new SquareUtils.ProgressListener() {
            @Override
            public void update(@IntRange(from = 0, to = 100) int percent) {
                initHolder(finalHolder, percent);
            }
        });
        picasso.load(imageUrl)
                .placeholder(context.getResources().getDrawable(R.drawable.place_holder))
                .config(Bitmap.Config.ARGB_4444)
                .into(holder.pieImageView);

        Object tag = holder.pieImageView.getTag();
        if (tag != null) {
            int newTag = (int) tag;
            if (newTag == currentPlayPosition && newTag != position) {
                Log.e("TAG", "正在播放的item已经滑动出屏幕， 需要停止播放");

                finalHolder.pieImageView.setVisibility(View.VISIBLE);
                finalHolder.play.setVisibility(View.VISIBLE);

                mVideoPlayerManager.stopAnyPlayback();
                currentPlayPosition = -1;
            }
        }
        holder.pieImageView.setTag(position);

        holder.pieImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TAG", "onClick: Pie clicked");

                if (currentPlayPosition != position) {
                    finalHolder.pieImageView.setVisibility(View.INVISIBLE);
                    finalHolder.play.setVisibility(View.INVISIBLE);

                    currentPlayPosition = position;
                    mVideoPlayerManager.playNewVideo(null, finalHolder.videoPlayerView, mp4_url);
                }
            }
        });

        holder.videoPlayerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TAG", "onClick: VideoView clicked");
                Toast.makeText(context, "播放到: " + finalHolder.videoPlayerView.getCurrentPosition(),
                        Toast.LENGTH_SHORT).show();

//                if (!finalHolder.controller.isShowing()) {
//                    finalHolder.controller.show();
//                } else {
//                    finalHolder.controller.hide();
//                }
            }
        });

        return convertView;
    }


    private int currentPlayPosition = -1;

    private void initHolder(final HumourHolder finalHolder, final int percent) {
        if (context instanceof Activity) {
            Activity activity = ((Activity) context);
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    finalHolder.pieImageView.setProgress(percent);
                }
            });
        }
    }

    class HumourHolder {
        TextView text;
        PieImageView pieImageView;
        ImageView play;
        VideoPlayerView videoPlayerView;
        UniversalMediaController controller;
    }
}
