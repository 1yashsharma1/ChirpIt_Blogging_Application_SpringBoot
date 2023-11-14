package com.blog.app.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blog.app.services.FileService;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile multipartFile) throws IOException {

		// Get file name
		String name = multipartFile.getOriginalFilename();

		// Generate Random name for file
		String randomId = UUID.randomUUID().toString();
		String randomFileName = randomId.concat(name.substring(name.lastIndexOf(".")));

		// FullPath of file
		String fullFilePath = path + File.separator + randomFileName;

		// Checking if folder is created or not
		File f = new File(path);
		if (!f.exists())
			f.mkdir();

		// Copying file contents to new place
		Files.copy(multipartFile.getInputStream(), Paths.get(fullFilePath));

		return randomFileName;
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {

		String fullFilePath = path + File.separator + fileName;
		InputStream is = new FileInputStream(fullFilePath);

		return is;
	}

}
