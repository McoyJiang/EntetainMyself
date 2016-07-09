package material.danny_jiang.com.xinxingmovie.adapter;

import android.content.Context;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import material.danny_jiang.com.xinxingmovie.R;
import material.danny_jiang.com.xinxingmovie.bean.FunnyPetBean;

/**
 * Created by axing on 16/7/7.
 */
public class FunnyPetAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    private Context context;
    private List<FunnyPetBean.T1457069232830Bean> funnyPetBeans;

    public FunnyPetAdapter(Context context, List<FunnyPetBean.T1457069232830Bean> funnyPetBeans) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.funnyPetBeans = funnyPetBeans;
    }

    @Override
    public int getCount() {
        return funnyPetBeans == null ? 0 : funnyPetBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return funnyPetBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PetViewHolder holder = null;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.funny_pet_item, null);

            holder = new PetViewHolder();
            holder.title = ((TextView) convertView.findViewById(R.id.funnyPetTitle));


            convertView.setTag(holder);
        } else {
            holder = ((PetViewHolder) convertView.getTag());
        }

        FunnyPetBean.T1457069232830Bean funnyPetBean = funnyPetBeans.get(position);

        holder.title.setText(funnyPetBean.getTitle());
        return convertView;
    }

    class PetViewHolder {
        TextView title;
    }
}
