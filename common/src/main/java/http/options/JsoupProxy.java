package http.options;

import java.util.concurrent.atomic.AtomicLong;

public class JsoupProxy {

/*	private static ConfigableProxyFactory configableProxyFactory;

	public static Authenticator proxyAuthenticator = new Authenticator() {
		@Override
		public Request authenticate(Route route, Response response) throws IOException {
			String username = Settings.getString("crawler.proxy.username", null);
			String password = Settings.getString("crawler.proxy.password", null);
			String credential = Credentials.basic(username, password);
			return response.request().newBuilder().header("Proxy-Authorization", credential).build();
		}
	};

	public static class ConfigableAuthenticator implements Authenticator{
		String username;
		String password;
		public ConfigableAuthenticator(String username, String password){
			this.username = username;
			this.password = password;
		}

		@Override
		public Request authenticate(Route route, Response response) throws IOException {
			String credential = Credentials.basic(username, password);
			return response.request().newBuilder().header("Proxy-Authorization", credential).build();
		}
	}

	public static Authenticator buildAuthenticator(final String username, final String password){
		return new ConfigableAuthenticator(username, password);
	}

	public static Authenticator buildAuthenticator(final AuthorizationProxy authorizationProxy){
		Assert.notNull(authorizationProxy);
		return new ConfigableAuthenticator(authorizationProxy.getName(), authorizationProxy.getPassword());
	}
	
	public static AuthorizationProxy getAuthorizationProxy(String retailerId) {
		if (configableProxyFactory == null) {
			configableProxyFactory = (Crawler.RUNNING.type() == Crawler.Type.ONLINE
					? (ConfigableProxyFactory.getInstance(ProxySettingsService.Type.online_proxy))
					: (ConfigableProxyFactory.getInstance(ProxySettingsService.Type.offline_proxy)));
		}

		return configableProxyFactory.getProxy(retailerId);
	}

	public static AuthorizationProxy getImageAuthorizationProxy(String retailerId){
		if (configableProxyFactory == null) {
			configableProxyFactory = (Crawler.RUNNING.type() == Crawler.Type.ONLINE
					? (ConfigableProxyFactory.getInstance(ProxySettingsService.Type.online_proxy))
					: (ConfigableProxyFactory.getInstance(ProxySettingsService.Type.offline_proxy)));
		}
		if (configableProxyFactory.isImageUseProxy(retailerId)){
			return configableProxyFactory.getProxy(retailerId);
		}
		return null;
	}

	public static RequestHttpProxy getAuthorizationProxy() {
		return RequestHttpProxy.getInstance();
	}

	public static Authenticator getProxyAuthenticator() {
		return proxyAuthenticator;
	}

	public static class RequestHttpProxy implements AuthorizationProxy {

		private Proxy proxy;

		public RequestHttpProxy() {
			this.proxy = HttpProxy.getProxy();
		}

		public RequestHttpProxy(String host, int port) {
			this.proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(host, Integer.valueOf(port)));
		}

		public static RequestHttpProxy getInstance() {
			RequestHttpProxy requestHttpProxy = new RequestHttpProxy();
			return requestHttpProxy;
		}

		@Override
		public Proxy getProxy() {
			return proxy;
		}

		@Override
		public String getName() {
			return null;
		}

		@Override
		public String getPassword() {
			return null;
		}

	}

	public static class LuminatiProxy implements AuthorizationProxy {

		private Proxy proxy;
		private String host;
		private int port;
		private String username;
		private String password;

		private static LuminatiProxy luminatiProxy = new LuminatiProxy();

		public static LuminatiProxy getInstance() {
			return luminatiProxy;
		}

		public LuminatiProxy() {
			updateProxy();
		}

		public void updateProxy() {
			host = Settings.getString("crawler.proxy.host", "10.30.0.88");
			port = Settings.getInt("crawler.proxy.port", 23000);
			username = Settings.getString("crawler.proxy.username", null);
			password = Settings.getString("crawler.proxy.password", null);
			proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(host, port));
		}

		@Override
		public Proxy getProxy() {
			return proxy;
		}

		@Override
		public String getName() {
			return username;
		}

		@Override
		public String getPassword() {
			return password;
		}
	}*/

	/*
	 * static class JsoupListener implements Listener { State state = new
	 * State();
	 * 
	 * @Override public void beforeRequest(org.jsoup.Connection.Request req) {
	 * long start = System.currentTimeMillis(); req.attribute("start", start);
	 * req.proxy(RequestHttpProxy.createInstance()); }
	 * 
	 * @Override public void afterResponse(org.jsoup.Connection.Request req,
	 * org.jsoup.Connection.Response res) { long start = req.attribute("start");
	 * state.total.incrementAndGet(); long end = System.currentTimeMillis();
	 * state.max.set((end - start) > state.max.get() ? (end - start) :
	 * state.max.get()); state.min.set((end - start) < state.min.get() ? (end -
	 * start) : state.min.get()); state.time.set(state.time.get() + end -
	 * start); state.report(); } }
	 */

	public static class State {
		public AtomicLong total = new AtomicLong();
		public AtomicLong time = new AtomicLong(0);
		public AtomicLong min = new AtomicLong(99999999);
		public AtomicLong max = new AtomicLong(0);
		public AtomicLong errors = new AtomicLong(0);

		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("total:").append(total.get()).append(", ");
			sb.append("total time:").append(time.get()).append("ms, ");
			sb.append("max time:").append(max.get()).append("ms, ");
			sb.append("min time:").append(min).append("ms, ");
			sb.append("avg=").append((time.get() / total.get())).append("ms, ");
			sb.append("errors:").append(errors.get());
			return sb.toString();
		}

		public void report() {
			System.out.println(this);
		}
	}

}
