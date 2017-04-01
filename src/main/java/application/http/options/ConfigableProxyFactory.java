package application.http.options;



public class ConfigableProxyFactory {
	
	/*private static final Logger LOGGER = LoggerFactory.getLogger(ConfigableProxyFactory.class);

	private static ConfigableProxyFactory _this;

	private ProxySettingsService proxyService;

	private Map<String, ProxyBean> configuredProxyMap;

	private boolean useProxy;
	
	private boolean imageUseProxy;
	
	private static final Random RANDOM = new Random();
	
	private ConfigableProxyFactory(ProxySettingsService.Type type, boolean schedSyncSetting) {
		useProxy = Strings.isTrue(String.valueOf(AuthSupportedProxyLoader.PROP.get(type.name())));
		imageUseProxy = Strings.isTrue(String.valueOf(AuthSupportedProxyLoader.PROP.get(type.name() + "_image")));
		proxyService = new ProxySettingsService(type);
		configuredProxyMap = proxyService.getProxySettings();
		LOGGER.info(configuredProxyMap + " loaded.");
		if(schedSyncSetting) {
			schedLoadProxySettings(TimeUnit.MINUTES.toMillis(5L));
		}
	}
	
	*//**
	 * Sync data from settings. It is a scheduled job to keep data latest in a certain time
	 *//*
	private synchronized void schedLoadProxySettings(Long interval) {
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				configuredProxyMap = proxyService.getProxySettings();
				LOGGER.info(configuredProxyMap + " loaded.");
			}
		};
		Timer timer = new Timer(true);
		timer.schedule(task, interval, interval);
	}
	
	public static ConfigableProxyFactory getInstance(ProxySettingsService.Type type) {
		return getInstance(type, true);
	}
	
	public static ConfigableProxyFactory getInstance(ProxySettingsService.Type type, boolean schedSyncSetting) {
		if (_this == null) {
			synchronized (ConfigableProxyFactory.class) {
				if(_this == null) {
					_this = new ConfigableProxyFactory(type, schedSyncSetting);
				}
			}
		}
		return _this;
	}
	
	public boolean isImageUseProxy(String retailerId) {
		if (configuredProxyMap.containsKey(retailerId)) {
			ProxyBean proxyBean = configuredProxyMap.get(retailerId);
			return proxyBean.isImageEnabled();
		}
		return imageUseProxy;
	}

	public AuthorizationProxy getProxy(String retailerId) {
		if (configuredProxyMap.containsKey(retailerId)) {
			ProxyBean proxyBean = configuredProxyMap.get(retailerId);
			if (proxyBean.isEnabled()) {
				// use proxy
				return randomlyPickProxy(proxyBean);
			}
		} else {
			if (useProxy) {
				// use proxy
				return randomlyPickProxy();
			}
		}
		return null; // all do not use proxy case
	}

	private AuthorizationProxy randomlyPickProxy() {
		return randomlyPickProxy(null);
	}
	
	private AuthorizationProxy randomlyPickProxy(ProxyBean bean) {
		List<String> proxyList = AuthSupportedProxyLoader.getProxies();
		if (bean == null) {
			return toAuthorizationProxy(proxyList.get(RANDOM.nextInt(proxyList.size())));
		} else {
			List<String> tmpProxyList = new ArrayList(proxyList);
			// remove invalid proxies
			if (bean.getInvalid() != null && !bean.getInvalid().isEmpty()) {
				for (String invalid : bean.getInvalid()) {
					tmpProxyList.remove(invalid);
				}
			}
			List<String> targetProxyList = new ArrayList();
			// collect valid proxies
			if (bean.getValid() != null && !bean.getValid().isEmpty()) {
				for (String valid : bean.getValid()) {
					if (tmpProxyList.contains(valid)) {
						targetProxyList.add(valid);
					}
				}
			}
			// if no available proxy, it will get from the original list
			if (targetProxyList.isEmpty()) {
				targetProxyList = tmpProxyList;
			}
			return toAuthorizationProxy(targetProxyList.get(RANDOM.nextInt(targetProxyList.size())));
		}
	}

	private AuthorizationProxy toAuthorizationProxy(String line) {
		final String[] element = line.split(":");
		String host = (element.length > 0 ? element[0] : null);
		String port = (element.length > 1 ? element[1] : null);
		String name = (element.length > 2 ? element[2] : null);
		String password = (element.length > 3 ? element[3] : null);
		return new ConfiguableHttpAuthProxy(host, port, name, password);
	}

	class ConfiguableHttpAuthProxy implements AuthorizationProxy {
		private String name, password;
		
		private Proxy proxy;

		public ConfiguableHttpAuthProxy(String host, String port, String name, String password) {
			super();
			this.proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(host, Integer.valueOf(port)));
			this.name = name;
			this.password = password;
		}

		@Override
		public Proxy getProxy() {
			return proxy;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public String getPassword() {
			return password;
		}
	}*/

}
