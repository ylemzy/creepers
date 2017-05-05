package application.schedule;

import application.service.NewsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by huangzebin on 2017/3/6.
 */
@Component
public class FetchSchedule {
    private static final Logger logger = LogManager.getLogger();

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");


    @Value("${jobs.fetch.schedule.enabled}")
    boolean scheduleEnabled;

    @Value("#{'${jobs.fetch.schedule.list}'.split(',')}")
    List<String> fetchList;

    @Autowired
    NewsService newsService;

    @Scheduled(cron = "${jobs.fetch.schedule.cron}")
    public void fetch(){
        if (!scheduleEnabled)
            return;
        logger.info("FetchSchedule at {}", dateFormat.format(new Date()));

        newsService.start(fetchList);
    }
}
