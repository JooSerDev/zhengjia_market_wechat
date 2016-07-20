package com.joosure.server.mvc.wechat.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sword.wechat4j.token.TokenProxy;

import com.shawn.server.wechat.common.media.MediaFileUtil;

@Service
public class WechatNativeService {

	@Autowired
	private SystemLogStorageService logService;
	@Autowired
	private ObjectStorageService objectStorageService;

	public String getAccessToken() {
		System.out.println("get access token");
		String token = TokenProxy.accessToken();
		System.out.println("get access token success :: " + token);
		return token;
	}

	public String getJsApiTicket() {
		System.out.println("get js api ticket");
		String ticket = TokenProxy.jsApiTicket();
		System.out.println("get js api ticket success :: " + ticket);
		return ticket;
	}

	/**
	 * 下载宝贝图片文件
	 * 
	 * @param mediaIds
	 * @param request
	 * @return
	 */
	public List<String> downloadItemImagesByMediaIds(String mediaIds, HttpServletRequest request) {
		List<String> urlList = null;
		String[] mediaIdArray = mediaIds.split(";");
		if (mediaIdArray.length > 0) {
			urlList = new ArrayList<>();
			for (int i = 0; i < mediaIdArray.length; i++) {
				String mediaId = mediaIdArray[i];
				String url = downloadItemImgByMediaId(mediaId, request);
				if (url != null) {
					urlList.add(url);
				}
			}
		}
		return urlList;
	}

	/**
	 * 通过media api 接口，本地化宝贝文件
	 * 
	 * @param mediaId
	 * @param request
	 * @return
	 */
	private String downloadItemImgByMediaId(String mediaId, HttpServletRequest request) {
		String url = null;
		try {
			MediaFileUtil mfu = new MediaFileUtil(mediaId, getAccessToken());
			byte[] fileData = mfu.download();
			url = objectStorageService.putItemImg(fileData, request);
		} catch (IOException e) {
			logService.systemException("download file by media api fail", "WechatNativeService.downloadByMediaId");
		}

		return url;
	}

}
