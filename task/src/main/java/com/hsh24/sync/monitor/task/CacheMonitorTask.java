package com.hsh24.sync.monitor.task;

import com.hsh24.sync.api.cache.IMemcachedCacheService;
import com.hsh24.sync.api.monitor.ICacheMonitorService;
import com.hsh24.sync.framework.annotation.Profiler;
import com.hsh24.sync.framework.log.Logger4jCollection;
import com.hsh24.sync.framework.log.Logger4jExtend;

/**
 * 
 * @author xujiakun
 * 
 */
public class CacheMonitorTask {

	private Logger4jExtend logger = Logger4jCollection.getLogger(CacheMonitorTask.class);

	private IMemcachedCacheService memcachedCacheService;

	private ICacheMonitorService cacheMonitorService;

	@Profiler
	public void cacheMonitor() {
		try {
			cacheMonitorService.createCacheMonitor(memcachedCacheService.getStats());
		} catch (Exception e) {
			logger.error(e);
		}
	}

	public IMemcachedCacheService getMemcachedCacheService() {
		return memcachedCacheService;
	}

	public void setMemcachedCacheService(IMemcachedCacheService memcachedCacheService) {
		this.memcachedCacheService = memcachedCacheService;
	}

	public ICacheMonitorService getCacheMonitorService() {
		return cacheMonitorService;
	}

	public void setCacheMonitorService(ICacheMonitorService cacheMonitorService) {
		this.cacheMonitorService = cacheMonitorService;
	}

}
