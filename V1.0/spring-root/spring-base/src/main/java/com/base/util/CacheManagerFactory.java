package com.base.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年9月4日 @此类的作用：
 */
@PropertySource("classpath:/META-INF/system.properties")
public class CacheManagerFactory {
	private static final Logger logger = LoggerFactory.getLogger(CacheManagerFactory.class);
	public Map<String, Cache> cacheMap = new HashMap<String, Cache>();
	@Value("sunxl.chcacheManager.name")
	private static String managerNames;

	public CacheManagerFactory() {
		if (managerNames == null || "".equals(managerNames))
			managerNames = "oneEhcache|twoEhcache";
		String[] names = managerNames.split("\\|");
		CacheManager cacheManager = null;
		for (int i = 0; i < names.length; i++) {
			cacheManager = CacheManager.getCacheManager(names[i]);
			cacheMap.put(names[i], cacheManager.getCache(names[i]));
		}
	}

	private static class InnereCacheManagerFactory {
		private static final CacheManagerFactory INSTANCE = new CacheManagerFactory();
	}

	public static CacheManagerFactory getInstance() {
		return InnereCacheManagerFactory.INSTANCE;
	}

	public Map<String, Cache> getCacheMap() {
		return cacheMap;
	}

	public Cache getCache(String cacheName) {
		return getCacheMap().get(cacheName);
	}

	public void read(String cacheName, String key) {
		logger.warn(getInstance().getCache(cacheName).getName());
		Cache cache = getInstance().getCache(cacheName);
		Element element = cache.get(key);
		logger.warn(element.toString());
	}

	public void remove(String cacheName, String key) {
		logger.warn(getInstance().getCache(cacheName).getName());
		getInstance().getCache(cacheName).remove(key);
	}

	public void update(String cacheName, String key, Object obj) {
		logger.warn(getInstance().getCache(cacheName).getName());
		getInstance().getCache(cacheName).put(new Element(key, obj));
	}
}
