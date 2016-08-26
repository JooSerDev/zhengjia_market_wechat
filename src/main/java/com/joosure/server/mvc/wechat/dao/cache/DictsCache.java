package com.joosure.server.mvc.wechat.dao.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.joosure.server.mvc.wechat.constant.CommonConstant;
import com.joosure.server.mvc.wechat.dao.database.DictDao;
import com.joosure.server.mvc.wechat.entity.pojo.Dict;
import com.shawn.server.core.util.SpringUtil;

/**
 * 字典缓存
 * @author Ted
 *
 */
public class DictsCache {

//	private static List<Dict> dicts = new ArrayList<Dict>();
	private static Map<String,String> ScoreEvents = new HashMap<String,String>();

	//	@Autowired
	//	private static DictDao dictDao;

	static{
		getScoreMap();//加载积分时间字典
	}

	private static void getScoreMap(){
		DictDao dictDao = SpringUtil.getBean(DictDao.class);
		Dict cond = new Dict();
		cond.setParamGroup(CommonConstant.GROUP_SCORE_EVENT);
		List<Dict> dicts = dictDao.getAllDict(cond);
		if(dicts!=null && dicts.size()>0){
			ScoreEvents.clear();
			for(Dict dict:dicts){
				ScoreEvents.put(dict.getParamId(),dict.getParamName());
			}
		}
	}

	public static Map<String,String> getScoreEvent(){
		return ScoreEvents;
	}
}
