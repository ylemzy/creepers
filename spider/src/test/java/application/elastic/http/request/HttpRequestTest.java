package application.elastic.http.request;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by huangzebin on 2017/3/2.
 */
public class HttpRequestTest {
    private static final Logger logger = LogManager.getLogger();

    @Test
    public void test() throws IOException {
        Document document = Jsoup.connect("https://wenda.toutiao.com/wenda/web/question/association/?title=%E4%BF%9D%E9%99%A9%20%E5%AD%A9%E5%AD%90")
                .ignoreContentType(true).get();
        System.out.println(document.html());
    }

   @Test
    public void testDouban() throws IOException {
        Document document = Jsoup.connect("https://www.douban.com/search?q=%E6%B3%B0%E5%9D%A6%E5%B0%BC%E5%85%8B%E5%8F%B7")
                .ignoreContentType(true).get();
        System.out.println(document.html());
    }


   @Test
    public void testBaidu() {
       Connection connect = Jsoup.connect("http://www.fx678.com/")
               //.header("Host", "news.hexun.com")
               .header("User-Agent", "Mozilla")
               //.header("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
               ;
       System.out.println("" +  connect.request().headers().toString());
       System.out.println("" +  connect.response().headers().toString());

       Document document = null;
       try {
           document = connect.timeout(10000)
                    .get();
       } catch (IOException e) {
           logger.error(e, e);
       }
       System.out.println(document.html());
    }

   @Test
    public void testToutiao() throws IOException {
        Document document = Jsoup.connect("http://www.toutiao.com/").get();
        System.out.println(document.html());
    }





}