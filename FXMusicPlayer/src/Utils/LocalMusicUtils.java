package Utils;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.*;
import org.jaudiotagger.tag.id3.AbstractID3v2Frame;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import org.jaudiotagger.tag.id3.framebody.FrameBodyAPIC;
import org.jaudiotagger.tag.images.Artwork;
import org.jaudiotagger.tag.images.ArtworkFactory;
import pojo.PlayBean;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 水瓶座鬼才（zhaoyijie）
 * @Description:
 * @date 2020/3/3 8:18
 */
public class LocalMusicUtils {
    public final static String LOCAL_LRC_DIR=System.getProperty("user.dir")+"/LocalMusic/Lrc/";
    public final static String LOCAL_MUSIC_DIR=System.getProperty("user.dir")+"/LocalMusic/Music/";

    public static String getLrc(String lrcPath){
        String lrc=null;
        File lrcFile = new File(lrcPath);
        if (!lrcFile.exists()) {
            lrc="[00:00.00]无歌词";
        }
        else {
            try {
                Long filelength = lrcFile.length();
                byte[] filecontent = new byte[filelength.intValue()];
                FileInputStream in = new FileInputStream(lrcFile);
                in.read(filecontent);
                in.close();
                lrc=new String(filecontent,"UTF-8");
            } catch (Exception e) {
                System.err.println(e);
                lrc="[00:00.00]无歌词";
                return lrc;
            }
        }
        return lrc;
    }

    public static void createLocalMusicDir(){
        File parentMusicFile=new File(LOCAL_MUSIC_DIR);
        if (!parentMusicFile.exists()){
            parentMusicFile.mkdirs();
        }
        File parentLrcFile=new File(LOCAL_LRC_DIR);
        if (!parentLrcFile.exists()){
            parentLrcFile.mkdirs();
        }
    }
    public static void getLocalMusicInf(List<PlayBean> list){
        createLocalMusicDir();
        File[] filelist=new File(LOCAL_MUSIC_DIR).listFiles();
        for(int i=0;i<filelist.length;i++){
            list.add(new PlayBean(filelist[i].getName()));
        }
        for (int i = 0; i < list.size(); i++) {
            PlayBean playBean = list.get(i);
            //读取音频文件
            File file = new File(LOCAL_MUSIC_DIR+playBean.getMusicName());
            //解析文件
            AudioFile audioFile = null;
            try {
                audioFile = AudioFileIO.read(file);
            } catch (Exception e) {
                System.out.println(e.toString());
                list.remove(i);
                continue;
            }
            Tag tag = audioFile.getTag();
            String songName =  tag.getFirst(FieldKey.TITLE);//歌名
            String artist = tag.getFirst(FieldKey.ARTIST);//演唱者
            String album =tag.getFirst(FieldKey.ALBUM);//专辑名称
            String fileName = null;
            try {
                fileName = playBean.getMusicName().substring(0, playBean.getMusicName().lastIndexOf('.'));
            } catch (Exception e) {
                System.out.println(e.toString());
                fileName="无名歌曲";
            }
            //为PlayBean赋值
            if (artist!=null&&!artist.equals("")){
                playBean.setArtistName(artist);
            }
            if (album!=null&&!album.equals("")){
                playBean.setAlbum(album);
            }
            if (songName!=null&&!songName.equals("")){
                playBean.setMusicName(songName);
            }
            else {
                playBean.setMusicName(fileName);
            }
            playBean.setLocalMusic(true);
            playBean.setMp3Url(file.toURI().toString());
            String lrcPath=LocalMusicUtils.LOCAL_LRC_DIR+fileName+".lrc";
            playBean.setLrc(getLrc(lrcPath));
            /*if (audioFile.getExt().equalsIgnoreCase("mp3")){
                AbstractID3v2Tag abstractID3v2Tag = ((MP3File)audioFile).getID3v2Tag();
                AbstractID3v2Frame frame = (AbstractID3v2Frame)abstractID3v2Tag.getFrame("APIC");
                if (frame != null) {
                    FrameBodyAPIC body = (FrameBodyAPIC) frame.getBody();
                    byte[] imageData = body.getImageData();
                    //将字节数组转换为Image对象
                    Image image = Toolkit.getDefaultToolkit().createImage(imageData, 0, imageData.length);
                    BufferedImage bufferedImage = ImageUtils.toBufferedImage(image,width,height);
                    WritableImage writableImage = SwingFXUtils.toFXImage(bufferedImage, null);
                    playBean.setImage(writableImage);
                }
            }*/
        }
    }

