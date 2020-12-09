package com.academy.controller;

import com.academy.model.Academy0501Request;
import com.academy.model.Academy0601Request;
import com.academy.model.Academy0601Response;
import com.academy.service.CourseFeeService;
import com.academy.service.StudentService;
import com.academy.service.TotalSummaryService;
import com.academy.vo.CourseFeeInfo;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/academy05")
public class Academy05Controller {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    StudentService studentService;
    @Autowired
    CourseFeeService courseFeeService;
    @Autowired
    TotalSummaryService totalSummaryService;

    @RequestMapping(value = "/01", method = { RequestMethod.GET, RequestMethod.POST })
    public void exportTotalSummaryExcel(@RequestBody(required = false) Academy0501Request academy0501Request,HttpServletResponse response) {

        logger.debug("exportTotalSummaryExcel_Grade:{}，Month:{}", academy0501Request.getGrade(), academy0501Request.getMonth());

        //組出HEADERS
        List<CourseFeeInfo> courseFeeInfoList = courseFeeService.queryAllCourseFee();
        List<String> courseFeeNameList = totalSummaryService.getExcelHeader(courseFeeInfoList);

        //組出dataList
        List<List<Object>> studentPayRecordList = totalSummaryService.getStudentPayRecordList(academy0501Request, courseFeeInfoList);

        try (InputStream is = this.getClass().getClassLoader().getResourceAsStream("templates/totalSummary.xlsx")) {
            //設置檔頭資訊 編碼
            String fileName = URLEncoder.encode("合計總表", "UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + ".xlsx");
            response.setContentType("application/vnd.ms-excel;charset=utf8");

            OutputStream os = response.getOutputStream();
            Context context = new Context();
            context.putVar("headers", courseFeeNameList); //名稱對應excel的items
            context.putVar("dataList", studentPayRecordList);
            JxlsHelper.getInstance().setEvaluateFormulas(true).processTemplate(is, os, context);
            os.flush();
            os.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
