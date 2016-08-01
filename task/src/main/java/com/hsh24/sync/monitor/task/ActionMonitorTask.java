package com.hsh24.sync.monitor.task;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hsh24.sync.api.cache.IMemcachedCacheService;
import com.hsh24.sync.api.monitor.IActionMonitorService;
import com.hsh24.sync.api.monitor.bo.ActionMonitor;
import com.hsh24.sync.framework.annotation.Profiler;
import com.hsh24.sync.framework.log.Logger4jCollection;
import com.hsh24.sync.framework.log.Logger4jExtend;

/**
 * 
 * @author xujiakun
 * 
 */
@Component
public class ActionMonitorTask {

	private Logger4jExtend logger = Logger4jCollection.getLogger(ActionMonitorTask.class);

	@Resource
	private IMemcachedCacheService memcachedCacheService;

	@Resource
	private IActionMonitorService actionMonitorService;

	@SuppressWarnings("unchecked")
	@Profiler
	public void actionMonitor() {
		List<ActionMonitor> list = null;
		try {
			list = (List<ActionMonitor>) memcachedCacheService.get(IMemcachedCacheService.CACHE_KEY_ACTION_LOG);
		} catch (Exception e) {
			logger.error(e);
		}

		if (list != null && list.size() != 0 && actionMonitorService.createActionMonitor(list)) {
			try {
				memcachedCacheService.remove(IMemcachedCacheService.CACHE_KEY_ACTION_LOG);
			} catch (Exception e) {
				logger.error(e);
			}
		}
	}

}
