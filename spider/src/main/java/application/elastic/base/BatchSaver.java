package application.elastic.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by huangzebin on 2017/2/20.
 */
public abstract class BatchSaver<R extends CustomElasticsearchRepository, T> implements Saver<R, T> {
    private static final Logger logger = LogManager.getLogger();

    List<T> list = new ArrayList<>();
    int batchSize = 10;
    int savedCount = 0;

    @Autowired
    private R customElasticsearchRepository;

/*    public BatchSaver(){

    }*/

  /*  public BatchSaver(R customElasticsearchRepository, int batchSize){
        this.batchSize = batchSize;
        this.customElasticsearchRepository = customElasticsearchRepository;
    }*/

    @Override
    public void save(T data){
        list.add(data);

        if (list.size() >= batchSize){
            saveAndClear();
        }
    }

    @Override
    public void finish(){
        if (list.size() > 0){
            saveAndClear();
        }
        logger.info("Saved count = {}", savedCount);
    }

    private void saveAndClear(){
        customElasticsearchRepository.update(list);
        savedCount += list.size();
        list.clear();
    }

    //@Async
    public void run(){
        while (true){
            try {
                Thread.sleep(TimeUnit.SECONDS.toMillis(2));
                finish();
            } catch (InterruptedException e) {
                logger.error(e, e);
            }
        }
    }
}
