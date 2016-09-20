package com.joosure.server.mvc.wechat.dao.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.joosure.server.mvc.wechat.constant.CommonConstant;
import com.joosure.server.mvc.wechat.entity.pojo.Dict;
import com.joosure.server.mvc.wechat.service.db.IDictDbService;
import com.shawn.server.core.util.SpringUtil;

/**
 * 字典缓存
 * 
 * @author Ted
 *
 */
public class DictsCache {

	/*// private static List<Dict> dicts = new ArrayList<Dict>();
	private static Map<String, String> ScoreEvents = new HashMap<String, String>();

	// @Autowired
	// private static DictDao dictDao;

	static {
		getScoreMap();// 加载积分时间字典
	}

	private static void getScoreMap() {
//		IDictDbService
		IDictDbService dictDao = SpringUtil.getBean("dictDbService");
		Dict cond = new Dict();
		cond.setParamGroup(CommonConstant.GROUP_SCORE_EVENT);
		List<Dict> dicts = dictDao.getAllDict(cond);
		if (dicts != null && dicts.size() > 0) {
			ScoreEvents.clear();
			for (Dict dict : dicts) {
				// ScoreEvents.put(dict.getParamId(),dict.getParamName());
				ScoreEvents.put(dict.getParamId(), dict.getParamvalue());
			}
		}
	}

	public static Map<String, String> getScoreEvent() {
		return ScoreEvents;
	}*/
	
	private static Map<String, String> ScoreEvents = new HashMap<String, String>();
	private static List<Dict> allDicts = new ArrayList<Dict>();
	private static IDictDbService dictDao = SpringUtil.getBean("dictDbService");
	
	public static void initDicts(){
		allDicts = dictDao.getAllDict(null);
		getScoreMap();// 加载积分时间字典
	}

	/**
	 * 通过group 和  id  获取值
	 * @param paramGroup
	 * @param paramId
	 * @return
	 */
	public static String getValue(String paramGroup,String paramId){
		if(paramGroup==null || paramId==null){
			return null;
		}
		for(Dict dict:allDicts){
			if(paramGroup.equals(dict.getParamGroup()) && 
					paramId.equals(dict.getParamId())){
				return dict.getParamvalue();
			}
		}
		return "";
	}
	
	/**
	 * 通过 group  获取一组 dicts
	 * @param paramGroup
	 * @return
	 */
	public static List<Dict> getGroupDict(String paramGroup){
		if(paramGroup==null){
			return null;
		}
		List<Dict> gGroup = new ArrayList<Dict>();
		for(Dict dict:allDicts){
			if(paramGroup.equals(dict.getParamGroup())){
				gGroup.add(dict);
			}
		}
		return gGroup;
	}
	
	private static void getScoreMap() {
		Dict cond = new Dict();
		cond.setParamGroup(CommonConstant.GROUP_SCORE_EVENT);
		List<Dict> dicts = dictDao.getAllDict(cond);
		if (dicts != null && dicts.size() > 0) {
			ScoreEvents.clear();
			for (Dict dict : dicts) {
				ScoreEvents.put(dict.getParamId(), dict.getParamvalue());
			}
		}
	}
	
	public static Map<String, String> getScoreEvent() {
		return ScoreEvents;
	}
	
	/**
	 * 刷新所有字典
	 */
	public static void refreshAll(){
		allDicts.clear();
		allDicts = dictDao.getAllDict(null);
	}
	
	/**
	 * 刷新一个组的字典
	 * @param paramGroup
	 */
	public static void refreshGroup(String paramGroup){
		Dict cond = new Dict();
		cond.setParamGroup(paramGroup);
		List<Dict> gDict = dictDao.getAllDict(cond);
		Iterator<Dict> itr = allDicts.iterator();
		while(itr.hasNext()){
			Dict dict = itr.next();
			if(paramGroup.equals(dict.getParamGroup())){
				itr.remove();
			}
		}
		allDicts.addAll(gDict);
		refreshScoreMap();
	}
	
	public static void refreshScoreMap(){
		List<Dict> dicts = getGroupDict(CommonConstant.GROUP_SCORE_EVENT);
		if (dicts != null && dicts.size() > 0) {
			ScoreEvents.clear();
			for (Dict dict : dicts) {
				ScoreEvents.put(dict.getParamId(), dict.getParamvalue());
			}
		}
	}
}
