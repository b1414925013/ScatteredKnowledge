import Utils.*;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import pojo.PlayBean;
import pojo.PlayListBean;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static javafx.scene.paint.Color.*;

public class MainApp extends Application {
    //1.全局的"舞台"对象
    public static Stage staticStage;
    //2.最大化之前的x,y坐标
    private double resetX;
    private double resetY;
    //3.最大化之前的宽度、高度
    private double resetWidth;
    private double resetHeight;
    //4.窗体移动前，相对于Scene的X,Y坐标
    private double mouseX;
    private double mouseY;

    //5.显示歌单列表的VBox对象
    private VBox groupVBox;

    //6.改变窗体前，X,Y坐标
    private double xOffset;
    private double yOffset;

    //7.歌单名称标签
    private Label labGroupName;

    //8.播放列表的TableView
    private TableView<PlayBean> tableView;

    //9.当前播放歌曲的索引
    private int currentIndex;
    //10.当前播放的时间的前一秒--设置滚动条
    private int prevSecond;
    //11.当前播放的PlayBean
    private PlayBean currentPlayBean;
    //12.下侧面板的：总时间
    private Label labTotalTime;
    //13.碟片的ImageView对象
    private ImageView panImageView;

    //16.播放按钮的ImageView对象
    private ImageView butPlayImage;

    //17.播放按钮的Label
    private Label labPlay;

    //18.当前播放模式：
    private int playMode = 1;//1 : 列表循环；2. 顺序播放  3.单曲循环

    //19.播放时间滚动条对象
    private Slider sliderSong;

    //20.已播放时间的Lable
    private Label labPlayTime;

    //21.音量滚动条
    private Slider sldVolume;

    //22.音量的进度条
    private ProgressBar volumeProgress;

    //23.记录静音前的音量
    private double prevVolumn;

    //24.显示歌词的VBox容器
    private VBox lrcVBox;
    //25.存储歌词时间的ArrayList
    private ArrayList<BigDecimal> lrcList = new ArrayList<>();
    //26.当前歌词的索引
    private int currentLrcIndex;
    private Date date;

    private MediaPlayer mediaPlayer;

    private int currentSecond;

    private CloudRequest cloudRequest=new CloudRequest();//网易云请求工具类

    private SimpleDateFormat simpleDateFormat=new SimpleDateFormat("mm:ss");

    private double millis;
    //判断此次是否在正常的播放区间
    private double min = 0;
    private double max = 0;
    private TextField search_TextField;


    public List<PlayBean> list;//音乐列表

    private ProgressBar pb1;

    private Label searchTiplabel;

    private CloudMusicSpider cloudMusicSpider=new CloudMusicSpider();

    private Timeline t1;

    private Timeline t2;

    private ArrayList<PlayListBean> playListBeanList;

    private String preImagePath="";//上一首歌曲的图片路径

    private List<PlayBean> localMusiclist;//音乐列表

    private ProgressIndicator progressIndicator;//下载进度条

   /* '/ \ : * ? " < > |'*/
    private  Pattern pattern = Pattern.compile("[\\/\\\\\\:\\*\\?\\\"\\<\\>\\|]");

    private Alert alert;

    private Label downProgressLabel;

    private final int PANIMAGVIEWSIZE=130;//音乐封面Image最大大小

    private ChangeListener<Duration> changeListener;//播放进度监听器

    private Runnable valueRunnable;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.getIcons().add(new Image("img/topandbottom/logo.jpg"));
        staticStage = primaryStage;
        localMusiclist=new ArrayList();
        playListBeanList=new ArrayList(10);

        playListBeanList.add(new PlayListBean(
                "https://music.163.com/#/playlist?id=904721255",
                "史诗｜战地女声，大美巅峰",
                "http://p3.music.126.net/sGXXFojMY0qIU_C1yZK03g==/109951163019027154.jpg?param=100y100"));
        playListBeanList.add(new PlayListBean(
                "https://music.163.com/#/playlist?id=25787308",
                "『柔和的旋律』你的温柔让我心醉",
                "http://p4.music.126.net/dZPxVhFRcX02nQZuXBqZ3w==/109951163041643279.jpg?param=100y100"));
        playListBeanList.add(new PlayListBean(
                "https://music.163.com/#/playlist?id=3004398689",
                "『日系』萦绕指尖的美妙旋律",
                "http://p4.music.126.net/7t4kd_pGd_Haox8mBjdVAA==/109951164422004419.jpg?param=100y100"));
        playListBeanList.add(new PlayListBean(
                "https://music.163.com/#/playlist?id=507712546",
                "泽野弘之十三大神曲加七大主题",
                "http://p3.music.126.net/NHN-622oYp758ZehSLeCzg==/3402988506771254.jpg?param=100y100"));

        playListBeanList.add(new PlayListBean(
                "https://music.163.com/#/playlist?id=2817888316",
                "纯音|总有一栖温柔，照亮心灵一方",
                "http://p3.music.126.net/sUd7_a8tjtmFxKLneLJtMg==/109951164489873260.jpg?param=100y100"));


        playListBeanList.add(new PlayListBean(
                "https://music.163.com/#/playlist?id=919444742",
                "静心&读书｜让你静下心来の轻音乐。",
                "http://p3.music.126.net/n6RS98m6rmMo5ae2Q6HXxQ==/18582846023108227.jpg?param=100y100"));

        playListBeanList.add(new PlayListBean(
                "https://music.163.com/#/playlist?id=2951172488",
                "[电音]纯音乐，动感强[建议耳机播放]",
                "http://p3.music.126.net/TrFpbq3yKIGRdZn1fQU7lg==/109951164348662817.jpg?param=100y100"));

        playListBeanList.add(new PlayListBean(
                "https://music.163.com/#/playlist?id=2779796218",
                "这些歌设置过单曲循环",
                "http://p4.music.126.net/MY4kAefdqzqH_-d9tu2MWA==/109951164402135028.jpg?param=100y100"));


        playListBeanList.add(new PlayListBean(
                "https://music.163.com/#/playlist?id=962934202",
                "魔性BGM（关爱日推，慎入）",
                "http://p3.music.126.net/QXU7V9fmrgRDn4EfQ8wIxA==/19071029184207193.jpg?param=100y100"));


        playListBeanList.add(new PlayListBean(
                "https://music.163.com/#/playlist?id=313174192",
                "振奋激昂的战歌",
                "http://p3.music.126.net/hzfIna0CWl4gbh6Gct3rnw==/1378787590317257.jpg?param=100y100"));

        playListBeanList.add(new PlayListBean(
                "https://music.163.com/#/playlist?id=4892800240",
                "写作业背书歌单｜随机播放｜♬",
                "http://p3.music.126.net/aoiX7-dMwNrWiHy8oMQTYw==/109951164762050124.jpg?param=100y100"));

        playListBeanList.add(new PlayListBean(
                "https://music.163.com/#/playlist?id=695403810",
                "刘珂矣歌集",
                "http://p3.music.126.net/SKujq5vqqv4KYOVFk7SiRA==/3407386538630284.jpg?param=100y100"));



