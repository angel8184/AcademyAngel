package com.academy.controller;

import com.academy.model.*;
import com.academy.service.SignUpRecordService;
import com.academy.service.StudentService;
import com.academy.vo.StdntPaymentRecord;
import com.academy.vo.StdntSignUpRecord;
import com.academy.vo.StudentInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/academy01")
public class Academy01Controller {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    StudentService studentService;

    @Autowired
    SignUpRecordService signUpRecordService;

    @PostMapping("/01")
    public List<Academy0101Response> queryStudentData(@RequestBody Academy0101Request academy0101Request){

        List<Academy0101Response> academy0101ResponseList = new ArrayList<Academy0101Response>();

        try{
            logger.debug("queryStudentData_academy0101Request:Grade_{},Name_{}", academy0101Request.getGrade(), academy0101Request.getName());

            LocalDate currentdate = LocalDate.now();

            int currentMonth = currentdate.getMonth().getValue();

            //撈學生資料
            List<StudentInfo> studentInfoList = studentService.queryStudentData(academy0101Request.getGrade(), academy0101Request.getName());

            for(StudentInfo studentInfo : studentInfoList){

                Academy0101Response academy0101Response = new Academy0101Response();

                List<StdntSignUpRecord> stdntSignUpRecordList = signUpRecordService.queryStdntSignUpRecord(studentInfo.getStdntId());

                academy0101Response.setStdntId(String.valueOf(studentInfo.getStdntId()));
                academy0101Response.setName(studentInfo.getName());
                academy0101Response.setBirth(studentInfo.getBirth().toString());
                academy0101Response.setIdCard(studentInfo.getIdCard());
                academy0101Response.setParentName(studentInfo.getParentName());
                academy0101Response.setPhone(studentInfo.getPhone());
                academy0101Response.setNewNote(studentInfo.isNewNote() == true ? "1" : "0");
                academy0101Response.setLeaveNote(studentInfo.isLeaveNote() ==  true ? "1" : "0");
                academy0101Response.setNewDate(studentInfo.getNewDate() == null ? "" : studentInfo.getNewDate().toString());
                academy0101Response.setLeaveDate(studentInfo.getLeaveDate() == null ? "" : studentInfo.getLeaveDate().toString());
                academy0101Response.setGrade(String.valueOf(studentInfo.getGrade()));
                academy0101Response.setHandoutExemption(studentInfo.isHandoutExemption() == true ? "1" : "0");
                academy0101Response.setEngDiscount(studentInfo.isEngDiscount() == true ? "1" : "0");
                academy0101Response.setMathDiscount(studentInfo.isMathDiscount() == true ? "1" : "0");
                academy0101Response.setRemark(studentInfo.getRemark());
                academy0101Response.setLastPaymentDate(studentInfo.getLastPaymentDate() == null ? "" : studentInfo.getLastPaymentDate().toString());
                academy0101Response.setCourseFeeList(getCourseFeeList(stdntSignUpRecordList));

                academy0101ResponseList.add(academy0101Response);
            }
        }catch (Exception e){
            logger.debug("queryStudentData Error", e);
        }
        return academy0101ResponseList;
    }

    @PostMapping("/02")
    public String insertStudentData(@RequestBody Academy0102Request academy0102Request){
        try{
            logger.debug("insertStudentData");

            int stdntId = studentService.insertStudentInfo(academy0102Request);

            signUpRecordService.insertSignUpRecord(stdntId, academy0102Request.getCourseFeeList());

        }catch (Exception e){
            logger.debug("insertStudentData Error", e);
            return "M9999";
        }
        return "M0000";
    }

    @PostMapping("/03")
    public String updateStudentGrade(){

        String grade = "";
        String name = "";

        try{
            logger.debug("updateStudentGrade");

            //撈學生資料
            List<StudentInfo> studentInfoList = studentService.queryStudentData(grade, name);

            studentService.updateAllStudentGrade(studentInfoList);

        }catch (Exception e){
            logger.debug("updateStudentGrade Error", e);
            return "M9999";
        }
        return "M0000";
    }

    private List<Academy0101Response_courseFeeList> getCourseFeeList(List<StdntSignUpRecord> stdntSignUpRecordList){

        List<Academy0101Response_courseFeeList> courseFeeLists = new ArrayList<>();

        if(!stdntSignUpRecordList.isEmpty()){
            for(StdntSignUpRecord stdntSignUpRecord : stdntSignUpRecordList){

                Academy0101Response_courseFeeList courseFee = new Academy0101Response_courseFeeList();
                courseFee.setSignUpId(String.valueOf(stdntSignUpRecord.getId()));
                courseFee.setCourseFeeId(String.valueOf(stdntSignUpRecord.getRefCourseFeeId()));
                courseFee.setSignUpStartMonth(String.valueOf(stdntSignUpRecord.getSignUpStartMonth()));
                courseFee.setSignUpEndMonth(String.valueOf(stdntSignUpRecord.getSignUpEndMonth()));

                courseFeeLists.add(courseFee);
            }
        }
        return courseFeeLists;
    }

}
