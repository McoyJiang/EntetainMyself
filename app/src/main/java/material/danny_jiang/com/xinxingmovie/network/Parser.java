package material.danny_jiang.com.xinxingmovie.network;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import material.danny_jiang.com.xinxingmovie.bean.NewComingBean;
import material.danny_jiang.com.xinxingmovie.utils.MyConstans;

/**
 * Created by axing on 16/6/26.
 */
public class Parser {

    public static ArrayList<NewComingBean> parseNewComingJSON(JSONObject jsonObject) {
        ArrayList<NewComingBean> newComingBeans = new ArrayList<>();

        try {
            if (jsonObject != null && MyConstans.QUERY_SUCCEED.equals(jsonObject.getString("reason"))) {
                JSONObject jsonObject_result = jsonObject.getJSONObject(MyConstans.QUERY_RESULT);

                if (jsonObject_result != null && jsonObject_result.has(MyConstans.QUERY_RESULT_DATA)) {
                    JSONArray jsonArray_data = jsonObject_result.getJSONArray(MyConstans.QUERY_RESULT_DATA);

                    if (jsonArray_data != null) {
                        JSONObject jsonObject_new_coming = jsonArray_data.getJSONObject(1);

                        if (jsonObject_new_coming != null && jsonObject_new_coming.has(MyConstans.QUERY_RESULT_DATA)) {
                            JSONArray jsonArray_data_data = jsonObject_new_coming.getJSONArray(MyConstans.QUERY_RESULT_DATA);
                            for (int i = 0; i < jsonArray_data_data.length(); i++) {
                                JSONObject jsonObject_i = jsonArray_data_data.getJSONObject(i);
                                NewComingBean newComingBean = new NewComingBean();
                                String title = jsonObject_i.getString(MyConstans.QUERY_TV_TITLE);
                                String des = jsonObject_i.getJSONObject(MyConstans.QUERY_STORY).getJSONObject(
                                        MyConstans.QUERY_STORA_DATA).getString(MyConstans.QUERY_STORA_BRIEF);
                                String coverUrl = jsonObject_i.getString(MyConstans.QUERY_ICON_ADDRESS);

                                newComingBean.setMovieTitle(title);
                                newComingBean.setMovieDes(des);
                                newComingBean.setMovieCoverUrl(coverUrl);
                                newComingBeans.add(newComingBean);
                            }

                            return newComingBeans;
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("TAG", "parseNewComingJSON: " + e.getMessage());
        }
        return null;
    }
}
