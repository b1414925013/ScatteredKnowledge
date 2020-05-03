package Utils;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pojo.PlayBean;
import pojo.PlayListBean;

import java.util.*;

/*
 * @Author :zhaoyijie（Aquarius_genius）水瓶座鬼才
 * @create :2019/11/14 13:28
 * @Email   : 747897928@qq.com
 * @Description : 处理网易云音乐爬虫
 */
public class CloudMusicSpider extends ConnectUtils {
    private HashMap headers;
    /*limit 表示单页显示的歌单数（修改无效） offset 表示当前页数，即 offset / limit + 1   limit=35*/
    private final String playListUrl="https://music.163.com/discover/playlist/?order=hot&cat=%E5%85%A8%E9%83%A8&limit=35&offset={OFFSET}";
    private final int MAXPAGENUM=37;//目前网易云歌单38页，但是我们不超这个页数
    private String offset;
    private Random random;

    public CloudMusicSpider() {
        random=new Random();
        headers = new HashMap();
        //必须用Windows的请求头，安卓的请求头获取不到想要的数据
        headers.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
        headers.put("Referer","http//music.163.com");
        headers.put("Host","music.163.com");
    }

    public void getPlayList(ArrayList<PlayListBean> playListBeanList){
        offset= String.valueOf((int) (Math.random()*MAXPAGENUM+1)*35);
        String url=getPlayListUrl().replace("{OFFSET}",offset);
        String html= this.connect(url, Connection.Method.GET, headers);
        Document document = Jsoup.parse(html);
        Elements LIItem = document.getElementsByClass("u-cover u-cover-1");
        Elements AItems =LIItem.select("a");
        Elements ImageItems =LIItem.select("img");
        /*Collections.shuffle(AItems);//list随机打乱顺序
        Collections.shuffle(ImageItems);*/
        int stepSize=random.nextInt(2)+1;
        ListIterator<PlayListBean> playListBeanListIterator = playListBeanList.listIterator();
        for (int i=0;i<AItems.size();i++) {
            if (i%stepSize!=0){
                continue;
            }
            try {
                Element AItem =AItems.get(i);
                String PlayListTitle=AItem.attr("title");
                String tmpHref = AItem.attr("href");
                if (tmpHref.contains("javascript")){
                    continue;
                }
                String PlayListUrl="https://music.163.com"+tmpHref;
                String imageUrl=ImageItems.get(i).attr("src");
                if (playListBeanListIterator.hasNext()){
                    PlayListBean playListBean =playListBeanListIterator.next();
                    playListBean.setAlbum(PlayListTitle);
                    playListBean.setImageUrl(imageUrl);
                    playListBean.setPlayListUrl(PlayListUrl);
                }else {
                    return;
                }
            } catch (Exception e) {
                System.err.println(e);
                break;
            }
        }

    }

    /**
     * @Description:  获取指定网易云歌单里的每一首歌的名字和mp3链接
     * @Param: [playListBean，list] 歌单对象  音乐对象数组
     * @return: java.util.List 返回一个装载指定网易云歌单里的每一首歌的名字和mp3链接的数组
     */
    public void getSongList(PlayListBean playListBean, List<PlayBean> list){
        if (list.size()!=0){
            list.clear();
        }
        String url=playListBean.getPlayListUrl().replace("#/","");
        String imageUrl=playListBean.getImageUrl();
        String html= this.connect(url, Connection.Method.GET, headers);
        Document document = Jsoup.parse(html);
        Elements ULItem = document.getElementsByClass("f-hide");
        Elements AItems =ULItem.select("a");
        for (Element AItem : AItems) {
            String mp3Name=AItem.text();
            String mp3Id= AItem.attr("href").substring(9);
            String mp3Url="http://music.163.com/song/media/outer/url?id="+mp3Id+".mp3";
            list.add(new PlayBean(mp3Name, mp3Id, mp3Url, "", imageUrl,""));
        }
    }

    public String getPlayListUrl() {
        return playListUrl;
    }
}
