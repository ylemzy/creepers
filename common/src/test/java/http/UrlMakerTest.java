package http;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by huangzebin on 2017/5/17.
 */
public class UrlMakerTest {
    private static final Logger logger = LogManager.getLogger();

    @Test
    public void getUri() throws Exception {
        String uri = UrlMaker.make("mailto://").getUri();
        URI uri1 = URI.create("https://zhidao.baidu.com/question/1608427063437488827.html");
        logger.info("-->> {}", uri1);
    }

    @Test
    public void test(){
        String url1 = "http://www.xx.com:9800:9";
        String url2 = "w.xx.com";
        String url3 = "https://w.xx.com";
        String url4 = "xxxx";
        String url5 = "http://192.1.1.1:8990";
        String url6= "http://192.1.1:8990";
        String url7= "http://www.xx.com:8990";
        String url8= "http://user:password@www.xx.com:8990";
        String url9= "http://www.xx.com:8990/index.html";
        String url10= "mailto://";

        System.out.println(isURL2(url1));
        System.out.println(isURL2(url2));
        System.out.println(isURL2(url3));
        System.out.println(isURL2(url4));
        System.out.println(isURL2(url5));
        System.out.println(isURL2(url6));
        System.out.println(isURL2(url7));
        System.out.println(isURL2(url8));
        System.out.println(isURL2(url9));
        System.out.println(isURL2(url10));
    }

    public boolean isURL2(String url){
        String regex = "^((https|http|ftp|rtsp|mms)://)"
                + "(([0-9a-z_]+:)?([A-Za-z0-9_]+)@)?" //user:password@
                + "(([0-9]{1,3}\\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184
                + "|" // 允许IP和DOMAIN（域名）
                + "([0-9a-z_!~*'()-]+\\.)*" // 域名- www.
                 + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\\." // 二级域名
                + "[a-z]{2,6})" // first level domain- .com or .museum
                + "(:[0-9]{1,4})?" // 端口- :80
                + "((/?)|" // a slash isn't required if there is no file name
                + "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(url).matches();
    }
}