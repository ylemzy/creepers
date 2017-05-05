package application.fetch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by huangzebin on 2017/3/3.
 */
public class TemplateLoader {
    private static final Logger logger = LogManager.getLogger();

    private Impl impl = new Impl();

    private TemplateLoader() {
    }

    static TemplateLoader LOADER = new TemplateLoader();

    public static Template getTemplate(String name) {
        return LOADER.loadTemplate(name);
    }

    Template loadTemplate(String name) {
        return impl.getTemplate(name);
    }

    class Impl {

        Map<String, Template> templates = new ConcurrentHashMap<>();

        Template getTemplate(String name) {
            if (name == null) {
                throw new RuntimeException("template name is required");
            }

            Template template = templates.get(name);
            if (template != null) {
                return template;
            }

            synchronized (Impl.class) {
                if (template == null) {
                    template = newObject(name);
                    templates.put(name, template);
                }
            }

            return template;
        }

        Template newObject(String name) {
            String tplName = "application.fetch.templates." + name + "Template";
            Class<?> forName;
            try {
                forName = Class.forName(tplName);
            } catch (ClassNotFoundException e) {
                logger.error("can't load template:{}", tplName);
                return null;
            }
            try {
                Template template = (Template) forName.newInstance();
                logger.info("new {}Template", name);
                return template;
            } catch (InstantiationException | IllegalAccessException e) {
                logger.error("can't new template:{}", tplName);
            } catch (Throwable e){
                logger.error("can't new template:{}", tplName);
            }
            return null;
        }
    }
}
