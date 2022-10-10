package com.blog.app.blog.report.controller;

import com.blog.app.blog.report.service.ReportService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("/api/v1/report")
public class ReportController {
    @Autowired
    private ReportService reportService;

    @GetMapping("/{format}")
    public ResponseEntity<byte[]> generateReport(@PathVariable String format) throws JRException, FileNotFoundException {
        byte[] data = reportService.exportReport(format);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=users.pdf");
        return ResponseEntity.ok().headers(httpHeaders).contentType(MediaType.APPLICATION_PDF).body(data);
    }
}
