package com.techwells.blue.util;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;


/**
 * 上传文件到服务器工具类
 * @author Administrator
 *
 */
public class UploadFileUtils {
	
	/**
	 * 上传文件
	 * @param childFolder 子文件夹
	 * @param fileName  文件的名字
	 * @param file   MultipartFile
	 * @return
	 * @throws Exception
	 */
	public static String uploadFile(String childFolder,String fileName,MultipartFile file)throws Exception{
		String filePath=BlueConstants.UPLOAD_PATH+childFolder+"/";
//		String filePath=ApplicationMarketConstants.UPLOAD_PATH+childFolder+"\\";  
		File targetFile=new File(filePath,fileName);
		String fileUrl=BlueConstants.UPLOAD_URL+childFolder+"/"+fileName;
		//如果文件夹不存在，那么新建一个
		if (!targetFile.getParentFile().exists()) {
			targetFile.getParentFile().mkdirs();
		}
		file.transferTo(targetFile);
		return fileUrl;
	}
	
	
	
	/**
	 * 创建子文件
	 * @param targetFile  目标文件
	 * @throws Exception
	 */
	public static void createChildFolder(File targetFile)throws Exception{
		if (!targetFile.getParentFile().exists()) {
			targetFile.getParentFile().mkdirs();  //如果文件夹不存在，那么就创建
		}
	}
	
	
	
	
	
	
	
}
