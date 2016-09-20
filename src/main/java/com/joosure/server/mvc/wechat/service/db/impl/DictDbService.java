package com.joosure.server.mvc.wechat.service.db.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joosure.server.mvc.wechat.dao.database.DictDao;
import com.joosure.server.mvc.wechat.entity.pojo.Dict;
import com.joosure.server.mvc.wechat.service.db.IDictDbService;

@Service("dictDbService")
public class DictDbService implements IDictDbService{
	
	@Autowired
	private DictDao dictDao;

	@Override
	public List<Dict> getAllDict(Dict cond) {
		return dictDao.getAllDict(cond);
	}


	@Override
	public int updateDict(Dict dict) {
		return dictDao.updateDict(dict);
	}


	@Override
	public int insertDict(Dict cond) {
		return dictDao.insertDict(cond);
	}
	

}
