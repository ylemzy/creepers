package http;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;
import org.springframework.util.Assert;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by huangzebin on 2017/3/6.
 */
public class UrlMaker {

    private static final Logger logger = LogManager.getLogger();


    private final static String regex = "^((https|http|ftp|rtsp|mms)://)"
            + "(([0-9a-z_]+:)?([A-Za-z0-9_]+)@)?" //user:password@
            + "(([0-9]{1,3}\\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184
            + "|" // 允许IP和DOMAIN（域名）
            + "([0-9a-z_!~*'()-]+\\.)*" // 域名- www.
            + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\\." // 二级域名
            + "[a-z]{2,6})" // first level domain- .com or .museum
            + "(:[0-9]{1,4})?" // 端口- :80
            + "((/?)|" // a slash isn't required if there is no file name
            + "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";
    private static Pattern pattern = Pattern.compile(regex);


    Map<String, String> params = new HashMap<>();
    URL url = null;
    String link = null;
    String uri = null;

    String[] img = {
            "jpg", "jpeg", "png", "gif", "ico"
    };

    String[] code = {
            ".css", ".js", ""

    };

    public static UrlMaker make(String url) throws MalformedURLException {
        return new UrlMaker(url);
    }

    public UrlMaker(String url) throws MalformedURLException {
        this.link = url;
        this.url = new URL(url);
        parse(url);
    }

    public String getUri() {
        return uri;
    }

    public URL getUrl() {
        try {
            return new URL(getRowUrl());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public String getRowUrl(){
        if (params.size() == 0){
            return uri;
        }

        StringBuffer buffer = new StringBuffer();
        params.forEach( (k, v)->{
            buffer.append(k + "=" + v + "&");
        });
        if (buffer.length() > 0){
            buffer.setLength(buffer.length() - 1);
        }
        return uri + "?" + buffer.toString();
    }

    public String getHost(){
        return url.getHost();
    }

    public UrlMaker param(String key, String value){
        if (this.params == null){
            this.params = new HashMap<>();
        }

        this.params.put(key, value);
        return this;
    }

    public String param(String key){
        if (params == null)
            return null;
        return params.get(key);
    }

    public String getDomain(){
        return url.getHost() + (url.getPort() == -1 ? "" : ":" + url.getPort());
    }

    private void parse(String strURL) {

        String query = url.getQuery();

        if (StringUtils.isNotEmpty(query)){
            parseParams(query);
        }

        StringBuffer tmp = new StringBuffer();
        tmp.append(url.getProtocol());
        tmp.append("://");
        tmp.append(url.getHost());

        if (url.getPort() != -1){
            tmp.append(":");
            tmp.append(url.getPort());
        }
        uri = tmp.toString();


        Assert.isTrue(!Strings.isBlank(this.uri), "Uri is empty");
    }

    private void parseParams(String paramUrl) {
        if (StringUtils.isEmpty(paramUrl))
            return;

        String[] split = paramUrl.split("[&]");
        for (String s : split) {
            String[] keyValue = s.split("[=]");
            if (keyValue.length == 2){
                this.params.put(keyValue[0], keyValue[1]);
            }else if (keyValue.length == 1){
                this.params.put(keyValue[0], "");
            }
        }
    }


    public static boolean isURL(String url){
        return pattern.matcher(url).matches();
    }
}
