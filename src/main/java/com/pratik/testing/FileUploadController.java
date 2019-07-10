package com.pratik.testing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(path = "/fileUpload")
public class FileUploadController {

	@Autowired
	private FileService fileService;

	@RequestMapping( 
			method = RequestMethod.POST, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object uploadSize(MultipartFile file) throws Exception {
		return fileService.uploadFile(file.getBytes(), "hello.jpg");
	}
}