        //每隔15毫秒执行一次
        t1 = new Timeline(new KeyFrame(Duration.millis(15),event -> {lrcVBox.setLayoutY(lrcVBox.getLayoutY() - 15);}));
        t1.setCycleCount(3);//执行3次
        date = new Date();
        list=new ArrayList<>();
        //设置舞台
        //1.创建一个BorderPane对象
        BorderPane borderPane = new BorderPane();//面板，透明的
        borderPane.setTop(getTopPane());
        borderPane.setLeft(getLeftPane());
        borderPane.setBottom(getBottomPane());
        borderPane.setCenter(getCenterPane());
        borderPane.setBackground(new Background(new BackgroundFill(Color.BLACK,null,null)));
        changeListener=initChangeListener();
        valueRunnable=initRunnable();
        //2.创建一个场景
        Scene scene = new Scene(borderPane, 1210, 800);//场景宽度：1300像素；场景高度：800像素
        scene.getStylesheets().add(getClass().getResource("/css/main.css").toExternalForm());
        //3.将场景设置到舞台
        primaryStage.setScene(scene);
        //4.将舞台的标题栏去掉
        primaryStage.initStyle(StageStyle.UNDECORATED);
        alert.initOwner(primaryStage);
        //显示舞台
        primaryStage.show();
        if (SplashScreen.getSplashScreen() != null) SplashScreen.getSplashScreen().close();
    }
    //创建一个中间的面板
    private BorderPane getCenterPane() {

        //2.歌单：标签
        Label lab1 = new Label("歌单：");
        lab1.setTextFill(Color.rgb(255, 255, 255));//文字：暗红色
        lab1.setFont(Font.font("Timer New Roman",
                FontWeight.BOLD, FontPosture.ITALIC, 18));
        BorderStroke bs = new BorderStroke(
                Color.rgb(180,0,0),//四个边的颜色
                Color.rgb(180,0,0),
                Color.rgb(180,0,0),
                Color.rgb(180,0,0),
                BorderStrokeStyle.SOLID,//四个边的线型--实线
                BorderStrokeStyle.SOLID,
                BorderStrokeStyle.SOLID,
                BorderStrokeStyle.SOLID,
                new CornerRadii(5),
                new BorderWidths(5),
                new Insets(1,1,1,1)

        );
        lab1.setBorder(new Border(bs));
        lab1.setLayoutX(30);
        lab1.setLayoutY(10);
        lab1.setPrefWidth(80);
        lab1.setPrefHeight(25);
        lab1.setAlignment(Pos.CENTER);

        //3.歌单名称：标签
        labGroupName = new Label("默认歌单");
        labGroupName.setLayoutX(120);
        labGroupName.setLayoutY(14);
        labGroupName.setTextFill(WHITE);
        labGroupName.setFont(Font.font("Timer New Roman",
                FontWeight.BOLD, FontPosture.ITALIC, 18));
        labGroupName.setPrefWidth(250);
        labGroupName.setPrefHeight(25);
        labGroupName.setAlignment(Pos.CENTER_LEFT);

        //5.歌词的VBox容器
        lrcVBox = new VBox(15);
        lrcVBox.setPadding(new Insets(20,20,20,20));
        lrcVBox.setLayoutX(250);
        lrcVBox.setLayoutY(0);

        //6.歌单列表标签
        Label lab3 = new Label("歌单列表");
        lab3.setPrefWidth(80);
        lab3.setPrefHeight(25);
        lab3.setTextFill(Color.WHITE);
        lab3.setAlignment(Pos.CENTER);
        lab3.setBackground(new Background(new BackgroundFill(Color.rgb(180,0,0),null,null)));
        lab3.setLayoutX(30);
        lab3.setLayoutY(275);

        //一条红线：Label
        Label labLine = new Label();
        labLine.setMinHeight(0);
        labLine.setPrefHeight(2);
        labLine.setBackground(new Background(new BackgroundFill(Color.rgb(180,0,0),null,null)));
        labLine.setLayoutX(0);
        labLine.setLayoutY(lab3.getLayoutY() + lab3.getPrefHeight());

        ImageView backImageView;
        Image image = new Image("img/center/time.jpg");
        backImageView = new ImageView(image);
        backImageView.setLayoutX(0);
        backImageView.setLayoutY(0);
        backImageView.setFitWidth(300);
        backImageView.setFitHeight(300);

        //高斯模糊
        GaussianBlur gaussianBlur = new GaussianBlur();
        gaussianBlur.setRadius(63);

        backImageView.setEffect(gaussianBlur);

        panImageView = new ImageView("img/center/pan_default.jpg");
        panImageView.setFitHeight(PANIMAGVIEWSIZE);
        panImageView.setFitWidth(PANIMAGVIEWSIZE);
        Circle circle1=new Circle();
        int halfCircleSize=PANIMAGVIEWSIZE/2;
        circle1.setCenterX(halfCircleSize);
        circle1.setCenterY(halfCircleSize);
        circle1.setRadius(halfCircleSize);
        int panImageViewX=65;
        int panImageViewY=85;
        panImageView.setLayoutX(panImageViewX);
        panImageView.setLayoutY(panImageViewY);
        panImageView.setClip(circle1);
        int circle1BorderWidth=30;

        Circle circle2=new Circle();
        int circle2Size=halfCircleSize+circle1BorderWidth;
        circle2.setCenterX(circle2Size);
        circle2.setCenterY(circle2Size);
        circle2.setRadius(circle2Size);
        circle2.setLayoutX(panImageViewX-circle1BorderWidth);
        circle2.setLayoutY(panImageViewY-circle1BorderWidth);
        //设置背景色--渐变
        Stop[] stops = new Stop[]{
                new Stop(0, Color.rgb(185, 185, 185)),
                new Stop(0.5, Color.rgb(23, 23, 23)),
                new Stop(1, Color.rgb(185, 185, 185))
        };
        circle2.setFill(new LinearGradient(0,0,1,1,
                true, CycleMethod.NO_CYCLE,stops));//渐变固定写法
        circle2.setStrokeWidth(5);
        circle2.setStroke(Color.BLACK);

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setBackground(new Background(new BackgroundFill(Color.BLACK,null,null)));
        anchorPane.getChildren().addAll(backImageView,lab1,labGroupName,lrcVBox,lab3,labLine,circle2,panImageView);
        //上侧的ScrollPane
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setPadding(new Insets(0,0,0,0));
        scrollPane.setContent(anchorPane);
        scrollPane.setPrefHeight(304);
        scrollPane.setMouseTransparent(true);//使ScrollPane不接收鼠标事件

        anchorPane.prefWidthProperty().bind(scrollPane.widthProperty());
        anchorPane.prefHeightProperty().bind(scrollPane.heightProperty());
        backImageView.fitWidthProperty().bind(scrollPane.widthProperty());
        backImageView.fitHeightProperty().bind(scrollPane.heightProperty());
        labLine.prefWidthProperty().bind(scrollPane.widthProperty());
        /*spectrum.prefWidthProperty().bind(scrollPane.widthProperty());*/

        //*******************************上侧完毕***********************************************//
        //*******************************下侧：歌单列表******************************************//
        tableView = new TableView<>();
        tableView.setPrefWidth(960);
        tableView.getStylesheets().add("css/playTable.css");

        TableColumn c1 = new TableColumn("音乐标题");
        c1.setPrefWidth(300);
        c1.setCellValueFactory(new PropertyValueFactory<>("musicName"));



        TableColumn c2 = new TableColumn("歌手");
        c2.setPrefWidth(300);
        c2.setCellValueFactory(new PropertyValueFactory<>("artistName"));

        TableColumn c3 = new TableColumn("专辑");
        c3.setPrefWidth(300);
        c3.setCellValueFactory(new PropertyValueFactory<>("album"));

        tableView.getColumns().addAll(c1,c2,c3);


        tableView.setRowFactory(tv -> {
            TableRow<PlayBean> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                //验证双击
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    //1.获取选中行的索引
                    this.currentIndex = row.getIndex();
                    //2.将前一秒置为：0
                    this.prevSecond = 0;
                    //3.判断当前是否正在播放，如果是：将其停止
                    if (this.currentPlayBean != null) {
                        if (this.mediaPlayer!=null){
                            this.mediaPlayer.stop();
                        }
                    }
                    //4.获取当前的PlayBean
                    this.currentPlayBean = row.getItem();
                    //5.播放
                    play();
                }
            });
            return row;
        });

        //**************************************总的BorderPane********************************//
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(scrollPane);
        borderPane.setCenter(tableView);
        //将"操作列"随着窗体的大小改变而改变,可以该做其他列
        c3.prefWidthProperty().bind(borderPane.widthProperty());
        return borderPane;
    }

    //播放
    private void play() {
        if (mediaPlayer!=null) {
            mediaPlayer.stop();
            try {
                mediaPlayer.currentTimeProperty().removeListener(changeListener);
                mediaPlayer.setOnEndOfMedia(null);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
            mediaPlayer.dispose();//释放资源
            mediaPlayer = null;
        }
        if (currentPlayBean==null){
            System.out.println("currentPlayBean 为空");
            return;
        }
        labPlayTime.setText("00:00");
        labTotalTime.setText("00:00");
        mediaPlayer=new MediaPlayer(new Media(currentPlayBean.getMp3Url()));
        new Thread(()-> mediaPlayer.play()).start();
        loadLrc();
        mediaPlayer.currentTimeProperty().addListener(changeListener);
        if (currentPlayBean.isLocalMusic()){
            try {
                File file = new File(new URI(currentPlayBean.getMp3Url()));
                WritableImage writableImage = LocalMusicUtils.getLocalMusicArtwork(file,PANIMAGVIEWSIZE,PANIMAGVIEWSIZE);
                if (writableImage!=null){
                    panImageView.setImage(writableImage);
                    preImagePath="";
                }
                else {
                    if (!preImagePath.equals("img/center/pan_default.jpg")){
                        panImageView.setImage(new Image("img/center/pan_default.jpg",PANIMAGVIEWSIZE,PANIMAGVIEWSIZE,false,false,true));
                        preImagePath="img/center/pan_default.jpg";
                    }
                }
            } catch (Exception e){
                System.err.println(e.getMessage());
            }
        }
        else {
            if (preImagePath!=null){
                //如果上一个图片的url和这次图片的url相同，应该就是歌单歌曲，就没必需要重复加载图片
                if (!preImagePath.equals(currentPlayBean.getImageUrl())){
                    /*backgroundLoading开启异步加载Image*/
                    try {
                        panImageView.setImage(new Image(currentPlayBean.getImageUrl(),PANIMAGVIEWSIZE,PANIMAGVIEWSIZE,false,false,true));
                    } catch (Exception e) {
                        panImageView.setImage(new Image("img/center/pan_default.jpg",PANIMAGVIEWSIZE,PANIMAGVIEWSIZE,false,false,true));
                    }
                    preImagePath=currentPlayBean.getImageUrl();
                }
            }
        }
        int total_second= (int) Math.floor(mediaPlayer.getTotalDuration().toSeconds());
        date.setTime(total_second*1000);
        labTotalTime.setText(simpleDateFormat.format(date));
        //如果total_second==0，证明音频文件还没完整地读取，这时候无法获取总时间，这时候就先给个100，稍后在监听器那边修改
        if (total_second==0){
            sliderSong.setMax(100);
        }
        else {
            sliderSong.setMax(total_second);
        }
        sliderSong.setMajorTickUnit(1);//每次前进1格
        sliderSong.setValue(0);
        prevSecond = 0;
        butPlayImage.setImage(new Image("img/topandbottom/Pause.png"));
        mediaPlayer.setVolume(sldVolume.getValue());
        mediaPlayer.setOnEndOfMedia(valueRunnable);
        //System.gc();
    }

    //加载正在播放的歌曲的lrc文件(歌词文件)
    private void loadLrc() {
        if (currentPlayBean.getMusicName() == null||currentPlayBean.getMusicName().equals("")) {
            return;
        }
        //初始化lrcVBox
        this.lrcVBox.getChildren().clear();
        this.lrcVBox.setLayoutY(60);
        this.lrcList.clear();
        this.currentLrcIndex = 0;
        String[] musicLrcList=null;
        if (currentPlayBean.getLrc()==null&&!currentPlayBean.isLocalMusic()){
            cloudRequest.spider(currentPlayBean);
            //封装歌词Label
        }
        musicLrcList = currentPlayBean.getLrc().split("\n");
        for (String row:musicLrcList){
            if (row.indexOf("[") == -1 || row.indexOf("]") == -1) {
                continue;
            }
            if (row.charAt(1) < '0' || row.charAt(1) > '9') {
                continue;
            }
            String strTime = row.substring(1,row.indexOf("]"));//00:03.29
            String strMinute = strTime.substring(0, strTime.indexOf(":"));//取出：分钟
            String strSecond = strTime.substring(strTime.indexOf(":") + 1);//取出：秒和毫秒
            //转换为int分钟
            int intMinute = Integer.parseInt(strMinute);
            //换算为总的毫秒
            BigDecimal totalMilli = new BigDecimal(intMinute * 60).add(new BigDecimal(strSecond)).multiply(new BigDecimal("1000"));
            this.lrcList.add(totalMilli);
            //创建歌词Label
            Label lab = new Label(row.trim().substring(row.indexOf("]") + 1));
            lab.setMinWidth(480);
            lab.setMinHeight(30);
            lab.setMaxHeight(30);

            lab.setPrefWidth(530);
            lab.setPrefHeight(30);
            lab.setTextFill(Color.rgb(53,53,53));
            lab.setFont(new Font("黑体",18));
            lab.setAlignment(Pos.CENTER);

            //判断是否是第一个歌词，如果是，改为30号，黄色
            if (this.lrcVBox.getChildren().size() == 0) {
                lab.setTextFill(Color.YELLOW);
                lab.setFont(new Font("黑体",18));
            }
            //判断是否是第二行
            if (this.lrcVBox.getChildren().size() == 1) {
                lab.setTextFill(Color.BLACK);
            }
            //将歌词Label添加到lrcVBox中
            this.lrcVBox.getChildren().add(lab);
        }
    }


    //获取下侧面板
    private BorderPane getBottomPane() {
        //1.上一首
        ImageView v1 = new ImageView("img/topandbottom/Last.png");
        v1.setFitHeight(40);
        v1.setFitWidth(40);
        Label lab1 = new Label("", v1);
        lab1.setOnMouseClicked(e -> {
            if (this.currentPlayBean != null) {
                this.mediaPlayer.stop();
            }
            if (this.tableView.getItems().size()!=0){
                //让当前的索引-1
                this.currentIndex--;
                if (currentIndex < 0) {
                    if (this.playMode == 1) {//列表循环
                        this.currentIndex = this.tableView.getItems().size() - 1;//定位到最后一首歌
                    }else{
                        this.currentIndex = 0;
                    }
                }
                //设置Table的选中
                this.tableView.getSelectionModel().select(currentIndex);
                //设置播放PlayBean对象
                this.currentPlayBean = this.tableView.getItems().get(currentIndex);
                //开始播放
                play();
            }
        });

        //2.播放按钮
        butPlayImage = new ImageView("img/topandbottom/Play.png");
        butPlayImage.setFitWidth(40);
        butPlayImage.setFitHeight(40);
        labPlay = new Label("", butPlayImage);
        labPlay.setOnMouseClicked(e -> {
            if (this.mediaPlayer!=null){
                //判断如果当前正在播放，暂停
                if (this.mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                    //设置播放器暂停
                    this.mediaPlayer.pause();
                    //设置播放按钮图标为：播放
                    butPlayImage.setImage(new Image("img/topandbottom/Play.png"));
                }else if(this.mediaPlayer.getStatus() == MediaPlayer.Status.PAUSED){
                    this.mediaPlayer.play();
                    butPlayImage.setImage(new Image("img/topandbottom/Pause.png"));
                }
            }
        });

        //3.下一首
        ImageView v3 = new ImageView("img/topandbottom/Next.png");
        v3.setFitWidth(40);
        v3.setFitHeight(40);
        Label lab3 = new Label("", v3);
        lab3.setOnMouseClicked(e -> {
            if (this.currentPlayBean != null) {
                this.mediaPlayer.stop();
            }
            if (this.tableView.getItems().size()!=0){
                //让当前的索引+1
                this.currentIndex++;
                if (currentIndex >= this.tableView.getItems().size()) {
                    if (this.playMode == 1) {//列表循环
                        this.currentIndex = 0;//定位到第一首歌
                    }else{
                        this.currentIndex = this.tableView.getItems().size() - 1;
                    }
                }
                //设置Table的选中
                this.tableView.getSelectionModel().select(currentIndex);
                //设置播放PlayBean对象
                this.currentPlayBean = this.tableView.getItems().get(currentIndex);
                //开始播放
                play();
            }
        });

        HBox hBox1 = new HBox(30);
        hBox1.setPrefWidth(255);
        hBox1.setPadding(new Insets(5, 10, 5, 30));
        hBox1.getChildren().addAll(lab1, labPlay, lab3);

        //*************************中间滚动条部分**********************************//
        //1.已播放的时间：
        labPlayTime = new Label("00:00");
        labPlayTime.setPrefHeight(50);
        labPlayTime.setPrefWidth(40);
        labPlayTime.setTextFill(Color.WHITE);
        //2.滚动条
        sliderSong = new Slider();
        sliderSong.setMinWidth(0);
        sliderSong.setMinHeight(0);
        sliderSong.setPrefWidth(300);
        sliderSong.setPrefHeight(12);
        sliderSong.getStylesheets().add("css/TopAndBottomPage.css");


        //进度条
        pb1 = new ProgressBar(0);
        pb1.setProgress(0);
        pb1.setMinWidth(0);
        pb1.setMinHeight(0);

        pb1.setMaxWidth(5000);
        pb1.setPrefWidth(300);
        pb1.getStylesheets().add("css/TopAndBottomPage.css");

        //Slider值发生变化时..
        sliderSong.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                //设置进度条
                if (currentPlayBean != null) {
                    pb1.setProgress((newValue.doubleValue() + 1) / (int) Math.floor(mediaPlayer.getTotalDuration().toSeconds()));
                }
            }
        });
        //Slider的鼠标抬起事件中
        sliderSong.setOnMouseReleased(e -> {
            if (currentPlayBean != null) {
                Duration duration = new Duration(sliderSong.getValue() * 1000);
                mediaPlayer.seek(duration);//设置新的播放时间

                //同时设置Label
                date.setTime((long) mediaPlayer.getCurrentTime().toMillis());

                labPlayTime.setText(simpleDateFormat.format(date));
                //设置前一秒
                prevSecond = (int)duration.toSeconds() - 1;
            }
        });


        //使用StackPane来存储滚动条和进度条
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(pb1,sliderSong );

        //3.总时间标签
        labTotalTime = new Label("00:00");
        labTotalTime.setPrefWidth(40);
        labTotalTime.setPrefHeight(50);
        labTotalTime.setTextFill(Color.WHITE);
        labTotalTime.setAlignment(Pos.CENTER_RIGHT);

        BorderPane borderPane = new BorderPane();
        borderPane.setLeft(labPlayTime);
        borderPane.setCenter(stackPane);
        borderPane.setRight(labTotalTime);
        borderPane.setPrefHeight(50);

        labPlayTime.prefHeightProperty().bind(borderPane.prefHeightProperty());
        sliderSong.prefHeightProperty().bind(borderPane.prefHeightProperty());
        labTotalTime.prefHeightProperty().bind(borderPane.prefHeightProperty());


        //************************右侧的几个组件************************************//
        //1.音量图片
        ImageView v5 = new ImageView("img/topandbottom/Volumn.png");
        v5.setFitWidth(17);
        v5.setFitHeight(17);
        Label lab5 = new Label("",v5);
        lab5.setOnMouseClicked(e ->{
            if (this.currentPlayBean != null) {
                //判断当前的音量
                if (this.mediaPlayer.getVolume() != 0) {//此时有音量
                    //将当前的音量存储起来
                    this.prevVolumn = this.mediaPlayer.getVolume();
                    //设置为：静音
                    this.mediaPlayer.setVolume(0);
                    //设置图片
                    v5.setImage(new Image("img/left/VolumnZero_1.png"));
                    //设置音量滚动条
                    this.sldVolume.setValue(0);
                }else{//此时是静音状态
                    //恢复原音量
                    this.mediaPlayer.setVolume(this.prevVolumn);
                    //恢复图片
                    v5.setImage(new Image("img/topandbottom/Volumn.png"));
                    //恢复音量滚动条
                    this.sldVolume.setValue(this.prevVolumn * 100);
                }
            }
        });

        //2.音量滚动条
        sldVolume = new Slider();
        sldVolume.setMax(100);
        sldVolume.setValue(50);
        sldVolume.setMajorTickUnit(1);//每前进一格，增加多少的值

        sldVolume.setMinHeight(0);
