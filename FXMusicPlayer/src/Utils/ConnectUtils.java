package Utils;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

/*import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;*/
import javax.net.ssl.SSLException;
import java.util.Map;

/*
 * @Author :zhaoyijie（Aquarius_genius）水瓶座鬼才
 * @create :2019/11/5 19:29
 * @Email   : 747897928@qq.com
 * @Description :获取网页源代码的父类
 */
public class ConnectUtils {

    /**
     * @Description: GET请求获取目标网页的源代码，请求头为安卓手机
     * @param url url链接
     * @return 目标网页的源代码
     */
    public String connect(String url) {
        try {
            //获取一个连接
            Connection connection = Jsoup.connect(url).ignoreContentType(true);
            connection.timeout(15000);
            //设置请求方式
            connection.method(Connection.Method.GET);
            connection.header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.9 Safari/537.36");
            //获得响应
            Connection.Response response = connection.execute();
            //返回响应网页源代码
            if(response.statusCode()==403||response.statusCode()==404){
                System.out.println(response.statusCode());
                return "error";
            }
            return response.body();
        }catch (SSLException e){
            System.out.println(e.toString());
            url=url.replace("https","http");
            String html=connect(url);
            return html;
        }
        catch (Exception e){
            System.out.println(e.toString());
            return "error";
        }
    }
    /**
     * @param url url链接
     * @param headers 请求头
     * @param type 请求方式
     * @return 目录网页的源代码
     */
    public String connect(String url, Connection.Method type, Map<String, String> headers) {
        try {
            //获取一个连接
            Connection connection = Jsoup.connect(url).ignoreContentType(true);
            connection.timeout(15000);
            //设置请求方式
            connection.method(type);
            // 添加headers
            if(headers != null && headers.size() > 0){
                for (String header : headers.keySet()){
                    connection.header(header,headers.get(header));
                }
            }
            //获得响应
            Connection.Response response = connection.execute();
            //返回响应网页源代码
            if(response.statusCode()==403||response.statusCode()==404){
                System.out.println(response.statusCode());
                return "error";
            }
            return response.body();
        } catch (SSLException e){
            System.out.println(e.toString());
            url=url.replace("https","http");
            String html=connect(url);
            return html;
        }
        catch (Exception e){
            System.out.println(e.toString());
            return "error";
        }
    }
    /**
     * @Description: 获取服务端响应
     * @param url url链接
     * @param headers 请求头
     * @param type 请求方式
     * @param data post请求携带的参数
     * @param timeout 请求超时时间
    *  @return: org.jsoup.Connection.Response
    */
    public Connection.Response connect(String url, Connection.Method type, Map<String,String> headers, Map<String,String> data, int timeout){
        try {
            //获取一个连接
            Connection connection = Jsoup.connect(url).ignoreContentType(true);
            connection.timeout(timeout);
            //设置请求方式
            connection.method(type);
            // 添加headers
            if(headers != null && headers.size() > 0){
                for (String header : headers.keySet()){
                    connection.header(header,headers.get(header));
                }
            }

            //携带Post请求参数
            if(data != null && data.size() > 0){
                connection.data(data);
            }
            //获得响应
            Connection.Response response = connection.execute();
            //返回响应
            return response;
        }catch (SSLException e){
            System.out.println(e.toString());
            url=url.replace("https","http");
            Connection.Response connect = connect(url, type, headers, data, timeout);
            return connect;
        }
        catch (Exception e){
            System.out.println(e.toString());
            return null;
        }
    }
}
