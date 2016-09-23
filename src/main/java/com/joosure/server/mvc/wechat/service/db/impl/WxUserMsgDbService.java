package com.joosure.server.mvc.wechat.service.db.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joosure.server.mvc.wechat.constant.CommonConstant;
import com.joosure.server.mvc.wechat.dao.database.WxUserMsgDao;
import com.joosure.server.mvc.wechat.entity.pojo.WxUserMsg;
import com.joosure.server.mvc.wechat.service.db.IWxUserMsgDbService;

@Service("wxUserMsgDbService")
public class WxUserMsgDbService implements IWxUserMsgDbService{

	@Autowired
	private WxUserMsgDao wxUserMsgDao;

	@Override
	public int insertWxUserMsg(WxUserMsg wxUserMsg) {
		WxUserMsg msg = chkIfExist(wxUserMsg.getUserid());
		if(msg!=null){
			wxUserMsg.setMsgid(msg.getMsgid());
			return wxUserMsgDao.updateByPrimaryKeySelective(wxUserMsg);
		}
		if(wxUserMsg.getMsgcount()==null){
			wxUserMsg.setMsgcount("0,0,0");
			wxUserMsg.setTotal(0);
		}
		return wxUserMsgDao.insertSelective(wxUserMsg);
	}

	/**
	 * 检查是否存在该用户未读消息
	 * @param userId
	 * @return
	 */
	public WxUserMsg chkIfExist(int userId){
		WxUserMsg cond = new WxUserMsg();
		cond.setUserid(userId);
		WxUserMsg userMsg = wxUserMsgDao.getById(cond);
		if(userMsg!=null && userMsg.getMsgid()!=null){
			return userMsg;
		}
		return null;
	}

	@Override
	public int updateWxUserMsg(WxUserMsg wxUserMsg) {
		return wxUserMsgDao.updateByPrimaryKeySelective(wxUserMsg);
	}

	@Override
	public WxUserMsg getWxUserMsg(WxUserMsg wxUserMsg) {
		return wxUserMsgDao.getById(wxUserMsg);
	}

	@Override
	public int deleteWxUserMsg(WxUserMsg record) {
		return wxUserMsgDao.deleteById(record);
	}


	/**
	 * 收到一条消息
	 * @param msgType
	 * @param userId
	 * @return
	 */
	public int receiveWxUserMsg(String msgType,int userId){
		if(msgType==null){
			return -1;//失败吧
		}
		return changeCount(msgType,userId,false);
	}

	/**
	 * 阅读一条消息
	 * @param msgType
	 * @param userId
	 * @return
	 */
	public int readWxUserMsg(String msgType,int userId){
		if(msgType==null){
			return -1;//失败吧
		}
		return changeCount(msgType,userId,true);
	}

	/**
	 * 一次阅读同一类型的消息
	 * @param msgType
	 * @param userId
	 * @return
	 */
	public int readSameTypeMsg(String msgType,int userId){
		if(msgType==null){
			return -1;//失败吧
		}
		WxUserMsg cond = new WxUserMsg();
		cond.setUserid(userId);
		WxUserMsg msg = wxUserMsgDao.getById(cond);
		if(msg !=null && msg.getUserid() !=null){
			String[] msgTypes = msg.getMsgtype().split(",");
			String[] msgCounts = msg.getMsgcount().split(",");
			for(int i=0;i<msgTypes.length;i++){
				if(msgType.equals(msgTypes[i])){
					String countStr = msgCounts[i].trim();
					if(countStr!=null || "".equals(countStr)){
						int count = Integer.parseInt(countStr);
						msgCounts[i] = "0";  //该类型的消息 全部阅读完
						msg.setTotal(msg.getTotal()-count);
						break;
					}
				}
			}
			StringBuffer sb = new StringBuffer();
			for(int i=0;i<msgCounts.length;i++){
				if(i<msgCounts.length-1){
					sb.append(msgCounts[i].trim()+",");
				}else{
					sb.append(msgCounts[i]);
				}
			}
			msg.setMsgcount(sb.toString());
			return wxUserMsgDao.updateByPrimaryKeySelective(msg);
		}
		return 0;
	}

	/**
	 * 一次性阅读所有消息 ，即将记录清除
	 * @param userId
	 * @return
	 */
	public int readAllMsgs(Integer userId){
		if(userId != null){
			WxUserMsg cond = new WxUserMsg();
			cond.setUserid(userId);
			WxUserMsg msg = wxUserMsgDao.getById(cond);
			if(msg != null){
				return wxUserMsgDao.deleteById(msg);
			}
		}
		return 0;
	}
	/**
	 * 更改 消息数量
	 * @param msgType
	 * @param userId
	 * @param flag--》 true:阅读  false:收到
	 * @return
	 */
	private int changeCount(String msgType,int userId, boolean flag){
		//首先获取到 该用户的未读消息
		WxUserMsg cond = new WxUserMsg();
		cond.setUserid(userId);
		WxUserMsg msg = wxUserMsgDao.getById(cond);
		if(msg!=null && msg.getUserid()!=null){
			//表中存在数据，则将相应的数据 变更然后更新操作
			String[] msgTypes = msg.getMsgtype().split(",");
			String[] msgCounts = msg.getMsgcount().split(",");
			for(int i=0;i<msgTypes.length;i++){
				if(msgType.equals(msgTypes[i])){
					String countStr = msgCounts[i].trim();
					if(countStr!=null || "".equals(countStr)){
						int count = Integer.parseInt(countStr);
						if(flag){
							if(count > 0 ){
								count -=1;//原来的基础上减去一条
								msg.setTotal(msg.getTotal()-1);
							}
						}else{
							count += 1;//原来的基础上增加一条
							msg.setTotal(msg.getTotal()+1);
						}
						msgCounts[i] = String.valueOf(count);
					}else{
						if(flag){
							msgCounts[i] = String.valueOf(0);//减去一条
						}else{
							msgCounts[i] = String.valueOf(1);//新增一条
						}
					}
				}
			}
			//msg.setMsgcount(msgCounts.toString()); //内存地址 L
			//msg.setMsgcount(Arrays.toString(msgCounts));//带了  中括号   [*,*,*]
			//String msgC = Arrays.toString(msgCounts);
			//msg.setMsgcount(msgC.substring(1, msgC.length()-1).trim());
			StringBuffer sb = new StringBuffer();
			for(int i=0;i<msgCounts.length;i++){
				if(i<msgCounts.length-1){
					sb.append(msgCounts[i].trim()+",");
				}else{
					sb.append(msgCounts[i]);
				}
			}
			msg.setMsgcount(sb.toString());
			int ret = wxUserMsgDao.updateByPrimaryKeySelective(msg);
			/*if(flag && msg.getTotal() == 0){//读消息，若消息条数为0，则删除记录
				ret = wxUserMsgDao.deleteById(msg);
			}*/
			return ret;
		}else{
			if(flag){
				return 0; //说明本来就不存在消息
			}else{
				//如果表中不存在数据，则插入
				String count = "0,0,0";//默认初始 都为0
				if(msgType.equals(CommonConstant.UserMsgType_Req)){//第一位
					count = "1,0,0";
				}
				if(msgType.equals(CommonConstant.UserMsgType_Rep)){//第二位
					count = "0,1,0";
				}
				if(msgType.equals(CommonConstant.UserMsgType_Apr)){//第三位
					count = "0,0,1";
				}
				msg = new WxUserMsg();
				msg.setMsgcount(count);
				msg.setUserid(userId);
				msg.setTotal(1);
				return wxUserMsgDao.insertSelective(msg);
			}
		}
	}
}