//        sldVolume.setPrefHeight(10);
        sldVolume.setPrefWidth(100);
        sldVolume.getStylesheets().add("css/TopAndBottomPage.css");

        //进度条
        volumeProgress = new ProgressBar(0);
        volumeProgress.setMinHeight(0);
        volumeProgress.setProgress(0.5);//初始在中间位置
        volumeProgress.setPrefWidth(100);
        volumeProgress.setPrefHeight(10);
        volumeProgress.prefWidthProperty().bind(sldVolume.prefWidthProperty());
        /*
        JavaFX绑定同步两个值：当因变量更改时，其他变量更改。
要将属性绑定到另一个属性，请调用bind()方法，该方法在一个方向绑定值。例如，当属性A绑定到属性B时，属性B的更改将更新属性A，但不是相反。
         */
        volumeProgress.getStylesheets().add("css/TopAndBottomPage.css");

        //监听进度条的值发生变化时
        sldVolume.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                //1.设置它的进度条
                volumeProgress.setProgress(sldVolume.getValue() / 100);
                //2.设置音量
                if (currentPlayBean != null) {
                    mediaPlayer.setVolume(volumeProgress.getProgress());
                }
            }
        });
        StackPane sp2 = new StackPane();
        sp2.getChildren().addAll(volumeProgress, sldVolume);

        //3.播放模式图片
        ImageView v6 = new ImageView("img/topandbottom/Repeat.png");
        v6.setFitWidth(25);
        v6.setFitHeight(25);
        Label lab6 = new Label("", v6);
        lab6.setOnMouseClicked(e -> {
            //此处只处理playMode，实现，放在播放的事件中
            this.playMode++;
            if (this.playMode > 3) {
                this.playMode = 1;
            }
            switch (this.playMode) {
                case 1:
                    v6.setImage(new Image("img/topandbottom/Repeat.png"));
                    break;
                case 2:
                    v6.setImage(new Image("img/topandbottom/OrderPlay.png"));
                    break;
                case 3:
                    v6.setImage(new Image("img/topandbottom/RepeatInOne.png"));
                    break;
            }

        });

        //4.歌词图片
        ImageView v7 = new ImageView("img/topandbottom/ci.png");
        v7.setFitWidth(25);
        v7.setFitHeight(25);
        Label lab7 = new Label("", v7);
        //5.拖拽图片
        ImageView v8 = new ImageView("img/topandbottom/right_drag.png");
        v8.setFitWidth(30);
        v8.setFitHeight(50);
        Label lab8 = new Label("", v8);
        //当鼠标按下时
        lab8.setOnMousePressed(e -> {
            //记录当前鼠标在屏幕的X,Y坐标
            xOffset = e.getScreenX();
            yOffset = e.getScreenY();
        });
        //当鼠标移动时
        lab8.setOnMouseMoved(e -> {
            if(e.getY() > 34 && e.getY() < 50 &&
                    e.getX() > 0 && e.getX() < 30){
                //改变鼠标的形状
                lab8.setCursor(Cursor.NW_RESIZE);
            }else{
                lab8.setCursor(Cursor.DEFAULT);
            }
        });
        //当鼠标拖拽时
        lab8.setOnMouseDragged(e -> {
            if (staticStage.getWidth() + (e.getScreenX() - xOffset) >= 1200) {
                staticStage.setWidth(staticStage.getWidth() + (e.getScreenX() - xOffset));
                xOffset = e.getScreenX();
            }
            if (staticStage.getHeight() + (e.getScreenY() - yOffset) >= 800) {
                staticStage.setHeight(staticStage.getHeight() + (e.getScreenY() - yOffset));
                yOffset = e.getScreenY();
            }
        });
        HBox hBox = new HBox(15);
        hBox.setPadding(new Insets(0, 0, 0, 10));
        hBox.setPrefWidth(270);
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.getChildren().addAll(lab5,sp2,lab6,lab7,lab8);

        //**********************总的BorderPane***********************************//
        BorderPane bottomPane = new BorderPane();
        bottomPane.setLeft(hBox1);
        bottomPane.setCenter(borderPane);
        bottomPane.setRight(hBox);
        return bottomPane;
    }

    //创建一个左侧面板
    private BorderPane getLeftPane() {
        VBox localVBox=new VBox(5);

        Label locallabGd = new Label("我的音乐");
        locallabGd.setPrefWidth(220);
        locallabGd.setPrefHeight(20);
        locallabGd.setTextFill(Color.WHITE);
        locallabGd.setFont(Font.font("Timer New Roman",
                FontWeight.BOLD, FontPosture.ITALIC, 14));

        ImageView musicIco=new ImageView("img/left/music.png");
        musicIco.setPreserveRatio(true);
        musicIco.setFitWidth(20);
        //歌单名称：Label
        Label localLabGroupName = new Label("本地音乐");
        localLabGroupName.setMinHeight(0);
        localLabGroupName.setPrefHeight(20);
        localLabGroupName.setPrefWidth(146);
        localLabGroupName.setTextFill(Color.rgb(210,210,210));
        Tooltip tooltip = new Tooltip("(请将本地音乐放到LocalMusic文件夹下，让程序检测出来)");
        /*setTipTime(tooltip,10000);//10毫秒显示时间*/
        localLabGroupName.setTooltip(tooltip);

        localLabGroupName.setOnMouseClicked(event -> {
            this.labGroupName.setText("本地音乐");
            if (searchTiplabel.getText().equals("正在搜索，请稍后再操作....")){
                return;
            }
            tableView.getItems().clear();//清空表格
            localMusiclist.clear();
            searchTiplabel.setText("正在搜索，请稍后再操作....");
            new Thread(()->{
                LocalMusicUtils.getLocalMusicInf(localMusiclist);
                //用GUI线程更新UI组件
                Platform.runLater(()->{
                    tableView.setItems(FXCollections.observableList(localMusiclist));
                    searchTiplabel.setText("");
                    /*Windows任务栏图标闪烁效果}*/
                    if(!staticStage.isFocused()){
                        staticStage.requestFocus();
                    }
                });
                if (localMusiclist.size()!=0){
                    currentIndex=0;
                }
                //System.gc();
            }).start();
        });

        ImageView dirIcoIv = new ImageView("img/left/derictory.png");
        dirIcoIv.setFitWidth(20);
        dirIcoIv.setFitHeight(20);
        Label dirIcoivLab= new Label("",dirIcoIv);
        dirIcoivLab.setOnMouseClicked(event -> {
            //打开LocalMusic文件夹
            try {
                LocalMusicUtils.createLocalMusicDir();
                Desktop.getDesktop().open(new File(LocalMusicUtils.LOCAL_MUSIC_DIR));
            } catch (IOException e) {
                System.out.println(e.toString());
            }
        });

        ImageView downIv = new ImageView("img/left/down.png");
        downIv.setFitWidth(20);
        downIv.setFitHeight(20);
        Label downIvLab= new Label("下载当前音乐");
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.titleProperty().set("提示信息");
        downIvLab.setOnMouseClicked(event -> {
            //打开LocalMusic文件夹
            LocalMusicUtils.createLocalMusicDir();
            //为了不卡死GUI主线程，new一个线程处理文件下载
            new Thread(()->{
                if (progressIndicator.getProgress()!=0.0){
                    System.out.println("正在下载！");
                    return;
                }
                if (currentPlayBean!=null){
                    if (!currentPlayBean.isLocalMusic()){
                        /*去除windows系统中文件名的非法路径*/
                        String validateMusicName=validateFileName(currentPlayBean.getSaveFileName());
                        String MusicSavepath=LocalMusicUtils.LOCAL_MUSIC_DIR+validateMusicName+".mp3";
                        boolean isSuccessdownload=FileDown.downloadFile(currentPlayBean.getMp3Url(),
                                MusicSavepath,progressIndicator,downProgressLabel);
                        if (isSuccessdownload){
                            try {
                                BufferedImage bufferedImage = SwingFXUtils.fromFXImage(panImageView.getImage(), null);
                                LocalMusicUtils.setMusicInf(currentPlayBean,MusicSavepath,bufferedImage);//设置MP3的头文件信息
                                if (currentPlayBean.getLrc()!=null&&!currentPlayBean.getLrc().contains("未找到歌词")&&currentPlayBean.getLrc().trim().length()!=0){
                                    String lrcString = currentPlayBean.getLrc();
                                    String LrcSavePath=LocalMusicUtils.LOCAL_LRC_DIR+validateMusicName+".lrc";
                                    LrcWriteUtils.writeFile(lrcString,LrcSavePath);
                                }
                                Desktop.getDesktop().open(new File(LocalMusicUtils.LOCAL_MUSIC_DIR));
                            } catch (IOException e) {
                                System.out.println(e.toString());
                            }
                            alert.headerTextProperty().set("下载成功");
                        }
                        else {
                            alert.headerTextProperty().set("下载失败");
                        }
                    }
                    else {
                        alert.headerTextProperty().set("本地音乐无法下载");
                    }
                }
                else {
                    alert.headerTextProperty().set("未选择歌曲，无法下载！");
                }
                Platform.runLater(()->{
                    alert.showAndWait();
                    progressIndicator.setProgress(0.0);
                    downProgressLabel.setText("0%");
                    /*Windows任务栏图标闪烁效果}*/
                    if(!staticStage.isFocused()){
                        staticStage.requestFocus();
                    }
                });
            }).start();
        });
        downProgressLabel=new Label("0%");
        downProgressLabel.setPrefWidth(40);
        downProgressLabel.setTextFill(Color.WHITE);
        downProgressLabel.setFont(Font.font("Timer New Roman",
                FontWeight.BOLD, FontPosture.ITALIC, 14));
        downIvLab.setPrefHeight(20);
        downIvLab.setTextFill(Color.rgb(210,210,210));

        progressIndicator=new ProgressIndicator(0.0);
        progressIndicator.setMaxSize(45,45);
        HBox downHbox=new HBox(10);
        downHbox.setPadding(new Insets(5,5,5,10));
        downHbox.getChildren().addAll(downIv,downIvLab,downProgressLabel,progressIndicator);

        //已创建歌单：Lable
        Label labGd = new Label("收藏与创建歌单");
        labGd.setPrefHeight(20);
        labGd.setTextFill(Color.WHITE);
        labGd.setFont(Font.font("Timer New Roman",
                FontWeight.BOLD, FontPosture.ITALIC, 14));
        HBox localHbox=new HBox(10);
        localHbox.setPadding(new Insets(5,5,5,10));

        Label refreshLabGd = new Label("换歌单",new ImageView("img/left/refresh.png"));
        refreshLabGd.setPrefHeight(20);
        refreshLabGd.setTextFill(Color.WHITE);
        refreshLabGd.setFont(Font.font("Timer New Roman",
                FontWeight.BOLD, FontPosture.ITALIC, 14));

        refreshLabGd.setOnMouseClicked(event -> {
            new Thread(()->{
                //随机获取歌单
                cloudMusicSpider.getPlayList(playListBeanList);
                //用GUI线程更新UI组件
                Platform.runLater(()->{
                    for (int i=0;i<playListBeanList.size();i++){
                        PlayListBean playListBean=playListBeanList.get(i);
                        Label label = (Label) groupVBox.getChildren().get(i);
                        label.setText(playListBean.getAlbum());
                    }
                });
            }).start();
        });

        HBox refreshHbox=new HBox(20);
        refreshHbox.getChildren().addAll(labGd,refreshLabGd);

        localHbox.getChildren().addAll(musicIco,localLabGroupName,dirIcoivLab);
        localVBox.getChildren().addAll(locallabGd,localHbox,downHbox,refreshHbox);
        VBox.setMargin(refreshHbox,new Insets(5,0,10,5));
        VBox.setMargin(localHbox,new Insets(5,0,0,5));
        VBox.setMargin(downHbox,new Insets(5,0,0,5));
        VBox.setMargin(labGd,new Insets(0,10,0,5));
        VBox.setMargin(locallabGd,new Insets(0,0,0,5));


        groupVBox = new VBox(10);
        groupVBox.setPrefWidth(255);
        groupVBox.setPadding(new Insets(5,5,5,10));
        //将每个"歌单名字"封装为一个"HBox"对象
        for (int i=0;i<playListBeanList.size();i++){
            ImageView musicIcoImageView=new ImageView("img/left/music.png");
            musicIcoImageView.setPreserveRatio(true);
            musicIcoImageView.setFitWidth(20);
            PlayListBean playListBean=playListBeanList.get(i);
            //歌单名称：Label
            Label labGroupName1 = new Label(playListBean.getAlbum(),musicIcoImageView);
            labGroupName1.setPrefHeight(20);
            labGroupName1.setPrefWidth(200);
            labGroupName1.setTextFill(Color.rgb(210,210,210));
            final int finalI = i;
            labGroupName1.setOnMouseClicked(event -> {
                PlayListBean playListBean1=playListBeanList.get(finalI);
                this.labGroupName.setText(playListBean1.getAlbum());
                if (searchTiplabel.getText().equals("正在搜索，请稍后再操作....")){
                    return;
                }
                tableView.getItems().clear();//清空表格
                searchTiplabel.setText("正在搜索，请稍后再操作....");
                new Thread(()->{
                    //搜索歌曲
                    cloudMusicSpider.getSongList(playListBean1,list);
                    //用GUI线程更新UI组件
                    Platform.runLater(()->{
                        tableView.setItems(FXCollections.observableList(list));
                        searchTiplabel.setText("");
                        /*Windows任务栏图标闪烁效果}*/
                        if(!staticStage.isFocused()){
                            staticStage.requestFocus();
                        }
                    });
                    if (list.size()!=0){
                        currentIndex=0;
                    }
                    //System.gc();
                }).start();
            });
            groupVBox.getChildren().add(labGroupName1);
        }

        //总面板
        BorderPane leftPane = new BorderPane();
        leftPane.setTop(localVBox);
        leftPane.setCenter(groupVBox);

        BorderStroke bs = new BorderStroke(
                Color.rgb(255,255,255),//四个边的颜色
                Color.rgb(255,255,255),
                Color.rgb(255,255,255),
                Color.rgb(255,255,255),
                BorderStrokeStyle.SOLID,//四个边的线型--实线
                BorderStrokeStyle.SOLID,
                BorderStrokeStyle.SOLID,
                BorderStrokeStyle.SOLID,
                new CornerRadii(1),
                new BorderWidths(1),
                new Insets(1,1,1,1)

        );
        leftPane.setBorder(new Border(bs));

        return leftPane;
    }

    //创建上侧面板
    private BorderPane getTopPane() {

        HBox logohBox=new HBox(0);
        logohBox.setPadding(new Insets(2,2,2,2));//设置内部元素与四边的间距
        //1.左侧的Logo
        ImageView imgView = new ImageView("img/topandbottom/logo.jpg");
        imgView.setFitHeight(40);//设置图片的高度：40像素
        imgView.setPreserveRatio(true);//根据图片设置的高度，保持宽高比；
        Circle circle = new Circle();
        circle.setCenterX(20);
        circle.setCenterY(20);
        circle.setRadius(20);//圆的半径
        imgView.setClip(circle);
        Label logoLabel=new Label("",imgView);

        t2 = new Timeline();
        t2.getKeyFrames().addAll(
                new KeyFrame(new Duration(0),new KeyValue(imgView.rotateProperty(),0)),
                new KeyFrame(new Duration(300), new KeyValue(imgView.rotateProperty(),360))
        );
        t2.setCycleCount(1);
        logoLabel.setOnMouseEntered(event -> {
            t2.play();
        });

        Label labtitle = new Label("水瓶座鬼才");
        labtitle.setTextFill(Color.WHITE);
        labtitle.setFont(Font.font("Timer New Roman",
                FontWeight.BOLD, FontPosture.ITALIC, 18));
        labtitle.setAlignment(Pos.CENTER);//字体居中

        BorderStroke bs = new BorderStroke(
                Color.rgb(255,255,255),//四个边的颜色
                Color.rgb(255,255,255),
                Color.rgb(255,255,255),
                Color.rgb(255,255,255),
                BorderStrokeStyle.SOLID,//四个边的线型--实线
                BorderStrokeStyle.SOLID,
                BorderStrokeStyle.SOLID,
                BorderStrokeStyle.SOLID,
                new CornerRadii(1),
                new BorderWidths(1),
                new Insets(1,1,1,1)

        );
        logohBox.setBorder(new Border(bs));
        logohBox.getChildren().addAll(logoLabel,labtitle);
        logohBox.setPrefWidth(255);
        HBox.setMargin(logoLabel,new Insets(0,5,0,20));
        HBox.setMargin(labtitle,new Insets(10,5,0,5));

        search_TextField=new TextField();
        ImageView searchImageView=new ImageView("img/topandbottom/Search.png");
        searchImageView.setFitWidth(22.5);
        searchImageView.setFitHeight(22.5);
        Button searchButton=new Button("",searchImageView);
        searchButton.setOnAction(event -> {
            if (search_TextField.getText().trim().length()==0){
                System.out.println("搜索信息不能为空");
                return;
            }
            if (searchTiplabel.getText().equals("正在搜索，请稍后再操作....")){
                return;
            }
            tableView.getItems().clear();//清空表格
            searchTiplabel.setText("正在搜索，请稍后再操作....");
            new Thread(()->{
                //搜索歌曲
                cloudRequest.connection(search_TextField.getText(),list);
                Platform.runLater(()->{
                    tableView.setItems(FXCollections.observableList(list));
                    searchTiplabel.setText("");
                    labGroupName.setText(search_TextField.getText().trim());
                    /*Windows任务栏图标闪烁效果}*/
                    if(!staticStage.isFocused()){
                        staticStage.requestFocus();
                    }
                });
                if (list.size()!=0){
                    currentIndex=0;
                }
            }).start();
        });
        searchTiplabel=new Label("");

        searchTiplabel.setFont(Font.font("Timer New Roman",
                FontWeight.BOLD, FontPosture.ITALIC, 18));
        searchTiplabel.setAlignment(Pos.CENTER);//字体居中
        searchTiplabel.setTextFill(Color.RED);
        search_TextField=new TextField();
        HBox hBox = new HBox();//HBox将孩子放在一排水平排列中。 如果hbox有一个边框和/或填充集合，那么这些内容将被放置在这些插入内。
        hBox.setAlignment(Pos.CENTER_LEFT);//左对齐
        hBox.setPrefHeight(50);
        hBox.setMaxHeight(50);
        //个Insets对象是容器边框的表示。 它指定容器必须在其每个边缘处留下的空间。 空格可以是边框，空格或标题。
        hBox.setPadding(new Insets(5,5,5,5));//设置内部元素与四边的间距
        hBox.getChildren().addAll(logohBox,search_TextField,searchButton,searchTiplabel);
        HBox.setMargin(search_TextField,new Insets(0,10,0,10));
        HBox.setMargin(searchTiplabel,new Insets(0,20,0,10));

        //2.右侧的最小化按钮
        ImageView v1 = new ImageView("img/topandbottom/MinmizeDark.png");
        v1.setFitWidth(15);
        v1.setFitHeight(15);
        Label lab1 = new Label("", v1);
        lab1.setMinWidth(0);//设置Label的最小宽度
        lab1.setMinHeight(0);//设置Label的最小高度
        lab1.setPrefWidth(15);
        lab1.setPrefHeight(15);

        //鼠标点击事件
        lab1.setOnMouseClicked(e -> staticStage.setIconified(true));//将"舞台"最小化

        //3.右侧的最大化按钮
        ImageView v2 = new ImageView("img/topandbottom/MaximizeDark.png");
        v2.setFitWidth(15);
        v2.setFitHeight(15);
        Label lab2 = new Label("",v2);
        lab2.setMinWidth(0);
        lab2.setMinHeight(0);
        lab2.setPrefWidth(15);
        lab2.setPrefHeight(15);
        lab2.setOnMouseClicked(e -> {
            //如果当前窗体是正常状态，应该：最大化
            if(!staticStage.isMaximized()){//正常状态
                //记录之前舞台的x,y坐标，宽度、高度的值
                resetX = staticStage.getX();
                resetY = staticStage.getY();
                resetWidth = staticStage.getWidth();
                resetHeight = staticStage.getHeight();
                //最大化
                staticStage.setMaximized(true);
                //设置图片
                v2.setImage(new Image("img/topandbottom/resetDark.png"));
            }else{
                //如果当前窗体是最大化状态，应该：还原
                staticStage.setX(resetX);
                staticStage.setY(resetY);
                staticStage.setWidth(resetWidth);
                staticStage.setHeight(resetHeight);
                //设置还原状态
                staticStage.setMaximized(false);

                //设置图片
                v2.setImage(new Image("img/topandbottom/MaximizeDark.png"));
            }
        });


        //4.右侧的关闭按钮
        ImageView v3 = new ImageView("img/topandbottom/CloseDark.png");
        v3.setFitWidth(15);
        v3.setFitHeight(15);
        Label lab3 = new Label("", v3);
        lab3.setMinWidth(15);
        lab3.setMinHeight(15);
        lab3.setPrefWidth(15);
        lab3.setPrefHeight(15);
        lab3.setOnMouseClicked(e -> {
            System.exit(0);//结束JVM
        });

        HBox hBox2 = new HBox(20);//内部元素之间的间距：10像素
        hBox2.setAlignment(Pos.CENTER_LEFT);//void setAlignment​(int alignment) 将此标签的对齐方式设置为指定的对齐方式。
        hBox2.setPrefWidth(110);
        hBox2.setPrefHeight(50);
        hBox2.getChildren().addAll(lab1,lab2,lab3);
        hBox2.setPadding(new Insets(5,0,0,0));

        //下侧的红线
        Rectangle rct = new Rectangle();
        rct.setX(0);
        rct.setY(0);
        rct.setWidth(100);
        rct.setHeight(2);
        //设置背景色--渐变
        Stop[] stops = new Stop[]{
                new Stop(0, Color.rgb(120, 8, 14)),
                new Stop(0.5, Color.RED),
                new Stop(1, Color.rgb(120, 8, 14))
        };

        rct.setFill(new LinearGradient(0,0,1,1,
                true, CycleMethod.NO_CYCLE,stops));//渐变固定写法

        BorderPane topPane = new BorderPane();
        topPane.setLeft(hBox);
        topPane.setRight(hBox2);
        topPane.setBottom(rct);

        //将rct的宽度绑定到stage的宽度上
        rct.widthProperty().bind(staticStage.widthProperty());

        //当鼠标按下时
        topPane.setOnMousePressed(e -> {
            //记录鼠标相对于窗体(Scene)的X,Y坐标
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
        });
        //当鼠标拖拽时
        topPane.setOnMouseDragged(e -> {
            //设置新的X,Y
            staticStage.setX(e.getScreenX() - mouseX);
            staticStage.setY(e.getScreenY() - mouseY);
        });
        return topPane;
    }
    private ChangeListener<Duration> initChangeListener(){
        ChangeListener<Duration> changeListener = new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observable,
                                Duration oldValue, Duration newValue) {
               /* 此方法用于在媒体播放器播放时自动调用，每隔100毫秒调用一次
                1.由于是每秒使滚动条前进一次，获newValue中的"秒"*/

                /*如果总时间还是为00:00，证明之前未加载歌曲总时间，就在这里尝试再次获取总时间*/
                if (labTotalTime.getText().equals("00:00")) {
                    int total_second = (int) Math.floor(mediaPlayer.getTotalDuration().toSeconds());
                    /*总秒数不为0才设置滚动条最大值*/
                    if (total_second != 0) {
                        date.setTime(total_second * 1000);
                        labTotalTime.setText(simpleDateFormat.format(date));
                        sliderSong.setMax(total_second);
                    }
                }

                currentSecond = (int) newValue.toSeconds();
                //2.设置滚动条，一秒一次
                if (currentSecond == prevSecond + 1) {
                    //设置滚动条
                    sliderSong.setValue(sliderSong.getValue() + 1);
                    //设置前一秒
                    prevSecond++;
                    //设置新的播放时间
                    date.setTime((int) sliderSong.getValue() * 1000);
                    labPlayTime.setText(simpleDateFormat.format(date));
                }
                //1.获取当前的播放时间
                millis = newValue.toMillis();
                //2.判断此次是否在正常的播放区间
                min = 0;
                max = 0;
                if (lrcList.size() == 0) {
                    return;
                }
                if (currentLrcIndex == 0) {
                    min = 0;
                } else {
                    min = lrcList.get(currentLrcIndex).doubleValue();
                }
                if (currentLrcIndex != lrcList.size() - 1) {
                    max = lrcList.get(currentLrcIndex + 1).doubleValue();
                } else {
                    max = lrcList.get(currentLrcIndex).doubleValue();
                }
                //判断是否在正常的区间
                if (millis >= min && millis < max) {
                    return;
                }

                if (currentLrcIndex < lrcList.size() - 1 &&
                        millis >= lrcList.get(currentLrcIndex + 1).doubleValue()) {
                    currentLrcIndex++;//当前歌词索引的指示器
                    //上移
                    t1.play();
                    //当前歌词变黄，字号：18
                    Label lab_current = (Label) lrcVBox.getChildren().get(currentLrcIndex);
                    lab_current.setTextFill(Color.YELLOW);
                    //前一行变小，变为：浅灰色
                    Label lab_Pre_1 = (Label) lrcVBox.getChildren().get(currentLrcIndex - 1);
                    if (lab_Pre_1 != null) {
                        lab_Pre_1.setTextFill(Color.rgb(114, 114, 114));
                    }

                    //前二行
                    if (currentLrcIndex - 2 >= 0) {
                        Label lab_Pre_2 = (Label) lrcVBox.getChildren().get(currentLrcIndex - 2);
                        lab_Pre_2.setTextFill(Color.rgb(53, 53, 53));
                    }

                    //当前行的后一行，白色
                    if (currentLrcIndex + 1 < lrcList.size()) {
                        Label lab_next_1 = (Label) lrcVBox.getChildren().get(currentLrcIndex + 1);
                        lab_next_1.setTextFill(Color.BLACK);
                    }
                } else if (currentLrcIndex > 0 && millis < lrcList.get(currentLrcIndex).doubleValue()) {
                    //拖动播放条，回退了
                    currentLrcIndex--;
                    //歌词VBox的下移
                    lrcVBox.setLayoutY(lrcVBox.getLayoutY() + 45);
                    //当前歌词变黄，字号：18
                    Label lab_current = (Label) lrcVBox.getChildren().get(currentLrcIndex);
                    lab_current.setTextFill(Color.YELLOW);

                    //前一行变为：浅灰
                    if (currentLrcIndex - 1 >= 0) {
                        Label lab = (Label) lrcVBox.getChildren().get(currentLrcIndex - 1);
                        lab.setTextFill(Color.rgb(114, 114, 114));
                    }
                    //后一行变为百色：字号：12
                    if (currentLrcIndex + 1 < lrcVBox.getChildren().size()) {
                        Label lab = (Label) lrcVBox.getChildren().get(currentLrcIndex + 1);
                        lab.setTextFill(Color.BLACK);
                    }
                    //后二行，变为浅灰
                    if (currentLrcIndex + 2 < lrcVBox.getChildren().size()) {
                        Label lab = (Label) lrcVBox.getChildren().get(currentLrcIndex + 2);
                        lab.setTextFill(Color.rgb(114, 114, 114));
                    }
                    //后三行，变为深灰
                    if (currentLrcIndex + 3 < lrcVBox.getChildren().size()) {
                        Label lab = (Label) lrcVBox.getChildren().get(currentLrcIndex + 3);
                        lab.setTextFill(Color.rgb(53, 53, 53));
                    }
                }
            }
        };
        return changeListener;
    }
    private Runnable initRunnable(){
        Runnable value=new Runnable() {
            @Override
            public void run(){
                //如果表格为空,比如用户在搜索歌曲过程中，搜不到歌曲，表格数据被清空了
                if (tableView.getItems().size()==0){
                    if (mediaPlayer!=null) {
                        mediaPlayer.stop();
                        mediaPlayer.dispose();//释放资源
                        mediaPlayer = null;
                    }
                    butPlayImage.setImage(new Image("img/topandbottom/Play.png"));
                }
                else {
                    //根据当前的播放模式选择下一首歌
                    switch (playMode) {
                        case 1://循环播放
                            currentIndex++;
                            if (currentIndex >= tableView.getItems().size()) {
                                currentIndex = 0;
                            }
                            currentPlayBean = tableView.getItems().get(currentIndex);
                            break;
                        case 2://列表顺序播放
                            currentIndex++;
                            if (currentIndex >= tableView.getItems().size()) {
                                return;
                            }
                            currentPlayBean = tableView.getItems().get(currentIndex);
                            break;
                        case 3://单曲循环
                            break;
                    }
                    tableView.getSelectionModel().select(currentIndex);
                    play();
                }
            }
        };
        return value;
    }
    private String validateFileName(String fileName){
        Matcher matcher = pattern.matcher(fileName);
        String newFileName = matcher.replaceAll(""); // 将匹配到的非法字符以空替换
        return newFileName;
    }
    public static void main(String[] args) {
        launch(args);
    }

}
