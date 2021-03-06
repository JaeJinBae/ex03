package com.dgit.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dgit.util.MediaUtils;
import com.dgit.util.UploadFileUtils;

@Controller
public class UploadController {
	private static final Logger logger= LoggerFactory.getLogger(UploadController.class);
	
	private String innerUploadPath="resources/upload";
	
	@Resource(name="uploadPath")
	private String outUploadPath;
	
	@RequestMapping(value="/innerUpload", method=RequestMethod.GET)
	public String innerUploadTest(){
		return "innerUploadForm";
	}
	
	@RequestMapping(value="/innerUpload",method=RequestMethod.POST)
	public String innerUploadResult(String test, MultipartFile file, HttpServletRequest request, Model model){
		logger.info("test:"+test);
		logger.info("file: "+file.getOriginalFilename());
		
		String root_path=request.getSession().getServletContext().getRealPath("/");
		
		File dirPath=new File(root_path+"/"+innerUploadPath);
		if(dirPath.exists() == false){
			dirPath.mkdirs();
		}
		
		UUID uid=UUID.randomUUID();//중복방지를 위하여 랜덤값 생성
		String savedName=uid.toString()+"_"+file.getOriginalFilename();
		
		//해당 경로에 파일 그릇을 만듬
		File target=new File(root_path+"/"+innerUploadPath,savedName);
		
		try {
			FileCopyUtils.copy(file.getBytes(), target);
			model.addAttribute("test",test);
			model.addAttribute("filename",innerUploadPath+"/"+savedName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return "innerUploadResult";
	}
	
	//innerMultiUpload
	@RequestMapping(value="/innerMultiUpload",method=RequestMethod.GET)
	public String innerMultiUploadForm(){
		logger.info("innerMultiUploadForm GET");
		return "innerMultiUploadForm";
	}
	
	@RequestMapping(value="/innerMultiUpload", method=RequestMethod.POST)
	public String innerMultiUploadResult(String test, List<MultipartFile> files,HttpServletRequest request, Model model){
		logger.info("innerMultiUploadResult POST");
		for(MultipartFile file: files){
			logger.info(file.getOriginalFilename());
		}
		
		String root_path=request.getSession().getServletContext().getRealPath("/");
		File dirPath=new File(root_path+"/"+innerUploadPath);
		if(dirPath.exists() == false){
			dirPath.mkdirs();
		}
		
		ArrayList<String> list=new ArrayList<>();
		for(MultipartFile file: files){	
			UUID uid=UUID.randomUUID();//중복방지를 위하여 랜덤값 생성
			String savedName=uid.toString()+"_"+file.getOriginalFilename();
			
			
			
			//해당 경로에 파일 그릇을 만듬
			File target=new File(root_path+"/"+innerUploadPath,savedName);
			
			try {
				FileCopyUtils.copy(file.getBytes(), target);
				list.add("resources/upload/"+uid.toString()+"_"+file.getOriginalFilename());
				
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		model.addAttribute("test",test);
		model.addAttribute("list",list);
		
		return "innerMultiUploadResult";
	}
	
	@RequestMapping(value="/outUpload",method=RequestMethod.GET)
	public String outUpload(){
		logger.info("out upload form GET");
		logger.info("outUploadForm addr: "+outUploadPath);
		return "outUploadForm";
	}
	
	@RequestMapping(value="/outUpload",method=RequestMethod.POST)
	public String outUploadResult(String test,MultipartFile file, Model model){
		logger.info("out upload result POST");
		logger.info("test: "+test);
		logger.info("file name: "+file.getOriginalFilename());
		logger.info("file size: "+file.getSize());
		logger.info("file contentType: "+file.getContentType());
		
		File dirPath=new File(outUploadPath);
		if(dirPath.exists()==false){
			dirPath.mkdirs();
		}
		
		UUID uid=UUID.randomUUID();
		String savedName=uid.toString()+"_"+file.getOriginalFilename();
		File target=new File(outUploadPath,savedName);
		try {
			FileCopyUtils.copy(file.getBytes(), target);
			model.addAttribute("test",test);
			model.addAttribute("filename",savedName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "outUploadResult";
	}
	
	@RequestMapping(value="/uploadDrag", method=RequestMethod.GET)
	public String uploadDragForm(){
		logger.info("[uploadDrag] form GET");
		
		return "uploadDragForm";
	}
	
	@ResponseBody
	@RequestMapping(value="/uploadDrag", method= RequestMethod.POST)
	public ResponseEntity<List<String>> uploadDragResult(String test, List<MultipartFile> files){
		logger.info("uploadDragResult post");
		ResponseEntity<List<String>> entity = null;
		logger.info("innerMultiUploadResult(), length: "+files.size());
		logger.info("test : " + test);
		List<String> list = new ArrayList<>();
		
		try{
			for(MultipartFile f : files){
				logger.info("file : " + f.getOriginalFilename()); 
				
				/*File dir = new File(outUploadPath);
				if(!dir.exists()){
					dir.mkdirs();
				}
				
				UUID uid = UUID.randomUUID();
				String savedName = uid.toString() + "_" + f.getOriginalFilename();
				File imgFile = new File(outUploadPath, saveName);
				
				FileCopyUtils.copy(f.getBytes(), imgFile);*/
				String savedName=UploadFileUtils.uploadFile(outUploadPath, f.getOriginalFilename(), f.getBytes());
				list.add(outUploadPath + "/" + savedName);
				
			}
			entity = new ResponseEntity<List<String>>(list, HttpStatus.OK);
		}catch(Exception e){
			e.printStackTrace();
			entity = new ResponseEntity<List<String>>(list, HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	@RequestMapping(value="/uploadPreview", method=RequestMethod.GET)
	public String uploadPreviewForm(){
		logger.info("[uploadPreview] GET");
		return "uploadPreviewForm";
	}
	
	@RequestMapping(value="/uploadPreview", method=RequestMethod.POST)
	public String uploadPreviewResult(String writer, List<MultipartFile> files, Model model) throws IOException, Exception{
		ResponseEntity<List<String>> entity=null;
		
		logger.info("[uploadPreview] POST");
		logger.info("writer: "+writer);
		System.out.println("파일 갯수="+files.size());
		List<String> list = new ArrayList<>();
		
		for(MultipartFile f : files){
			logger.info("file : " + f.getOriginalFilename()); 
			
			//upload처리
			String savedName=UploadFileUtils.uploadFile(outUploadPath, f.getOriginalFilename(), f.getBytes());
			list.add(outUploadPath + "/" + savedName);
			
		}
		
		model.addAttribute("writer",writer);
		model.addAttribute("list",list);
		
		return "uploadPreviewResult";
	}
	
	
	
	@ResponseBody
	@RequestMapping(value="displayFile",method=RequestMethod.GET)
	public ResponseEntity<byte[]> displayFile(String filename){
		ResponseEntity<byte[]> entity=null;
		InputStream in=null;
		
		logger.info("[displayFilename]: "+filename); // /2018/03/19/asdf.jpg
		
		try {
			String formatName=filename.substring(filename.lastIndexOf(".")+1);
			MediaType type=MediaUtils.getMediaType(formatName);
			HttpHeaders headers=new HttpHeaders();
			headers.setContentType(type);
			
			in=new FileInputStream(filename);
			
			entity=new ResponseEntity<>(IOUtils.toByteArray(in),headers,HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			entity=new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	@ResponseBody
	@RequestMapping(value="deleteFile", method = RequestMethod.GET)
	public ResponseEntity<String> deleteFile(String filename){
		ResponseEntity<String> entity = null;
		logger.info("deleteFile()");
		logger.info("filename : " + filename);
		try{
			File del = new File(filename);
			if(del.exists()){
				del.delete();
			}
			entity = new ResponseEntity<String>("success", HttpStatus.OK);			
		}catch(Exception e){
			entity = new ResponseEntity<String>("success", HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
}














