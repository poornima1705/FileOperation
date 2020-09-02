package com.poorni.springboot.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.poorni.springboot.model.Doc;
import com.poorni.springboot.service.DocStorageService;

@RestController
public class DocController {
	
	@Autowired
	private DocStorageService docStorageService;
	
	@GetMapping("/")
	public String get(Model model) {
		List<Doc> docs = docStorageService.getFiles();
		model.addAttribute("docs",docs);
		return "doc";
	}
	
	@PostMapping("/uploadFiles")
	public String uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
		for(MultipartFile file:files) {
			docStorageService.saveFile(file);
		}
		return "redirect:/";
	}
	
	@GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
		Doc doc = docStorageService.getFile(fileName);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(doc.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + doc.getFileName() + "\"")
                .body(new ByteArrayResource(doc.getData()));
    }
	
	 @DeleteMapping("/deleteFiles")
	    public ResponseEntity<String> deleteFile(@RequestParam(value= "fileName") final String keyName) {
		 docStorageService.deleteFile(keyName);
	        final String response = "[" + keyName + "] deleted successfully.";
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }

}
