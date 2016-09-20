package com.joosure.server.mvc.wechat.dao.database;

import java.util.List;

import com.joosure.server.mvc.wechat.entity.pojo.Dict;

public interface DictDao {

	List<Dict> getAllDict(Dict cond);

	int updateDict(Dict dict);

	int insertDict(Dict cond);


}
