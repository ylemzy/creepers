package application.fetch.request;

import application.elastic.document.News;
import application.fetch.request.filter.RequestFilter;
import application.fetch.request.filter.RequestFilterManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;


public abstract class AbstractTemplate implements Template {

	final int tryTimes = 3;

	protected int fetchNewsLimit = 128;

	private RequestFilter requestFilter;

	private static final Logger logger = LogManager.getLogger();

	public abstract Category category(Category root) throws Exception;

	protected abstract News itemRequest(Request request) throws Exception;

	protected abstract boolean pageRequest(Request request) throws Exception;

	public Category category() throws Exception {
		Category root = new Category("root", rootUrl(), webId());
		category(root);
		return root;
	}

	@Override
	public Response page(Request request) throws Exception {
		if (!RequestHelper.isPage(request)){
			throw new Exception("Not an page request");
		}
		return process(request);
	}

	@Override
	public Response item(Request request) throws Exception {
		if (!RequestHelper.isNews(request)){
			throw new Exception("Not an news request");
		}
		Response process = process(request);
		News item = (News)process.getBody();
		ItemValidate.valid(item);
		return process;
	}

	@Override
	public void start(Category category) throws Exception{
		if (category.hasChildren()) {
			for (Category c : category.getChildren()) {
				start(c);
			}
		} else {
			if (category.isFetchable()) {
				addRequest(RequestHelper.firstPageRequest(category.getLink(), category.getName(), webId()));
			}
		}
	}

	@Override
	public Category findCategoryFrom(Category category, String url) throws Exception{
		if (category.getLink().equalsIgnoreCase(url))
			return category;

		for (Category c : category.getChildren()) {
			Category found = findCategoryFrom(c, url);
			if (found != null)
				return found;
		}
		return null;
	}

	@Override
	public void start() throws Exception{
		logger.info("Start {}", this.webId());
		start(category());
	}

	protected void addRequest(Request request) {
		if (requestFilter == null){
			requestFilter = RequestFilterManager.getDefaultFilter();
		}
		requestFilter.filter(request);
	}

	@Override
	public Response process(Request request) throws Exception {
		Response response = new Response();
		Request.Type type = request.getType();
		logger.debug("process:[{}]-{}", type, request.getURL());

		switch (type) {
		case PAGE:
			response.setBody(this.pageRequest(request));
			break;
		case ITEM:
			response.setBody(this.itemRequest(request));
			break;
		default:
		}

		return response;
	}


	protected Document get(String url) throws Exception{
		return get(url, tryTimes);
	}

	protected Document get(String url, int trys) throws Exception {
		Connection connection = Jsoup.connect(url);
		try {
			return connection.get();
		} catch (IOException e) {
			logger.error(e.getMessage());
			if (ensureUnavailable(e)) {
				throw e;
			}
			if (--trys != 0) {
				return get(url, trys);
			}
			throw e;//throw the last try error
		}
	}

	private boolean ensureUnavailable(Exception e){
		HttpStatusException httpStatusException  = null;
		if (e.getCause() instanceof HttpStatusException) {
			httpStatusException = (HttpStatusException) e.getCause();

		}else if (e instanceof HttpStatusException){
			httpStatusException = (HttpStatusException)e;
		}

		if (httpStatusException != null){
			int code = httpStatusException.getStatusCode();
			switch (code){
				case 404:
				case 410:
				case 500:
				case 501:
					return true;
			}
		}
		return false;
	}

	protected int getFetchNewsLimit() {
		return fetchNewsLimit;
	}

	protected void setFetchNewsLimit(int fetchNewsLimit) {
		this.fetchNewsLimit = fetchNewsLimit;
	}
}
