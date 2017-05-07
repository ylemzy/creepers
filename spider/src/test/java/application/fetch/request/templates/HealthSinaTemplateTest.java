package application.fetch.request.templates;

import application.fetch.request.TemplateTester;
import org.junit.Test;

/**
 * Created by huangzebin on 2017/3/7.
 */
public class HealthSinaTemplateTest {

    TemplateTester templateTester = new TemplateTester("HealthSina");

    @Test
    public void testCategory(){
        templateTester.testCategory();
    }

    @Test
    public void testPage(){
        templateTester.testPage("http://feed.mix.sina.com.cn/api/roll/get?lid=561&num=1000&page=1&pageid=39");
    }

    @Test
    public void testItem(){
        //templateTester.testItem("http://health.sina.com.cn/d/2017-03-06/doc-ifycaafm5220483.shtml");
        //templateTester.testItem("http://health.sina.com.cn/hc/2017-03-07/doc-ifycaafm5444386.shtml");
        templateTester.testItem("http://slide.health.sina.com.cn/d/slide_31_28379_109929.html");
        //templateTester.testItem("http://health.sina.com.cn/hc/2017-03-03/doc-ifyazwha3595247.shtml");
    }

    @Test
    public void testFetch(){
        templateTester.testFetch();
    }
}