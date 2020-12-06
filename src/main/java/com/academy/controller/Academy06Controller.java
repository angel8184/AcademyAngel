package com.academy.controller;

import com.academy.model.Academy0601Request;
import com.academy.model.Academy0601Response;
import com.academy.service.StudentService;
import com.academy.vo.StudentInfo;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/academy06")
public class Academy06Controller {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    StudentService studentService;

    @PostMapping("/01")
    public void exportStudentInfoExcel(@RequestBody Academy0601Request academy0601Request, HttpServletResponse response) {

        logger.debug("exportStudentInfoExcel_Grade:{}", academy0601Request.getGrade());

        List<StudentInfo> studentInfoList = studentService.queryStudentData(academy0601Request.getGrade(), "");

        List<Academy0601Response> academy0601ResponseList = studentService.fetchStudentInfoToResponse(studentInfoList);

        try (InputStream is = this.getClass().getClassLoader().getResourceAsStream("templates/studentInfo.xlsx")) {
            //設置檔頭資訊 編碼
            String fileName = URLEncoder.encode("學生資料總表", "UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + ".xlsx");
            response.setContentType("application/vnd.ms-excel;charset=utf8");

            OutputStream os = response.getOutputStream();
            Context context = new Context();
            context.putVar("studentInfoList", academy0601ResponseList); //名稱對應excel的items
            JxlsHelper.getInstance().setEvaluateFormulas(true).processTemplate(is, os, context);
            os.flush();
            os.close();

        } catch (IOException e) {
            logger.debug("exportStudentInfoExcel Error", e);
        }
    }
}
