package application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by huangzebin on 2017/2/22.
 */

@Component
public class AppProperties {

    @Value("${batch.size}")
    public int esBatchSize;
}
