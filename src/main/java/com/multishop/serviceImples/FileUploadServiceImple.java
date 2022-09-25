package com.multishop.serviceImples;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.multishop.MultishopApplication;
import com.multishop.services.FileUploadService;

import lombok.Data;

@Service
@Data
public class FileUploadServiceImple implements FileUploadService {
	

	// uploading file to server
	@Override
	public String saveFile(String path, MultipartFile file) throws IOException {
		
		//generate random name
		String randomString = UUID.randomUUID().toString();
		String fileName = randomString.concat(file.getOriginalFilename());
		
		ApplicationHome applicationHome = new ApplicationHome(MultishopApplication.class);
		String homePath = applicationHome.getDir().getAbsolutePath();
		String fullpath = homePath+File.separator+"images";
		
		//if images folder not exist
		File f = new File(fullpath);
		if(!f.exists()) {
			f.mkdir();
		}
		
		File f2 = new File(fullpath+File.separator+path);
		if(!f2.exists()) {
			f2.mkdir();
		}
		
		Path finalPath = Paths.get(fullpath+File.separator+path+File.separator+fileName);
		System.out.println(homePath);
		
		// save file to the final path
		Files.copy(file.getInputStream(), finalPath,StandardCopyOption.REPLACE_EXISTING);

		return fileName;
	}

	@SuppressWarnings("resource")
	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		FileInputStream is = new FileInputStream(path+File.separator+fileName);
		return is;
	}

	@Override
	public boolean deleteImage(String directory, String imageName) {
		String url = new ApplicationHome(MultishopApplication.class).getDir().getAbsolutePath()+File.separator+ "images"+File.separator+directory+File.separator+imageName;
		Path path = Paths.get(url);
		try {
			Files.delete(path);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	

}
