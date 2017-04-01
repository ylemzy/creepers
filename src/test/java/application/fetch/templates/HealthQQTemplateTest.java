package application.fetch.templates;

import application.fetch.*;
import application.fetch.filter.RequestFilterManager;
import application.uil.JsonHelper;
import org.junit.Test;

/**
 * Created by huangzebin on 2017/3/2.
 */
public class HealthQQTemplateTest {

    TemplateTester templateTester = new TemplateTester(new HealthQQTemplate().webId());

    @Test
    public void testCategory() throws Exception {
       templateTester.testCategory();
    }

    @Test
    public void testPage() throws Exception {
        templateTester.testPage("http://health.qq.com/c/jibingkepu_2.htm");
    }

    @Test
    public void testItem() throws Exception{
        //templateTester.testItem("http://health.qq.com/a/20170315/017520.htm");
        //templateTester.testItem("http://health.qq.com/a/20170320/033221.htm");
        //templateTester.testItem("http://health.qq.com/a/20170301/025151.htm");
        templateTester.testItem("http://health.qq.com/a/20170213/017979.htm");
    }


    @Test
    public void testFetch() throws Exception {
        templateTester.testFetch();
    }
}