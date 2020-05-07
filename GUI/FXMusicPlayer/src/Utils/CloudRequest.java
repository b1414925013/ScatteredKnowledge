package Utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Connection;
import pojo.PlayBean;

import java.util.*;

/*
 * @Author :zhaoyijie（Aquarius_genius）水瓶座鬼才
 * @create :2019/11/14 13:28
 * @Email   : 747897928@qq.com
 * @Description : 处理网易云歌单爬虫
 */
public class CloudRequest extends ConnectUtils {
    private String Url = "http://www.songe.cc/";
    private String requestUrl = "http://music.163.com/api/song/lyric?id=SongId&lv=1&kv=1&tv=-1";//歌词请求接口
    public CloudRequest() {
    }

    public void connection(String name, List<PlayBean> list) {
        if (list.size() != 0) {
            list.clear();
        }
        String html=null;
        Map headers=new HashMap();
        headers.put("Accept","application/json,text/javascript, */*; q=0.01");
        headers.put("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
        headers.put("Host","www.songe.cc");
        headers.put("Origin","http://www.songe.cc");
        headers.put("Referer","http://www.songe.cc/?name=");
        headers.put("Accept-Language","zh-CN,zh;q=0.9");
        headers.put("X-Requested-With","XMLHttpRequest");
        headers.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.9 Safari/537.36");

        Map<String,String> dataMap=new HashMap<String,String>();
        dataMap.put("input",name);
        dataMap.put("filter","name");
        dataMap.put("type","netease");
        dataMap.put("page","1");
        try {
            Connection.Response connect = this.connect(getUrl(), Connection.Method.POST, headers, dataMap, 12000);
            html =connect.body() ;
            findDatalist(html, list);
            dataMap.put("page","2");//还需要第二页的数据
            connect = this.connect(getUrl(), Connection.Method.POST, headers, dataMap, 12000);
            html =connect.body() ;
            findDatalist(html, list);
        } catch (Exception e) {
            System.out.println(e.toString());
            System.out.println(html);
        }
    }

    public void findDatalist(String data, List<PlayBean> list) {
        JSONArray jsonArray = JSON.parseObject(data).getJSONArray("data");
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String namestring = jsonObject.getString("title");
            String idstring = jsonObject.getString("songid");
            String artistsname = jsonObject.getString("author");
            //String lrc=jsonObject.getString("lrc");
            String url=jsonObject.getString("url");
            String blurPicUrlstring = null;
            try {
                blurPicUrlstring = jsonObject.getString("pic");
            } catch (Exception e) {
                System.out.println(e);
                blurPicUrlstring = "img/center/pan_default.jpg";
            }
            PlayBean playBean = new PlayBean(namestring, idstring,
                    url, artistsname, blurPicUrlstring, "",null);
            list.add(playBean);
        }
    }

    public String getUrl() {
        return Url;
    }

    public String getRequestUrl() {
        return requestUrl;
    }
    public void spider(PlayBean currentPlayBean) {
        if (currentPlayBean.getLrc() == null) {
            try {
                String lrcRequestUrl = getRequestUrl().replace("SongId", currentPlayBean.getMusicId());
                String html = this.connect(lrcRequestUrl);
                //解析json，提取json里的lrc
                String lyricvalues = JSON.parseObject(html).getJSONObject("lrc").getString("lyric");
                currentPlayBean.setLrc(lyricvalues);
            } catch (Exception e) {
                currentPlayBean.setLrc("[00:00.000] 未找到歌词");
            }
        }
    }
}
