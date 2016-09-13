package com.joosure.server.mvc.wechat.service;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joosure.server.mvc.wechat.constant.StorageConstant;
import com.joosure.server.mvc.wechat.service.itf.IObjectStorageService;
import com.joosure.server.mvc.wechat.service.itf.ISystemLogStorageService;
import com.shawn.server.core.util.ImageCropUtil;
import com.shawn.server.core.util.StringUtil;

@Service("objectStorageService")
public class ObjectStorageService implements IObjectStorageService{

	@Autowired
	private ISystemLogStorageService logService;

	public void putObject() {

	}

	public void putImg() {

	}

	/**
	 * 通过url本地化头像图片,返回本地图片URL
	 * 
	 * @param imgUrl
	 * @return
	 */
	public String putHeadImg(String imgUrl, HttpServletRequest request) {
		URL url = null;
		File imgFile = null;
		// 生成图片目录
		String StoragePath = request.getServletContext().getRealPath("/");
		String pathFileURI = StorageConstant.HEAD_IMG_FILE_PATH + getFileDatePath();
		File pathFile = new File(StoragePath + pathFileURI);
		if (!pathFile.exists() || !pathFile.isDirectory()) {
			pathFile.mkdirs();
		}
		imgFile = makeRandomNameFile(StoragePath + pathFileURI);

		try {

			url = new URL(imgUrl);
			DataInputStream dataInputStream = new DataInputStream(url.openStream());
			FileOutputStream fileOutputStream = new FileOutputStream(imgFile);

			byte[] buffer = new byte[1024];
			int length;

			while ((length = dataInputStream.read(buffer)) > 0) {
				fileOutputStream.write(buffer, 0, length);
			}

			dataInputStream.close();
			fileOutputStream.close();

			ImageCropUtil.ImageCropCenterSquare(imgFile.getPath(), imgFile.getPath(), 100, 100);

		} catch (MalformedURLException e) {
			logService.systemException("img url can not read", "ObjectStorageService.putHeadImg");
		} catch (IOException e) {
			logService.systemException("img url DataInputStream can not read", "ObjectStorageService.putHeadImg");
		}

		if (imgFile != null) {
			return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ request.getContextPath() + pathFileURI + "/" + imgFile.getName();
		}

		return null;
	}

	/**
	 * 本地化宝贝图片
	 * 
	 * @param fileData
	 * @param request
	 * @return
	 */
	public String[] putItemImg(byte[] fileData, HttpServletRequest request) {
		File imgFile = null;
		String StoragePath = request.getServletContext().getRealPath("/");
		String datePath = getFileDatePath();
		;
		String pathFileURI = StorageConstant.ITEM_IMG_FILE_PATH + datePath;
		File pathFile = new File(StoragePath + pathFileURI);
		if (!pathFile.exists() || !pathFile.isDirectory()) {
			pathFile.mkdirs();
		}
		imgFile = makeRandomNameFile(StoragePath + pathFileURI);

		try {
			FileOutputStream fileOutputStream = new FileOutputStream(imgFile);
			fileOutputStream.write(fileData);

			fileOutputStream.close();
		} catch (IOException e) {
			logService.systemException("img url fileOutputStream can not read", "ObjectStorageService.putItemImg");
		}

		if (imgFile != null) {

			String sourcePath = imgFile.getPath();
			String sourceName = imgFile.getName();

			String centerPathFileURI = StorageConstant.ITEM_IMG_CENTER_SQUARE_FILE_PATH + datePath;

			File pathCenterFile = new File(StoragePath + centerPathFileURI);
			if (!pathCenterFile.exists() || !pathCenterFile.isDirectory()) {
				pathCenterFile.mkdirs();
			}

			String descPath = StoragePath + centerPathFileURI + "/" + sourceName;

			ImageCropUtil.ImageCropCenterSquare(sourcePath, descPath, StorageConstant.ITEM_IMG_RESIZE,
					StorageConstant.ITEM_IMG_RESIZE);

			String baseHttpUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ request.getContextPath();

			return new String[] { baseHttpUrl + pathFileURI + "/" + imgFile.getName(),
					baseHttpUrl + centerPathFileURI + "/" + imgFile.getName() };
		}

		return null;
	}

	/**
	 * 获得日期地址路径
	 * 
	 * @return
	 */
	private String getFileDatePath() {
		StringBuffer sb = new StringBuffer("/");
		Calendar cal = Calendar.getInstance();
		sb.append(cal.get(Calendar.YEAR));
		sb.append(cal.get(Calendar.MONTH));
		sb.append("/");
		sb.append(cal.get(Calendar.DAY_OF_MONTH));
		return sb.toString();
	}

	/**
	 * 生成random名称的文件
	 * 
	 * @param path
	 * @return
	 */
	private File makeRandomNameFile(String path) {
		File file = null;
		do {
			String randomFilename = StringUtil.getRandomString(32);
			file = new File(path + "/" + randomFilename + ".jpg");
		} while (file.exists());

		return file;
	}

}
