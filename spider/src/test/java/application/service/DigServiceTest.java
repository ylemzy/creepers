package application.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/**
 * Created by huangzebin on 2017/5/26.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class DigServiceTest {
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    DigService digService;

    @Test
    public void fetchCategory() throws Exception {
        digService.fetchCategory();

        Thread.sleep(TimeUnit.HOURS.toMillis(1));
    }
}