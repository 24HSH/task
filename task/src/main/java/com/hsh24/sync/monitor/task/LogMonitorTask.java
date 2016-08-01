package com.hsh24.sync.monitor.task;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hsh24.sync.api.cache.IMemcachedCacheService;
import com.hsh24.sync.api.monitor.ILogMonitorService;
import com.hsh24.sync.api.monitor.bo.LogMonitor;
import com.hsh24.sync.framework.annotation.Profiler;
import com.hsh24.sync.framework.log.Logger4jCollection;
import com.hsh24.sync.framework.log.Logger4jExtend;

/**
 * 
 * @author xujiakun
 * 
 */
@Component
public class LogMonitorTask {

	private Logger4jExtend logger = Logger4jCollection.getLogger(LogMonitorTask.class);

	@Resource
	private IMemcachedCacheService memcachedCacheService;

	@Resource
	private ILogMonitorService logMonitorService;

	@SuppressWarnings("unchecked")
	@Profiler
	public void logMonitor() {
		List<LogMonitor> list = null;
		try {
			list = (List<LogMonitor>) memcachedCacheService.get(IMemcachedCacheService.CACHE_KEY_LOG_MONITOR);
		} catch (Exception e) {
			logger.error(e);
		}

		if (list != null && list.size() != 0 && logMonitorService.createLogMonitor(list)) {
			try {
				memcachedCacheService.remove(IMemcachedCacheService.CACHE_KEY_LOG_MONITOR);
			} catch (Exception e) {
				logger.error(e);
			}
		}
	}

}
