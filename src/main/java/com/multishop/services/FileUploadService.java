package com.multishop.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
	// save image
	String saveFile(String path,MultipartFile file) throws IOException;
	
	//to serve image
	InputStream getResource(String path, String fileName) throws FileNotFoundException;
	
	//delete image
	boolean deleteImage(String directory,String imageName);
	
}
