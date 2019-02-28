package com.douzone.mysite.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

import javax.management.RuntimeErrorException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class FileuploadService {
	private static final String SAVE_PATH = "/uploads";
	private static final String URL = "/uploads/images";
	
	public String restore(MultipartFile multipartFile) {
		String url = "";
		
		try {
			
			if(multipartFile.isEmpty()) {
				return url;
			}
			
			String origonalFileName = multipartFile.getOriginalFilename();
			String extName = origonalFileName.substring(origonalFileName.lastIndexOf('.') + 1);
			String saveFileName = generateSaveFileName(extName);
			long fileSize = multipartFile.getSize();
			
			byte[] fileData = multipartFile.getBytes();
			OutputStream os = new FileOutputStream(SAVE_PATH + "/" + saveFileName);
			os.write(fileData);
			os.close();
			
			url = URL + "/" + saveFileName;
			
		} catch (IOException e) {
			new RuntimeException("upload file");
		}
		return url;
	}
	
	private String generateSaveFileName(String extName) {
		String fileName = "";
		Calendar calendar = Calendar.getInstance();
		
		fileName += calendar.get(Calendar.YEAR);
		fileName += calendar.get(Calendar.MONTH);
		fileName += calendar.get(Calendar.DATE);
		fileName += calendar.get(Calendar.HOUR);
		fileName += calendar.get(Calendar.MINUTE);
		fileName += calendar.get(Calendar.SECOND);
		fileName += calendar.get(Calendar.MILLISECOND);
		fileName += ("." + extName);
		
		return fileName;
	}
	
	public void deleteFile(String fileUrl) {
		//파일 삭제
		String sFilePath = fileUrl.replace("images/", "");

		File f = new File(sFilePath);
			
		if(f.exists()) {
			f.delete();
		}else {
			System.out.println("파일이 존재하지 않습니다.");
		}
	}
}