    public static void setMusicInf(PlayBean playBean,String musicPath){
        File file = new File(musicPath);
        AudioFile audioFile = null;
        try {
            audioFile = AudioFileIO.read(file);
        } catch (CannotReadException e) {
            System.err.println(e);
            return;
        } catch (IOException e) {
            System.err.println(e);
            return;
        } catch (TagException e) {
            System.err.println(e);
            return;
        } catch (ReadOnlyFileException e) {
            System.err.println(e);
            return;
        } catch (InvalidAudioFrameException e) {
            System.err.println(e);
            return;
        }
        Tag tag = audioFile.getTag();
        try {
            tag.setField(FieldKey.TITLE,playBean.getMusicName());
            if (!playBean.getArtistName().equals("")){
                tag.setField(FieldKey.ARTIST,playBean.getArtistName());
            }
            tag.setField(FieldKey.ALBUM,playBean.getAlbum());
        } catch (FieldDataInvalidException e) {
            System.err.println(e.toString());
        }
        try {
            audioFile.commit();
        } catch (CannotWriteException e) {
            System.err.println(e);
        }
    }

    public static WritableImage getLocalMusicArtwork(File file,int width,int height){
        //解析文件
        AudioFile audioFile = null;
        try {
            audioFile = AudioFileIO.read(file);
        }catch (NullPointerException e) {
            System.out.println("该歌曲无专辑图片");
            return null;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        Tag tag = audioFile.getTag();;
        /*获取音乐封面*/
        try {
            BufferedImage artwork = (BufferedImage) tag.getFirstArtwork().getImage();
            Graphics2D graphics = (Graphics2D) artwork.getGraphics();
            graphics.scale((width/artwork.getWidth()), (height/artwork.getHeight()));
            graphics.drawImage(artwork, 0, 0, null);
            graphics.dispose();
            WritableImage writableImage= SwingFXUtils.toFXImage(artwork, null);
            return writableImage;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public static void setMusicInf(PlayBean playBean,String musicPath,BufferedImage bufferedImage){
        File file = new File(musicPath);
        AudioFile audioFile = null;
        try {
            audioFile = AudioFileIO.read(file);
        } catch (CannotReadException e) {
            System.err.println(e);
            return;
        } catch (IOException e) {
            System.err.println(e);
            return;
        } catch (TagException e) {
            System.err.println(e);
            return;
        } catch (ReadOnlyFileException e) {
            System.err.println(e);
            return;
        } catch (InvalidAudioFrameException e) {
            System.err.println(e);
            return;
        }
        Tag tag = audioFile.getTag();
        try {
            tag.setField(FieldKey.TITLE,playBean.getMusicName());
            if (!playBean.getArtistName().equals("")){
                tag.setField(FieldKey.ARTIST,playBean.getArtistName());
            }
            tag.setField(FieldKey.ALBUM,playBean.getAlbum());
        } catch (FieldDataInvalidException e) {
            System.err.println(e.toString());
        }
        File file1 = new File(System.getProperty("user.dir")+"/LocalMusic/TmpImage.jpg");
        try {
            ImageIO.write(bufferedImage, "jpg", file1);
            try {
                Artwork artwork = ArtworkFactory.createArtworkFromFile(file1);
                try {
                    tag.setField(artwork);
                } catch (FieldDataInvalidException e) {
                    System.err.println(e);
                }
            } catch (IOException e) {
                System.err.println(e);
            }
        } catch (IOException e) {
            System.err.println(e);
        }
        try {
            audioFile.commit();
        } catch (CannotWriteException e) {
            System.err.println(e);
        }
        file1.delete();
    }
}
