package com.poorni.springboot.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.poorni.springboot.exception.FileNotFoundException;
import com.poorni.springboot.exception.FileStorageException;
import com.poorni.springboot.model.Doc;
import com.poorni.springboot.repository.DocRepository;

@Service
public class DocStorageService {

	@Autowired
	private DocRepository docRepository;

	public Doc saveFile(MultipartFile file) {
        String docName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if(docName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + docName);
            }

            Doc doc = new Doc(docName, file.getContentType(), file.getBytes());

            return docRepository.save(doc);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + docName + ". Please try again!", ex);
        }
    }


	
	public Doc getFile(String fileId) {
        return docRepository.findById(fileId)
        .orElseThrow(() -> new FileNotFoundException("File not found with id " + fileId));
	}

	public List<Doc> getFiles() {
		return docRepository.findAll();
	}

	public void deleteFile(String fileId) {
		docRepository.deleteById(fileId);
   }
}
