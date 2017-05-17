package application.elastic;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by huangzebin on 2017/5/11.
 */
public class IndexTargetTest {

    @Test
    public void timeIndex() throws Exception {
        String monthIndex = IndexTarget.timeIndex("test", System.currentTimeMillis(), IndexTarget.TargetType.MONTH);
        String dayIndex = IndexTarget.timeIndex("test", System.currentTimeMillis(), IndexTarget.TargetType.DAY);
        System.out.println(monthIndex + "," + dayIndex);
    }

}