package com.joosure.server.mvc.wechat.entity.common;

import java.util.List;


public class JsonResultBean<E extends Object> {

	private Object result;
	private List<E> resultList;
	
	private List<E> rows;
	private int total;
	
	private String retMsg;
	private boolean retFlag;
	
	private String retCode;
	
	public String getRetCode() {
		return retCode;
	}
	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}
	public JsonResultBean() {}
	public JsonResultBean(List<E> rows,int total) {
		this.rows = rows;
		this.total = total;
	}
	public JsonResultBean(boolean flag,String msg){
		if(msg == null){
			msg = "";
		}
		if(flag){
			this.retMsg = "处理成功！ "+msg;
			this.retFlag = flag;
		}else{
			this.retMsg = "处理失败！ "+msg;
			this.retFlag = flag;
		}
	}
	
	public String getRetMsg() {
		return retMsg;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}

	public boolean isRetFlag() {
		return retFlag;
	}

	public void setRetFlag(boolean retFlag) {
		this.retFlag = retFlag;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public List<E> getRows() {
		return rows;
	}

	public void setRows(List<E> rows) {
		this.rows = rows;
	}

	public List<E> getResultList() {
		return resultList;
	}

	public void setResultList(List<E> resultList) {
		this.resultList = resultList;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

}
