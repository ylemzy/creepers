package application.http.options;

public class HttpProxy {

/*	private static final String PROXIES_PATH = "proxies.txt";
	private static List<ProxyServer> PROXIES = new ArrayList<>();
	private static Random random = new Random();

	static {
		List<String> lines = null;
		try (InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(PROXIES_PATH)) {
			lines = IOUtils.readLines(resourceAsStream);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		for (String line : lines) {
			String[] pair = line.split(":");
			PROXIES.add(new ProxyServer(pair[0], Integer.valueOf(pair[1])));
		}
	}

	public static List<ProxyServer> getProxys(){
		return PROXIES;
	}

	public static Proxy getProxy() {
		if ( PROXIES.isEmpty()) {
			throw new RuntimeException("no config proxy");
		}
		int index = random.nextInt(PROXIES.size());
		ProxyServer proxyServer = PROXIES.get(index);
		return new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyServer.ip, Integer.valueOf(proxyServer.port)));
	}

	public static Proxy getProxy(ProxyServer server){
		return new Proxy(Proxy.Type.HTTP, new InetSocketAddress(server.ip, Integer.valueOf(server.port)));
	}

	public static class ProxyServer {
		String ip;
		int port;

		public ProxyServer(String ip, int port) {
			super();
			this.ip = ip;
			this.port = port;
		}

		public String getIp() {
			return ip;
		}

		public void setIp(String ip) {
			this.ip = ip;
		}

		public int getPort() {
			return port;
		}

		public void setPort(int port) {
			this.port = port;
		}
	}


	public static class LuminatiProxy implements AuthorizationProxy {
		public static final String luminatiUsername = "lum-customer-byrsp-zone-static";
		public static final String luminatiPassword = "ec7f5e2730d6";
		public static final String luminatiProxyHost = "zproxy.luminati.io";
		public static final int luminatiPort = 22225;
		public String session_id = Integer.toString(new Random().nextInt(Integer.MAX_VALUE));
		public Proxy proxy;


		String country;

		static LuminatiProxy luminatiProxy = new LuminatiProxy("us");

		public static LuminatiProxy getInstance(){
			return luminatiProxy;
		}

		public LuminatiProxy(String country) {
			this.country = country;
			updateProxy();
		}

		public void updateProxy(){
			session_id = Integer.toString(new Random().nextInt(Integer.MAX_VALUE));
			try {
				int proxy_session_id = new Random().nextInt(Integer.MAX_VALUE);
				InetAddress address = InetAddress.getByName("session-" + proxy_session_id + ".zproxy.luminati.io");
				String host = address.getHostAddress();
				proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(host, luminatiPort));
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}

		@Override
		public Proxy getProxy() {
			return proxy;
		}

		@Override
		public String getName() {
			String res = luminatiUsername + (country != null ? "-country-" + country : "") + "-session-" + session_id;
			return res;
		}

		@Override
		public String getPassword() {
			return this.luminatiPassword;
		}
	}*/
}
