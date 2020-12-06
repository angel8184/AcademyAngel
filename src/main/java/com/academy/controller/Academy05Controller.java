package com.academy.controller;

import com.academy.model.Academy0501Request;
import com.academy.model.Academy0601Request;
import com.academy.model.Academy0601Response;
import com.academy.service.CourseFeeService;
import com.academy.service.StudentService;
import com.academy.vo.CourseFeeInfo;
import com.academy.vo.StudentInfo;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
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

    @Autowired
    StudentService studentService;
    @Autowired
    CourseFeeService courseFeeService;

    @GetMapping("/01")
    public void exportTotalSummaryExcel(HttpServletResponse response) {

        List<CourseFeeInfo> courseFeeInfoList = courseFeeService.queryAllCourseFee();
        List<String> courseFeeNameList = new ArrayList<>();

        courseFeeNameList.add("學生姓名");
        courseFeeNameList.add("年級");
        courseFeeNameList.add("生日");
        courseFeeNameList.add("身分證字號");
        courseFeeNameList.add("家長姓名");
        courseFeeNameList.add("電話");
        courseFeeNameList.add("課程明細");


        for (CourseFeeInfo courseFeeInfo : courseFeeInfoList){
            String courseFeeName = courseFeeInfo.getName();
            courseFeeNameList.add(courseFeeName);
        }

        courseFeeNameList.add("繳費合計金額");
        courseFeeNameList.add("收費日期");
        courseFeeNameList.add("收款單位");


        try (InputStream is = this.getClass().getClassLoader().getResourceAsStream("templates/totalSummary.xlsx")) {
            //設置檔頭資訊 編碼
            String fileName = URLEncoder.encode("合計總表", "UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + ".xlsx");
            response.setContentType("application/vnd.ms-excel;charset=utf8");

            OutputStream os = response.getOutputStream();
            Context context = new Context();
            context.putVar("headers", courseFeeNameList); //名稱對應excel的items
            context.putVar("dataList", "");
            JxlsHelper.getInstance().setEvaluateFormulas(true).processTemplate(is, os, context);
//            JxlsHelper.getInstance().processGridTemplateAtCell(is, os, context,"name,jun,feb,mar,apr,depTotal","Sheet1!A1");
            os.flush();
            os.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
