package com.upload;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.upload.file.FileContentRepository;
import com.upload.file.FileObject;
import com.upload.file.FileRepository;

@RestController
public class FileContentController {

	@Autowired private FileRepository filesRepo;
	@Autowired private FileContentRepository contentsRepo;
	
	@RequestMapping(value="/files/{fileId}", method = RequestMethod.PUT, headers="content-type!=application/hal+json")
	public ResponseEntity<?> setContent(@PathVariable("fileId") Long id, @RequestParam("file") MultipartFile file) 
			throws IOException {

		FileObject f = filesRepo.findOne(id);
		f.setMimeType(file.getContentType());
		
		contentsRepo.setContent(f, file.getInputStream());
		
		filesRepo.save(f);
			
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

	@RequestMapping(value="/files/{fileId}", method = RequestMethod.GET, headers="accept!=application/hal+json")
	public ResponseEntity<?> getContent(@PathVariable("fileId") Long id) {

		FileObject fo = filesRepo.findOne(id);
		InputStreamResource ir = new InputStreamResource(contentsRepo.getContent(fo));
		HttpHeaders headers = new HttpHeaders();		
		headers.set("Content-Type",fo.getMimeType());
		return new ResponseEntity<Object>(ir, headers, HttpStatus.OK);
	}
}