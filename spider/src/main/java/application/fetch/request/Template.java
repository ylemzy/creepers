package application.fetch.request;

public interface Template {

	Category category() throws Exception;

	Response page(Request request) throws Exception;

	Response item(Request request) throws Exception;

	Response process(Request request) throws Exception;

	void start(Category category) throws Exception;

	void start() throws Exception;

	Category findCategoryFrom(Category category, String url) throws Exception;

	String webId();

	String rootUrl();
}
