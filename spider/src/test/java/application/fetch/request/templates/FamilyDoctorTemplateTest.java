package application.fetch.request.templates;

import application.fetch.request.TemplateTester;
import org.junit.Test;

/**
 * Created by huangzebin on 2017/3/16.
 */
public class FamilyDoctorTemplateTest {

    TemplateTester templateTester = new TemplateTester(new FamilyDoctorTemplate().webId());

    @Test
    public void category() throws Exception {

    }

    @Test
    public void itemRequest() throws Exception {
        //templateTester.testItem("http://xnxg.familydoctor.com.cn/a/201611/1453737.html");
        //templateTester.testItem("http://xnxg.familydoctor.com.cn/a/201703/1759943.html");
        templateTester.testItem("http://cancer.familydoctor.com.cn/a/201703/1758482.html");
    }

    @Test
    public void pageRequest() throws Exception {
        templateTester.testFetch();
    }

}