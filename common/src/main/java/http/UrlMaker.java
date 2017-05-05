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

/**
 * Created by huangzebin on 2017/3/6.
 */
public class UrlMaker {

    private static final Logger logger = LogManager.getLogger();

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


    public String getUrl(){
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
            this.uri = strURL.replace("?" + query, "");
            parseParams(query);
        }else{
            this.uri = strURL;
        }


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
}
