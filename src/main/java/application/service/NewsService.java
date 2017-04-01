package application.service;

import application.fetch.Category;
import application.fetch.Template;
import application.fetch.TemplateLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by huangzebin on 2017/3/1.
 */
@Service
public class NewsService {
    private static final Logger logger = LogManager.getLogger();

    public Category fetchCategory(String webId) {
        Template template = TemplateLoader.getTemplate(webId);
        try {
            return template.category();
        } catch (Exception e) {
            logger.error(e, e);
        }
        return null;
    }

    public void fetchPage(Category category) {
        Template template = TemplateLoader.getTemplate(category.getWebId());
        try {
            template.start(category);
        } catch (Exception e) {
            logger.error(e, e);
        }
    }

    public void start(List<String> ids) {
        ids.forEach(id -> {
            try {
                Template template = TemplateLoader.getTemplate(id);
                template.start();
            } catch (Exception e) {
                logger.error(e, e);
            }
        });
    }
}
