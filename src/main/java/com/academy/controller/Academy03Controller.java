package com.academy.controller;

import com.academy.model.*;
import com.academy.service.CourseFeeService;
import com.academy.service.PaymentRecordService;
import com.academy.service.StudentService;
import com.academy.vo.StdntPaymentRecord;
import com.academy.vo.StdntPaymentRecordMain;
import com.academy.vo.StudentInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/academy03")
public class Academy03Controller {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    StudentService studentService;
    @Autowired
    CourseFeeService courseFeeService;
    @Autowired
    PaymentRecordService paymentRecordService;

    @PostMapping("/01")
    public List<Academy0301Response> queryPaymentData(@RequestBody Academy0301Request academy0301Request){

        logger.debug("queryPaymentData_Name:{}, Grade：{}, paymentYear：{}, paymentMonth:{}",
                academy0301Request.getName(), academy0301Request.getGrade(), academy0301Request.getPaymentYear(), academy0301Request.getPaymentMonth());

        List<Academy0301Response> academy0301ResponseList = new ArrayList<>();

        try {
            academy0301ResponseList = paymentRecordService.queryStudentPaymentRecord(academy0301Request);

        }catch (Exception e){
            logger.debug("queryPaymentData Error", e);
        }
        return academy0301ResponseList;
    }


    @PostMapping("/02")
    public List<Academy0302Response> queryStudentInfo(@RequestBody Academy0302Request academy0302Request){

        logger.debug("queryStudentInfo_Name:{}, Grade:{}", academy0302Request.getName(), academy0302Request.getGrade());

        List<Academy0302Response> academy0302ResponseList = new ArrayList<>();

        try{
            List<StudentInfo> studentInfoList = studentService.queryStudentData(academy0302Request.getGrade()
                    , academy0302Request.getName());

            for(StudentInfo studentInfo : studentInfoList){
                if(!studentInfo.isLeaveNote()){
                    Academy0302Response academy0302Response = new Academy0302Response();

                    academy0302Response.setStdntId(String.valueOf(studentInfo.getStdntId()));
                    academy0302Response.setStdntName(studentInfo.getName());
                    academy0302Response.setGrade(studentService.getGradeName(studentInfo.getGrade()));
                    academy0302Response.setLastPaymentDate(studentInfo.getLastPaymentDate() == null ? "" : studentInfo.getLastPaymentDate().toString().substring(0, 10));

                    academy0302ResponseList.add(academy0302Response);
                }
            }
        }catch (Exception e) {
            logger.debug("queryStudentInfo Error", e);
        }
        return academy0302ResponseList;
    }

    @PostMapping("/03")
    public Academy0303Response queryStdntPaymentRecordLastMonth(@RequestBody Academy0303Request academy0303Request){

        logger.debug("queryStdntPaymentRecordLastMonth_StdntId:{}, Grade:{}", academy0303Request.getStdntId(), academy0303Request.getGrade());

        Academy0303Response academy0303Response = new Academy0303Response();

        try{
            StdntPaymentRecordMain stdntPaymentRecordMain = paymentRecordService.queryPaymentRecordMainByStdntIdAndGrade(
                    Integer.parseInt(academy0303Request.getStdntId()), Integer.parseInt(academy0303Request.getGrade()));

            List<StdntPaymentRecord> stdntPaymentRecordList = paymentRecordService.queryPaymentRecordByMainId(stdntPaymentRecordMain.getId());

            academy0303Response.setCourseFeeList(paymentRecordService.getPaymentCourseList(stdntPaymentRecordList));
            academy0303Response.setPaymentMonth(String.valueOf(stdntPaymentRecordMain.getPaymentMonth()));
        }catch (Exception e){
            logger.debug("queryStdntPaymentRecordLastMonth Error", e);
        }
        return academy0303Response;
    }

    @PostMapping("/04")
    public String insertStdntPaymentRecord(@RequestBody Academy0304Request academy0304Request){

        logger.debug("insertStdntPaymentRecord");

        try{
            int mainId = paymentRecordService.insertPaymentRecordMain(academy0304Request);

            paymentRecordService.interPaymentRecordCourseFee(mainId, academy0304Request.getCourseFeeList());

            return "success";
        }catch (Exception e){
            logger.debug("insertStdntPaymentRecord Error", e);
            return "M9999";
        }
    }

    @PostMapping("/05")
    public String updatePayDateAndRecevingUnit(@RequestBody Academy0305Request academy0305Request){

        logger.debug("updatePayDateAndRecevingUnit_PayMainId:{}, RecevingUnit:{}", academy0305Request.getPayMainId(), academy0305Request.getReceivingUnit());

        try{
            StdntPaymentRecordMain stdntPaymentRecordMain = paymentRecordService.queryPaymentRecordMainById(Integer.parseInt(academy0305Request.getPayMainId()));

            paymentRecordService.updatePayDateAndReceivingUnit(stdntPaymentRecordMain, academy0305Request.getReceivingUnit());

            StudentInfo studentInfo = studentService.findbyStdntIdAndGrade(stdntPaymentRecordMain.getRefStdntId(), stdntPaymentRecordMain.getGrade());

            studentService.updateLastPayDate(studentInfo);

            return "success";
        }catch (Exception e){
            logger.debug("updatePayDateAndRecevingUnit Error", e);
            return "M9999";
        }
    }

    @PostMapping("/06")
    public List<Academy0306Response> queryPaymentForPrintReceipt(@RequestBody Academy0306Request academy0306Request){

        logger.debug("queryPaymentForPrintReceipt_Name:{}, Grade:{}", academy0306Request.getName(), academy0306Request.getGrade());

        List<Academy0306Response> academy0306ResponseList = new ArrayList<>();

        try{
            academy0306ResponseList = studentService.queryPaymentForPrintReceipt(academy0306Request);

        }catch (Exception e){
            logger.debug("queryPaymentForPrintReceipt Error", e);
        }
        return academy0306ResponseList;
    }
}
