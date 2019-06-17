package io.udaptor.core.controllers;

import io.udaptor.core.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class TestController {

    @Autowired
    private FileService fileUploadService;

    @PostMapping("/testFileUpload")
    public void uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        fileUploadService.upload(file);
    }


    @GetMapping("/")
    public ResponseEntity<?> healthCheck() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
