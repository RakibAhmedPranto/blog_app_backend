package com.blog.app.blog.ExcelFileUpload;

import com.blog.app.blog.user.entity.User;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;

public class ExcelHelper {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public static boolean checkExcelFormat(MultipartFile file) {
        String contentType = file.getContentType();
        if (contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
            return true;
        } else {
            return false;
        }
    }

    public static Set<User> convertExcelToUsers(InputStream is) {
        Set<User> users = new HashSet<>();
        Set<String> emailList = new HashSet<>();

        try {
            XSSFWorkbook sheets = new XSSFWorkbook(is);
            XSSFSheet sheet = sheets.getSheet("data");
            int rowNumber = 0;

            Iterator<Row> iterator = sheet.iterator();

            while (iterator.hasNext()) {
                boolean isDuplicate = false;

                Row row = iterator.next();
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cells = row.iterator();
                int cid = 0;
                User user = new User();
                while (cells.hasNext()) {
                    Cell cell = cells.next();

                    switch (cid) {
                        case 0:
                            user.setName(cell.getStringCellValue());
                            break;
                        case 1:
                            user.setEmail(cell.getStringCellValue());
                            if (emailList.contains(cell.getStringCellValue())) {
                                isDuplicate = true;
                            }
                            emailList.add(cell.getStringCellValue());
                            break;
                        case 2:
                            user.setPassword(cell.getStringCellValue());
                            break;
                        case 3:
                            user.setAbout(cell.getStringCellValue());
                            break;
                        default:
                            break;
                    }
                    cid++;
                }
                if (!isDuplicate) {
                    users.add(user);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }
}
