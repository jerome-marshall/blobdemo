package com.zero.blobdemo.service;

import com.zero.blobdemo.model.Doc;
import com.zero.blobdemo.repository.DocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class DocStorageService {
    @Autowired
    private DocRepository docRepository;

    public void saveFile(MultipartFile file) {
        String docName = file.getOriginalFilename();
        try {
            Doc doc = new Doc(docName, file.getContentType(), file.getBytes());
            docRepository.save(doc);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Optional<Doc> getFile(Integer field){
        return docRepository.findById(field);
    }

    public List<Doc> getFiles(){
        return docRepository.findAll();
    }
}
