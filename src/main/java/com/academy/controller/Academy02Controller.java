package com.academy.controller;

import com.academy.model.*;
import com.academy.service.CourseFeeService;
import com.academy.service.SignUpRecordService;
import com.academy.vo.CourseFeeInfo;
import com.academy.vo.StdntSignUpRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/academy02")
public class Academy02Controller {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CourseFeeService courseFeeService;

    @Autowired
    SignUpRecordService signUpRecordService;

    @PostMapping("/01")
    public List<Academy0201Response> queryCourseFee(){

        logger.debug("queryCourseFee");

        List<Academy0201Response> academy0201ResponseList = new ArrayList<>();

        try{
            List<CourseFeeInfo> courseFeeInfoList = courseFeeService.queryAllCourseFee();

            for(CourseFeeInfo courseFeeInfo : courseFeeInfoList){
                Academy0201Response academy0201Response = new Academy0201Response();

                academy0201Response.setCourseFeeId(String.valueOf(courseFeeInfo.getId()));
                academy0201Response.setCourseFeeName(courseFeeInfo.getName());

                academy0201ResponseList.add(academy0201Response);
            }
        }catch (Exception e){
            logger.debug("queryCourseFee Error", e);
        }

        return academy0201ResponseList;
    }

    @PostMapping("/02")
    public String inertOrDeleteCourseFee(@RequestBody Academy0202Request academy0202Request){

        logger.debug("inertOrDeleteCourseFee");

        try{
            if("I".equals(academy0202Request.getInsertOrDelete())){
                logger.debug("inertCourseFee:{}", academy0202Request.getCourseFeeName());
                courseFeeService.insertCourseFee(academy0202Request.getCourseFeeName());
            }else if("D".equals(academy0202Request.getInsertOrDelete())){
                logger.debug("DeleteCourseFee:{}", academy0202Request.getCourseFeeId());
                courseFeeService.deleteCourseFee(Integer.parseInt(academy0202Request.getCourseFeeId()));
            }
        }catch (Exception e){
            logger.debug("inertOrDeleteCourseFee Error", e);
            return "M9999";
        }
        return "M0000";
    }

    @PostMapping("/03")
    public List<Academy0203Response> queryStdntSighUpCourseFeeList(@RequestBody Academy0203Request academy0203Request){

        logger.debug("queryStdntSighUpCourseFeeList_studentId{}", academy0203Request.getStdntId());

        List<Academy0203Response> academy0203ResponseList = new ArrayList<>();

        try{
            List<StdntSignUpRecord> stdntSignUpRecordList = signUpRecordService.queryStdntSignUpRecord(
                    Integer.parseInt(academy0203Request.getStdntId()));

            for(StdntSignUpRecord stdntSignUpRecord : stdntSignUpRecordList){
                Academy0203Response academy0203Response = new Academy0203Response();

                academy0203Response.setCourseFeeId(String.valueOf(stdntSignUpRecord.getRefCourseFeeId()));

                academy0203ResponseList.add(academy0203Response);
            }
        }catch (Exception e){
            logger.debug("queryStdntSighUpCourseFeeList Error", e);
        }
        return academy0203ResponseList;
    }
}
