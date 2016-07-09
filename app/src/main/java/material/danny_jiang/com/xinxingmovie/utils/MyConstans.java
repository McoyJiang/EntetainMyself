package material.danny_jiang.com.xinxingmovie.utils;

/**
 * Created by axing on 16/6/26.
 */
public class MyConstans {

    /**
     * 以下字段是为电影界面专用
     */
    public static final String MOVIE_DETAIL_HOST =
            "http://op.juhe.cn/onebox/movie/video";
    public static final String MOVIE_LATEST_HOST =
            "http://op.juhe.cn/onebox/movie/pmovie";
    public static final String APPKEY =
            "334058482654e24fe6eb8b1d6fb91096";
    public static final String CITY = "上海";

    public static final String QUERY_SUCCEED = "查询成功";
    public static final String QUERY_RESULT = "result";
    public static final String QUERY_RESULT_DATA = "data";

    public static final String QUERY_TV_TITLE = "tvTitle";
    public static final String QUERY_ICON_ADDRESS = "iconaddress";
    public static final String QUERY_STORY = "story";
    public static final String QUERY_STORA_DATA = "data";
    public static final String QUERY_STORA_BRIEF = "storyBrief";

    public static String getNewComingUrl() {
        return MOVIE_LATEST_HOST + "?key=" + APPKEY + "&city=" + MyConstans.CITY;
    }

    public static String getMovieDetailUrl(String movieName) {
        return MOVIE_DETAIL_HOST + "?key=" + APPKEY + "&q=" + movieName;
    }


    /**
     * 以下字段是为搞笑视频专用
     */
    public static final String FUNNY_VIDEO_ADDS_URL =
            "http://c.3g.163.com/recommend/getChanListNews?channel=T1457068979049&size" +
                    "=10&passport=&devId=M0mZpZPTh3qTtZl4hL728g%3D%3D&lat=YO6p1koFB04ckeiATuYaGw" +
                    "%3D%3D&lon=SQIt%2FB7%2BSqySYsiVHI%2FDiQ%3D%3D&version=7.0&net=" +
                    "wifi&ts=1462799836&sign=c2djfgLSp3MrKAy55XppYhwclrsb%2B%" +
                    "2Bf9BdnyzvbHZ3d48ErR02zJ6%2FKXOnxX046I&encryption=1&canal=wandoujia_news";

    public static final String FUNNY_VIDEO_RECOMMEND =
            "http://c.3g.163.com/nc/video/Tlist/T1457069232830/%d-10.html";

    public static final String FUNNY_VIDEO_HUMOUR =
            "http://c.3g.163.com/nc/video/Tlist/T1457069041911/%d-10.html";

    public static final String FUNNY_VIDEO_PET =
            "http://c.3g.163.com/nc/video/Tlist/T1457069232830/%d-10.html";

    public static final String FUNNY_VIDEO_TITLE = "http://c.3g.163.com/nc/video/topiclist.html";


}
