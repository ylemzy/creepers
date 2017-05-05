package application.controller;

import util.JsonHelper;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huangzebin on 2017/3/6.
 */
public class PageViewControllerTest {
    @Test
    public void getContent() throws Exception {
        Connection connect = Jsoup.connect("http://data.htbaobao.com/uc-controller/getnewsdetail.do");
        Connection.Response execute = connect.method(Connection.Method.POST)
                .data("newsId", "14702758170050858")
                .cookie("Hm_lvt_a87d01096a0d34762a5767f8920b9647", "1486535929,1487815383")
                .execute();
        HashMap hashMap = JsonHelper.toObject(execute.body(), HashMap.class);
        Map model = (Map)hashMap.get("model");
        System.out.print(model.get("content"));
    }

}