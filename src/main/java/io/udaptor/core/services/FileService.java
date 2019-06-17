package io.udaptor.core.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public interface FileService {

    String upload(MultipartFile file) throws IOException;
    String upload(File file) throws IOException;


    File download(String url) throws URISyntaxException, IOException;

}
