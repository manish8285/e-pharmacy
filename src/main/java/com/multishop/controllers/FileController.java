package com.multishop.controllers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.multishop.MultishopApplication;
import com.multishop.services.FileUploadService;

@RestController
@RequestMapping("/api/v1")
public class FileController {
	
	
	@Autowired
	private FileUploadService fileUploadService;

	@GetMapping(value="/images/{imageType}/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(
			@PathVariable("imageName") String imageName,
			@PathVariable("imageType") String directory,
			HttpServletResponse response) throws IOException {
		String homepath = new ApplicationHome(MultishopApplication.class).getDir().getAbsolutePath();
		String path = homepath+File.separator+"images"+File.separator+directory; 
		System.out.println("path = "+path);
		InputStream stream = this.fileUploadService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(stream, response.getOutputStream());
	}
}
