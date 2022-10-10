package com.blog.app.blog.file.controller;

import com.blog.app.blog.file.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api/v1/file/")
public class ServeFileController {
    @Autowired
    private FileService fileService;
    @Value("${project.image}")
    private String path;

    @GetMapping(value = "/{fileName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadFile(
            @PathVariable("fileName") String fileName,
            HttpServletResponse response
    ) throws IOException {
        InputStream resource = this.fileService.getResource(path,fileName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }
}
