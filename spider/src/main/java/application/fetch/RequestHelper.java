package application.fetch;

import util.HashUtil;

public class RequestHelper {

	public static Request firstPageRequest(String url, String categoryName, String webId){
		return new Request(url, Request.Type.PAGE,
				CATEGORY_ATT, categoryName,
				FIRST_PAGE_ATT, true)
				.setWebId(webId);
	}

	public static Request itemRequest(String url, String categoryName, String webId){
		return new Request(url, Request.Type.ITEM,
				CATEGORY_ATT, categoryName)
				.setWebId(webId);
	}

	public static Request itemRequest(String url, Request parent){
		return new Request(url, Request.Type.ITEM,
				CATEGORY_ATT, getCategoryAtt(parent))
				.setWebId(parent.getWebId());
	}

	public static Request pageRequest(String url, String categoryName, String webId){
		return new Request(url, Request.Type.PAGE,
				CATEGORY_ATT, categoryName)
				.setWebId(webId);
	}

	public static Request pageRequest(String url, Request parent){
		return new Request(url, Request.Type.PAGE,
				CATEGORY_ATT, getCategoryAtt(parent))
				.setWebId(parent.getWebId());
	}

	public static String makeNewsId(String url, String webId){
		return HashUtil.MD5(url);
	}

	public static boolean isNews(Request request){
		return request.getType() == Request.Type.ITEM;
	}

	public static boolean isPage(Request request){
		return request.getType() == Request.Type.PAGE;
	}

	public static boolean isFirstPage(Request request){
		return request.attr(FIRST_PAGE_ATT) != null;
	}

	public static String getCategoryAtt(Request request){
		return request.attr(CATEGORY_ATT);
	}

	private static String CATEGORY_ATT = "category";
	private static String FIRST_PAGE_ATT = "fp";

}
