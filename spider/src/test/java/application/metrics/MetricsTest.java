package application.metrics;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * Created by huangzebin on 2017/5/17.
 */
/*
@RunWith(SpringRunner.class)
@SpringBootTest*/
public class MetricsTest {
    private static final Logger logger = LogManager.getLogger();

    @Test
    public void testMeters() throws InterruptedException {



        MetricRegistry metrics = Metrics.getMetrics();


        ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
        reporter.start(1, TimeUnit.SECONDS);

        //Meter requests = metrics.meter(name(TestMeters.class, "request"));

/*        Slf4jReporter reporter2 = Slf4jReporter.forRegistry(metrics)
                .outputTo(LoggerFactory.getLogger("com.example.metrics"))
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
        reporter2.start(1, TimeUnit.MINUTES);*/

        Meter requests = metrics.meter("requests");
        for (int i = 0; i < 10; ++i){
            Thread.sleep(TimeUnit.SECONDS.toMillis(1));
            requests.mark();
        }

    }

}