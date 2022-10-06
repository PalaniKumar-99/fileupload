package com.example.demo.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class FileUploadController 
{
	@Autowired
	Environment env;
	//@Value("${file.upload.location}")
	private static String UPLOAD_FOLDER;
	
	@GetMapping("/")
	public String index()
	{
		return "upload";
	}
	
	@PostMapping("/upload")
	public String fileUpload(@RequestParam("file") MultipartFile file,RedirectAttributes redirect)
	{
		if(file.isEmpty())
		{
			redirect.addFlashAttribute("message","Please upload file");
			return "redirect:/uploadStatus";
		}
		try
		{
			UPLOAD_FOLDER = env.getProperty("file.upload.location");
			byte[] bytes= file.getBytes();
			Path path =Paths.get(UPLOAD_FOLDER+file.getOriginalFilename());
			Files.write(path, bytes);
			redirect.addFlashAttribute("message","you succesfully uploaded '"+file.getOriginalFilename()+"'");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/uploadStatus";
	}
	
	@GetMapping("/uploadStatus")
	public String uploadStatus()
	{
		return "uploadStatus";
	}
}
