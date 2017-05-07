package application.service;

import application.fetch.url.UrlDigger;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by J on 5/7/2017.
 */
@Service
public class DigService {

    public void dig(String url){
        new UrlDigger(url).dig();
    }
}
