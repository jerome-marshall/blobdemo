package com.zero.blobdemo.controller;

import com.zero.blobdemo.model.Doc;
import com.zero.blobdemo.service.DocStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class DocController {

    @Autowired
    private DocStorageService docStorageService;

    @GetMapping("/")
    public String get(Model model){
        List<Doc> docs = docStorageService.getFiles();
        model.addAttribute("docs", docs);
        return "doc";
    }

    @PostMapping("/uploadFiles")
    public String uploadMultipleFiles(@RequestParam("files")MultipartFile[] files) {
        for (MultipartFile file : files)
            docStorageService.saveFile(file);
        return "redirect:/";
    }

    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable Integer fileId) {
        Doc doc = docStorageService.getFile(fileId).get();
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(doc.getDocType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment:filename=\"" + doc.getDocName() + "\"")
                .body(new ByteArrayResource(doc.getData()));
    }
}
