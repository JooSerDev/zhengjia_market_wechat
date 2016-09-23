package com.joosure.server.mvc.wechat.listener;

import com.joosure.server.mvc.wechat.dao.cache.DictsCache;
import com.joosure.server.mvc.wechat.dao.cache.ItemCache;
import com.shawn.server.core.listener.PreContextInitListener;

public class InitialListener extends PreContextInitListener {

	@Override
	public void Initial() {
		ItemCache.inti();
		DictsCache.initDicts();
	}

	@Override
	public void Destroy() {

	}

}
