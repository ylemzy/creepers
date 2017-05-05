package application.fetch.templates;

import application.fetch.Template;
import application.fetch.TemplateLoader;
import http.UrlMaker;
import util.HashUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.springframework.util.Assert;

import java.net.MalformedURLException;
import java.util.*;

/**
 * Created by huangzebin on 2017/3/3.
 */
public class RequestHelperTest {

    private static final Logger logger = LogManager.getLogger();

    @Test
    public void testLoad(){
        Template healthQQ = TemplateLoader.getTemplate("HealthQQ");
    }

    @Test
    public void testUrl() throws MalformedURLException {
        //String url = "http://feed.mix.sina.com.cn/api/roll/get?pageid=39&lid=561&num=20&versionNumber=1.2.8&page=1&encode=utf-8&callback=feedCardJsonpCallback&_=1488792450494";

        String url = "http://www.baidu.com?p1=123";
        UrlMaker urlMaker = new UrlMaker(url).param("p1", "222");
        System.out.println(urlMaker.getUrl());
    }

    @Test
    public void testhash2(){
        logger.info(">>" + HashUtil.MD5("https://jingyan.baidu.com/article/ff41162582507512e5823763.html").hashCode());
    }

    @Test
    public void testHash(){

        for (int i = 0; i < 10; ++i){
            hashLoop(i);
        }

    }

    public void hashLoop(int th){

        List<Integer> res = new ArrayList<>();
        long l = System.currentTimeMillis();
        for (int i = 0; i < 10000; ++i){
            String strText = "" + (++l);
            //logger.info("Test={}", strText);
            res.add(HashUtil.MD5(strText).hashCode());
        }

        Set<Integer> set = new HashSet<>();
        res.forEach(i -> set.add(i));
        logger.info("Try th:{} A={}, B={}", th, res.size(), set.size());
        Assert.isTrue(res.size() == set.size());

    }
}