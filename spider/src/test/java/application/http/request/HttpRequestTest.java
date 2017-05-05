package application.http.request;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by huangzebin on 2017/3/2.
 */
public class HttpRequestTest {

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
    public void testBaidu() throws IOException {
        Document document = Jsoup.connect("http://www.baidu.com/s?ie=utf-8&mod=1&isbd=1&isid=dfd2c4fd0004fd94&ie=utf-8&f=8&rsv_bp=1&rsv_idx=1&tn=baidu&wd=%E7%99%BE%E5%BA%A6&oq=%25E7%2599%25BE%25E5%25BA%25A6&rsv_pq=dfd2c4fd0004fd94&rsv_t=1d07cq6VyejfDiRT3uvkBb2swOTLCQcq1n5d%2FeLlTqT5h1I58QRanVF8TsY&rqlang=cn&rsv_enter=0&bs=%E7%99%BE%E5%BA%A6&rsv_sid=22584_1443_21095_18559_17001_22174_20928&_ss=1&clist=97fa6eda6c430ef9&hsug=&f4s=1&csor=2&_cr1=28622")
                .ignoreContentType(true).get();
        System.out.println(document.html());
    }



}