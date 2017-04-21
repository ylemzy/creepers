package application.fetch.templates;

import application.fetch.TemplateTester;
import org.junit.Test;

/**
 * Created by huangzebin on 2017/4/17.
 */
public class DavinciTemplateTest {
    TemplateTester templateTester = new TemplateTester(new DavinciTemplate().webId());

    @Test
    public void itemRequest() throws Exception {

/*        templateTester.testItem("http://www.thebigdata.cn/YeJieDongTai/32398.html");
        templateTester.testItem("http://www.36dsj.com/archives/78778");
        templateTester.testItem("http://health.qq.com/a/20170320/033221.htm");
        templateTester.testItem("http://health.sina.com.cn/hc/2017-03-07/doc-ifycaafm5444386.shtml");
        templateTester.testItem("http://news.ifeng.com/a/20170417/50952248_0.shtml");
        templateTester.testItem("http://www.ccdi.gov.cn/xwtt/201704/t20170417_97380.html");
        templateTester.testItem("http://news.haiwainet.cn/n/2017/0417/c3541093-30864233.html");*/
        //templateTester.testItem("http://www.toutiao.com/a6386503828514734337/");
        //templateTester.testItem("http://m.huanqiu.com/r/MV8wXzEwMTcwNzY2XzEyNjJfMTQ4NzYzOTU4MA==?tt_group_id=6389362877111959810");
        templateTester.testItem("http://www.toutiao.com/a6410303068849078529/");
    }

    @Test
    public void testFetch(){
        templateTester.testFetch();
    }


}