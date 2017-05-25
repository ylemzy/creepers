package application.elastic.util;

import http.UrlMaker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.junit.Test;
import util.DateTimeUtil;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by huangzebin on 2017/4/17.
 */
public class DateTimeUtilTest {
    private static final Logger logger = LogManager.getLogger();

    @Test
    public void parse() throws Exception {

    }

    @Test
    public void parseDaily() throws Exception {
        URL url = new URL("http://localhost:8080/index/test?a=1&b=3");
        String query = url.getQuery();

        System.out.println(url.getHost());
        System.out.println(url.getQuery());
        System.out.println(url.getPath());
        System.out.println(url.getRef());
        System.out.println(url.getFile());
        System.out.println(url.getPort());
        System.out.println(url.getProtocol());

        UrlMaker urlMaker = UrlMaker.make("http://localhost:8080/index/test?a=1&b=3");
        System.out.println(urlMaker.getDomain());

        UrlMaker urlMaker2 = UrlMaker.make("http://www.baidu.com/index/test?a=1&b=3");
        System.out.println(urlMaker2.getDomain());
    }

    String[] dateTimes = new String[]{
            "2017年03月28日 06:30",
            "2017年03月28日",
            "2017-03-30 10:40",
            "2017-03-30 10:40:59",
            "2017-03-30",
            "2017年03月31日 06:30",

            "2017年03月31日 06时30分",
            "2017年03月31日 06时30分59秒",

    };

    @Test
    public void test() {
        for (String dateTime : dateTimes) {
            boolean b = DateTimeUtil.matchDate(dateTime);
            Assert.assertTrue(b);
        }
    }

    @Test
    public void testSpe() {
        /*boolean b = DateTimeUtil.matchDate("2017-02-17");
        Assert.assertTrue(b);*/

        String date = DateTimeUtil.findDate("[日期：2017-02-17]");
        logger.info("---------------------------Find result:{}", date);
    }

    @Test
    public void testSpe2() {
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher("[aaa2223bb]");
        logger.info(m.find());
        /*logger.info(m.start());
        logger.info(m.end());*/
        logger.info(m.group());
/*        m.find();//匹配2223
        m.start();//返回3
        m.end();//返回7,返回的是2223后的索引号
        m.group();//返回2223*/
    }

    @Test
    public void testBaidu() throws IOException {

        Connection connect = Jsoup.connect("https://www.baidu.com/");
        connect.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");

        Connection.Response execute = connect.execute();
        Map<String, String> cookies = execute.cookies();
        Map<String, String> headers = execute.headers();

        Connection connect1 = Jsoup.connect("https://www.baidu.com/s?ie=utf-8&mod=1&isbd=1&isid=FB107F7241711249&ie=utf-8&f=8&rsv_bp=0&rsv_idx=1&tn=baidu&wd=%E6%89%8B%E6%9C%BA%E7%99%BE%E5%BA%A6&rsv_pq=e2d02e4d0005de57&rsv_t=0941frNZV57%2BUXoiOrPPoaGWWrGM2tKf%2FZCQGcKWoJQSusNPxrloCEMyRCI&rqlang=cn&rsv_enter=1&rsv_sug3=3&rsv_n=2&rsv_sid=22584_1443_21095_18559_17001_22174_20928&_ss=1&clist=&hsug=&csor=4&pstg=2&_cr1=26691");
        connect1.cookies(cookies);
        Document document = connect1.get();
        logger.info(document.html());
    }

   /* @Test
    public void testFirstTimeInMonth(){
        Date date = new Date();
        Calendar calendar = DateTimeUtil.firstMillisInMonth(date.getMonth());
        System.out.println(calendar.getTime());
        System.out.println(new Date(calendar.getTimeInMillis() - 1));
    }

    @Test
    public void testLastTimeInMonth(){
        Date date = new Date();
        Calendar calendar = DateTimeUtil.lastMillisInMonth(date.getMonth());
        System.out.println(calendar.getTime());
        System.out.println(new Date(calendar.getTimeInMillis() + 1));
    }*/

}