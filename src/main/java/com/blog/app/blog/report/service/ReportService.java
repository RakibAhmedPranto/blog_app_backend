package com.blog.app.blog.report.service;

import com.blog.app.blog.user.entity.User;
import com.blog.app.blog.user.repository.UserRepo;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {
    @Autowired
    private UserRepo userRepo;

    public byte[] exportReport(String reportFormat) throws FileNotFoundException, JRException {
        String path = "C:\\Users\\Rakib\\Downloads\\report";
        List<User> users = userRepo.findAll();

        File file = ResourceUtils.getFile("classpath:users.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(users);
        Map<String,Object> parameters = new HashMap<>();
        parameters.put("createdBy","Rakib Ahmed");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameters, dataSource);
        byte[] data = JasperExportManager.exportReportToPdf(jasperPrint);
//        if (reportFormat.equalsIgnoreCase("html")) {
//            JasperExportManager.exportReportToHtmlFile(jasperPrint, path + "\\employees.html");
//        }
//        if (reportFormat.equalsIgnoreCase("pdf")) {
//            JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\employees.pdf");
//        }
        return data;
    }
}
