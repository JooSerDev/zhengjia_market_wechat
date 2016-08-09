package com.joosure.server.mvc.wechat;

import java.lang.reflect.Field;

import com.joosure.server.mvc.wechat.entity.pojo.CheckCode;
import com.joosure.server.mvc.wechat.entity.pojo.Exchange;
import com.joosure.server.mvc.wechat.entity.pojo.Item;
import com.joosure.server.mvc.wechat.entity.pojo.ItemLike;

@SuppressWarnings("rawtypes")
public class SqlBuilder {

	static String[] Jobs = { "insert", "update" };
	static String job = Jobs[0];
	static String table = "item_like";
	static Class clazz = ItemLike.class;

	public static void main(String[] args) {
		StringBuffer sb = new StringBuffer(job);
		if (job.equals("update")) {
			sb.append(" ").append(table).append(" set ");
			Field[] fields = clazz.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				sb.append(field.getName()).append("= #{").append(field.getName()).append("}, ");
			}
		} else if (job.equals("insert")) {
			sb.append(" into ").append(table).append(" (");
			Field[] fields = clazz.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				if (i != 0) {
					sb.append(",");
				}
				sb.append(field.getName());
			}
			sb.append(") values (");
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				if (i != 0) {
					sb.append(",");
				}
				sb.append("#{").append(field.getName()).append("}");
			}
			sb.append(");");
		}

		System.out.println(sb.toString());
	}

}
