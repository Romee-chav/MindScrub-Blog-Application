package com.mindScrub.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.web.multipart.MultipartFile;

public class FileUpload {

	public static boolean save(String uploadDir,String fileName,MultipartFile multipartFile) {
		Path uploadPath = Paths.get(uploadDir);
		try(InputStream inputStream = multipartFile.getInputStream()){
			if(!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}
			Path fullPath = uploadPath.resolve(fileName);
			Files.copy(inputStream,fullPath,StandardCopyOption.REPLACE_EXISTING);
			return true;
		}catch(IOException ex){
			ex.printStackTrace();
			return false;
		}
	}
}
