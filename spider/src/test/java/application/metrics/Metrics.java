package application.metrics;/**
 * Created by huangzebin on 2017/5/17.
 */

import com.codahale.metrics.MetricRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Metrics {
    private static final Logger logger = LogManager.getLogger();

    private static final MetricRegistry metrics = new MetricRegistry();

    public static MetricRegistry getMetrics(){
        return metrics;
    }


}
