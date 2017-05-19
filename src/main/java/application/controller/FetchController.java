package application.controller;

import application.service.NewsService;
import application.uil.JsonHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by huangzebin on 2017/3/3.
 */
@RestController
public class FetchController {

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    NewsService newsService;

    @RequestMapping(value = "/start", method = RequestMethod.GET)
    public ResponseEntity start(@RequestParam String id){
        logger.info("start id : {}", id);
        try{
            List<String> ids = new ArrayList<>();
            ids.add(id);
            newsService.start(ids);
            return ResponseEntity.ok("Start fetch id");
        }catch (Exception e){
            logger.error(e, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public ResponseEntity test(){
        logger.info("test performance");
        try{
            return ResponseEntity.ok("Start test performance");
        }catch (Exception e){
            logger.error(e, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
