package application.elastic.http;

import http.UrlMaker;
import org.junit.Test;

import java.net.MalformedURLException;

/**
 * Created by huangzebin on 2017/4/21.
 */
public class LinkMakerTest {

    @Test
    public void test() throws MalformedURLException {
        UrlMaker urlMaker = UrlMaker.make("http://www.toutiao.com/");
        System.out.println(urlMaker.getRowUrl());

        UrlMaker urlMaker2 = UrlMaker.make("http://www.toutiao.com/index?id=1&h=2");
        System.out.println(urlMaker2.getRowUrl());
    }
}