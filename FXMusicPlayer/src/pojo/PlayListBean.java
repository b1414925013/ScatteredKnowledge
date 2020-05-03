package pojo;

/**
 * @author 水瓶座鬼才（zhaoyijie）
 * @Description:
 * @date 2020/3/2 21:18
 */
public class PlayListBean {
    private String playListUrl;
    private String album;
    private String imageUrl;

    public PlayListBean() {
    }

    public PlayListBean(String playListUrl, String album, String imageUrl) {
        this.playListUrl = playListUrl;
        this.album = album;
        this.imageUrl = imageUrl;
    }

    public String getPlayListUrl() {
        return playListUrl;
    }

    public void setPlayListUrl(String playListUrl) {
        this.playListUrl = playListUrl;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "PlayListBean{" +
                "playListUrl='" + playListUrl + '\'' +
                ", album='" + album + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
