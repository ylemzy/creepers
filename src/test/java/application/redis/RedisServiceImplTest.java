package application.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by huangzebin on 2017/4/21.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisServiceImplTest {


    @Autowired
    RedisServiceImpl redisService;

    @Test
    public void set() throws Exception {
        redisService.set("htbaobao", "test_for");
    }

    @Test
    public void get() throws Exception {
        String htbaobao = redisService.get("htbaobao");
        System.out.println("-->> " + htbaobao);
    }

}