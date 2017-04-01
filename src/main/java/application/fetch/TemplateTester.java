package application.fetch;

import application.fetch.filter.RequestFilterManager;
import application.uil.JsonHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by huangzebin on 2017/3/7.
 */
public class TemplateTester {

    private static final Logger logger = LogManager.getLogger();

    private String webId;

    private Template template;

    public TemplateTester(String webId){
        this.webId = webId;
        this.template = TemplateLoader.getTemplate(this.webId);
    }

    public void testCategory() {

        Category category = null;
        try {
            category = template.category();
        } catch (Exception e) {
            logger.error(e, e);
        }
        category.getChildren().forEach(c->{
            if (c.isFetchable())
                logger.info(JsonHelper.toJSON(c));
        });
    }

    public void testPage(String categoryUrl)  {
        RequestFilterManager.setDefaultFilter(RequestFilterManager.FiltetType.CONSOLE);

        Category category;
        try {
            category = template.findCategoryFrom(template.category(), categoryUrl);
        } catch (Exception e) {
            logger.error(e, e);
            return;
        }

        if (category == null){
            logger.warn("Can't find category url {}", categoryUrl);
        }

        Request page = RequestHelper.firstPageRequest(category.getLink(), category.getName(), this.webId);
        try {
            template.page(page);
        } catch (Exception e) {
            logger.error(e, e);
        }

        outputResult();
    }


    public void testItem(String url){
        Request item =  RequestHelper.itemRequest(url, "test_item", this.webId);
        Response process = null;
        try {
            process = template.item(item);
        } catch (Exception e) {
            logger.error(e, e);
            return;
        }
        logger.info(JsonHelper.toJSON(process.getBody()));
    }

    public void testFetch(){
        RequestFilterManager.setDefaultFilter(RequestFilterManager.FiltetType.QUEUE_AS_CONSOLE);
        try {
            template.start();
        } catch (Exception e) {
            logger.error(e, e);
        }
        outputResult();
    }

    public void outputResult(){
        logger.info("Filter result : " + JsonHelper.toJSON(RequestFilterManager.getDefaultFilter().result()));
    }
}
