package com.joosure.server.mvc.wechat.dao.database;

import com.joosure.server.mvc.wechat.entity.pojo.WxUserMsg;

public interface WxUserMsgDao {
    /**
     * 根据主键删除
     * 参数:主键
     * 返回:删除个数
     * @ibatorgenerated 2016-09-14 14:33:22
     */
    int deleteByPrimaryKey(Integer msgid);

    /**
     * 插入，空属性也会插入
     * 参数:pojo对象
     * 返回:删除个数
     * @ibatorgenerated 2016-09-14 14:33:22
     */
    int insert(WxUserMsg record);

    /**
     * 插入，空属性不会插入
     * 参数:pojo对象
     * 返回:删除个数
     * @ibatorgenerated 2016-09-14 14:33:22
     */
    int insertSelective(WxUserMsg record);

    /**
     * 根据主键查询
     * 参数:查询条件,主键值
     * 返回:对象
     * @ibatorgenerated 2016-09-14 14:33:22
     */
    WxUserMsg selectByPrimaryKey(Integer msgid);

    /**
     * 根据主键修改，空值条件不会修改成null
     * 参数:1.要修改成的值
     * 返回:成功修改个数
     * @ibatorgenerated 2016-09-14 14:33:22
     */
    int updateByPrimaryKeySelective(WxUserMsg record);

    /**
     * 根据主键修改，空值条件会修改成null
     * 参数:1.要修改成的值
     * 返回:成功修改个数
     * @ibatorgenerated 2016-09-14 14:33:22
     */
    int updateByPrimaryKey(WxUserMsg record);

	WxUserMsg getById(WxUserMsg wxUserMsg);

	int deleteById(WxUserMsg record);
}