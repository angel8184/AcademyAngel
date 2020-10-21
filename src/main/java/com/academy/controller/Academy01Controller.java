package com.academy.controller;

import com.academy.model.*;
import com.academy.service.PaymentRecordService;
import com.academy.service.SignUpRecordService;
import com.academy.service.StudentService;
import com.academy.vo.StdntPaymentRecord;
import com.academy.vo.StdntSignUpRecord;
import com.academy.vo.StudentInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/academy01")
public class Academy01Controller {

    @Autowired
    StudentService studentService;

    @Autowired
    SignUpRecordService signUpRecordService;

    @Autowired
    PaymentRecordService paymentRecordService;

    @PostMapping("/01")
    public Academy0101Response queryStudentData(@RequestBody Academy0101Request academy0101Request){

        LocalDate currentdate = LocalDate.now();

        int currentMonth = currentdate.getMonth().getValue();

        Academy0101Response academy0101Response = new Academy0101Response();

        //撈學生資料
        StudentInfo studentInfo = studentService.queryStudentData(academy0101Request);

        //撈課程資料
        List<StdntSignUpRecord> stdntSignUpRecordList = signUpRecordService.queryStdntSignUpRecord(studentInfo.getStdntId());

        //課程費用資料

        List<StdntPaymentRecord> stdntPaymentRecordList = paymentRecordService.queryPaymentRecord(studentInfo.getStdntId());

        academy0101Response.setStdntId(String.valueOf(studentInfo.getStdntId()));
        academy0101Response.setName(studentInfo.getName());
        academy0101Response.setBirth(studentInfo.getBirth().toString());
        academy0101Response.setIdCard(studentInfo.getIdCard());
        academy0101Response.setParentName(studentInfo.getParentName());
        academy0101Response.setPhone(String.valueOf(studentInfo.getPhone()));
        academy0101Response.setNewNote(String.valueOf(studentInfo.isNewNote()));
        academy0101Response.setLeaveNote(String.valueOf(studentInfo.isLeaveNote()));
        academy0101Response.setNewDate(studentInfo.getNewDate().toString());
        academy0101Response.setLeaveDate(studentInfo.getLeaveDate().toString());
        academy0101Response.setGrade(String.valueOf(studentInfo.getGrade()));
        academy0101Response.setHandoutExemption(String.valueOf(studentInfo.isHandoutExemption()));
        academy0101Response.setEngDiscount(String.valueOf(studentInfo.isEngDiscount()));
        academy0101Response.setMathDiscount(String.valueOf(studentInfo.isMathDiscount()));
        academy0101Response.setRemark(studentInfo.getRemark());
        academy0101Response.setLastPaymentDate(studentInfo.getLastPaymentDate().toString());
        academy0101Response.setCourseFeeList(getCourseFeeList(stdntSignUpRecordList));
//        academy0101Response.setPaymentList(getPaymentList(stdntPaymentRecordList));

        return academy0101Response;
    }

    @PostMapping("/02")
    public String insertStudentData(@RequestBody Academy0102Request academy0102Request){

        int stdntId = studentService.insertStudentInfo(academy0102Request);

        signUpRecordService.insertSignUpRecord(stdntId, academy0102Request.getCourseList());

        return "success";
    }

    private List<Academy0101Response_courseFeeList> getCourseFeeList(List<StdntSignUpRecord> stdntSignUpRecordList){

        List<Academy0101Response_courseFeeList> courseFeeLists = new ArrayList<>();

        if(!stdntSignUpRecordList.isEmpty()){
            for(StdntSignUpRecord stdntSignUpRecord : stdntSignUpRecordList){

                Academy0101Response_courseFeeList courseFee = new Academy0101Response_courseFeeList();
                courseFee.setCourseFeeId(String.valueOf(stdntSignUpRecord.getRefCourseFeeId()));

                courseFeeLists.add(courseFee);
            }
        }
        return courseFeeLists;
    }

    private List<Academy0101Response_paymentList> getPaymentList(List<StdntPaymentRecord> stdntPaymentRecordList){

        List<Academy0101Response_paymentList> paymentLists = new ArrayList<>();

        return paymentLists;
    }




}
