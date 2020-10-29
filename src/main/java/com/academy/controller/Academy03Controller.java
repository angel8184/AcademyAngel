package com.academy.controller;

import com.academy.model.*;
import com.academy.service.CourseFeeService;
import com.academy.service.PaymentRecordService;
import com.academy.service.StudentService;
import com.academy.vo.StdntPaymentRecord;
import com.academy.vo.StdntPaymentRecordMain;
import com.academy.vo.StudentInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/academy03")
public class Academy03Controller {

    @Autowired
    StudentService studentService;
    @Autowired
    CourseFeeService courseFeeService;
    @Autowired
    PaymentRecordService paymentRecordService;

    @PostMapping("/01")
    public Academy0301Response queryPaymentData(@RequestBody Academy0301Request academy0301Request){

        Academy0301Response academy0301Response = new Academy0301Response();

        //use Name to find Student data.
        StudentInfo studentInfo = studentService.findbyName(academy0301Request.getName());

        //use stdntId and paymonth to find payment_Main.
        StdntPaymentRecordMain stdntPaymentRecordMain = paymentRecordService.
                queryPaymentRecordMainByStdntIdAndPaymentMonth(studentInfo.getStdntId(),
                        Integer.parseInt(academy0301Request.getPaymentMonth()));

        //use main.Id to find courseList.
        List<StdntPaymentRecord> stdntPaymentRecordList = paymentRecordService.queryPaymentRecordByMainId(stdntPaymentRecordMain.getId());

        academy0301Response.setStdntId(String.valueOf(studentInfo.getStdntId()));
        academy0301Response.setStdntName(studentInfo.getName());
        academy0301Response.setGrade(studentService.getGradeName(studentInfo.getGrade()));
        academy0301Response.setPayMainId(String.valueOf(stdntPaymentRecordMain.getId()));
        academy0301Response.setPaymentMonth(String.valueOf(stdntPaymentRecordMain.getPaymentMonth()));
        academy0301Response.setCourseFeeList(getPaymentCourseList(stdntPaymentRecordList));
        academy0301Response.setPaymentCrDate(stdntPaymentRecordMain.getCreateDate().toString());
        academy0301Response.setPayDate(stdntPaymentRecordMain.getPayDate() == null ? "" : stdntPaymentRecordMain.getPayDate().toString());

        return academy0301Response;
    }


    @PostMapping("/02")
    public List<Academy0302Response> queryStudentInfo(@RequestBody Academy0302Request academy0302Request){

        List<Academy0302Response> academy0302ResponseList = new ArrayList<>();

        List<StudentInfo> studentInfoList = studentService.queryStudentData(academy0302Request.getGrade()
                , academy0302Request.getName());

        for(StudentInfo studentInfo : studentInfoList){
            Academy0302Response academy0302Response = new Academy0302Response();

            academy0302Response.setStdntId(String.valueOf(studentInfo.getStdntId()));
            academy0302Response.setStdntName(studentInfo.getName());
            academy0302Response.setGrade(studentService.getGradeName(studentInfo.getGrade()));
            academy0302Response.setLastPaymentDate(studentInfo.getLastPaymentDate() == null ? "" : studentInfo.getLastPaymentDate().toString());

            academy0302ResponseList.add(academy0302Response);
        }

        return academy0302ResponseList;
    }

    @PostMapping("/03")
    public Academy0303Response queryStdntPaymentRecordLastMonth(@RequestBody Academy0303Request academy0303Request){

        Academy0303Response academy0303Response = new Academy0303Response();

        StdntPaymentRecordMain stdntPaymentRecordMain = paymentRecordService.queryPaymentRecordMainByStdntIdAndGrade(
                Integer.parseInt(academy0303Request.getStdntId()), Integer.parseInt(academy0303Request.getGrade()));

        List<StdntPaymentRecord> stdntPaymentRecordList = paymentRecordService.queryPaymentRecordByMainId(stdntPaymentRecordMain.getId());

        academy0303Response.setCourseFeeList(getPaymentCourseList(stdntPaymentRecordList));
        academy0303Response.setPaymentMonth(String.valueOf(stdntPaymentRecordMain.getPaymentMonth()));

        return academy0303Response;
    }

    @PostMapping("/04")
    public String insertStdntPaymentRecord(@RequestBody Academy0304Request academy0304Request){

        int mainId = paymentRecordService.insertPaymentRecordMain(academy0304Request);

        paymentRecordService.interPaymentRecordCourseFee(mainId, academy0304Request.getCourseFeeList());

        return "success";
    }

    @PostMapping("/05")
    public String updatePayDate(@RequestBody Academy0305Request academy0305Request){

        StdntPaymentRecordMain stdntPaymentRecordMain = paymentRecordService.queryPaymentRecordMainById(Integer.parseInt(academy0305Request.getPayMainId()));

        paymentRecordService.updatePayDate(stdntPaymentRecordMain);

        StudentInfo studentInfo = studentService.findbyStdntIdAndGrade(stdntPaymentRecordMain.getRefStdntId(), stdntPaymentRecordMain.getGrade());

        studentService.updateLastPayDate(studentInfo);

        return "success";
    }

    private List<Academy0301Response_courseFeeList> getPaymentCourseList(List<StdntPaymentRecord> stdntPaymentRecordList){

        List<Academy0301Response_courseFeeList> courseFeeLists = new ArrayList<>();

        if(!stdntPaymentRecordList.isEmpty()){
            for(StdntPaymentRecord stdntPaymentRecord : stdntPaymentRecordList){

                Academy0301Response_courseFeeList courseFee = new Academy0301Response_courseFeeList();

                courseFee.setPayRecordId(String.valueOf(stdntPaymentRecord.getId()));
                courseFee.setCourseFeeId(String.valueOf(stdntPaymentRecord.getRefCourseFeeId()));
                courseFee.setRemark(stdntPaymentRecord.getRemark());
                courseFee.setExpense(String.valueOf(stdntPaymentRecord.getExpense()));
                courseFee.setExpenseMonthStart(String.valueOf(stdntPaymentRecord.getExpenseMonthStart()));
                courseFee.setExpenseMonthEnd(String.valueOf(stdntPaymentRecord.getExpenseMonthEnd()));

                courseFeeLists.add(courseFee);
            }
        }
        return courseFeeLists;
    }
}
