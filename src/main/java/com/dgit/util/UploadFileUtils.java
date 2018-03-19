package com.dgit.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.springframework.util.FileCopyUtils;

public class UploadFileUtils {
	public static String uploadFile(String uploadPath, String originalName, byte[] fileData) throws Exception{
		File dirPath=new File(uploadPath);
		if(dirPath.exists()==false){
			dirPath.mkdirs();
		}
		
		UUID uid=UUID.randomUUID();
		String savedName=uid.toString()+"_"+originalName;
		
		//년월일 폴더 만들기
		// 한 폴더에 저장할 수 있는 용량이 제한되어 있으므로, 년월일 폴더를 만들도록 함
		String savedPath=calPath(uploadPath);
		
		//c:/zzz/upload/2018/03/19
		File target=new File(uploadPath+savedPath,savedName);
		try {
			FileCopyUtils.copy(fileData, target);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//Thumnail처리(작은이미지)
		String thumbName=makeThumnail(uploadPath, savedPath, savedName);
		
		
		return thumbName; //savedPath+"/"+savedName;
	}
	
	public static void makeDir(String uploadPath,String ...paths){
		for(String path:paths){
			File dirPath=new File(uploadPath+path);
			if(dirPath.exists()==false){
				dirPath.mkdir();
			}
		}
	}
	
	private static String calPath(String uploadPath){
		Calendar cal=Calendar.getInstance();
		String yearPath="/"+cal.get(Calendar.YEAR);
		//String monthPath=yearPath+"/"+cal.get(Calendar.MONTH)+1;
		String monthPath=String.format("%s/%02d", yearPath, cal.get(Calendar.MONTH)+1);
		String datePath=String.format("%s/%02d", monthPath, cal.get(Calendar.DATE));
		
		makeDir(uploadPath, yearPath, monthPath, datePath);
		
		return datePath;
	}
	
	private static String makeThumnail(String uploadPath, String path, String filename) throws IOException{
		//원본이미지를 읽어둘임
		BufferedImage sourceImg=ImageIO.read(new File(uploadPath+path+"/"+filename));
		
		//원본 이미지를 리사이징 함.
		//높이를 맞품. 100px 고정
		BufferedImage destImg=Scalr.resize(sourceImg, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT,100);
		
		String thumbnailName=uploadPath+path+"/s_"+filename;
		
		File newFile=new File(thumbnailName);//껍데기 만듬
		String formatName=filename.substring(filename.lastIndexOf(".")+1);
		
		//Thumbnail 경로/파일 이름에 resizing된 이미지를 넣는다.
		ImageIO.write(destImg, formatName.toUpperCase(), newFile);
		
		return thumbnailName.substring(uploadPath.length());
	}
}











