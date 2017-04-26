package application.kafka;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Created by huangzebin on 2017/4/26.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class KafkaMessageTest {

    @Autowired
    Receiver receiver;

    @Autowired
    Sender sender;


    @Test
    public void test() throws InterruptedException {
        sender.sendMessage();
        Thread.sleep(TimeUnit.SECONDS.toMillis(5));

        //receiver.processMessage();
    }
}