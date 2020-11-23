package com.academy.controller;

import com.academy.model.*;
import com.academy.service.CourseFeeService;
import com.academy.service.SignUpRecordService;
import com.academy.vo.CourseFeeInfo;
import com.academy.vo.StdntSignUpRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/academy02")
public class Academy02Controller {

    @Autowired
    CourseFeeService courseFeeService;

    @Autowired
    SignUpRecordService signUpRecordService;

    @PostMapping("/01")
    public List<Academy0201Response> queryCourseFee(){
        List<Academy0201Response> academy0201ResponseList = new ArrayList<>();

        List<CourseFeeInfo> courseFeeInfoList = courseFeeService.queryAllCourseFee();

        for(CourseFeeInfo courseFeeInfo : courseFeeInfoList){
            Academy0201Response academy0201Response = new Academy0201Response();

            academy0201Response.setCourseFeeId(String.valueOf(courseFeeInfo.getId()));
            academy0201Response.setCourseFeeName(courseFeeInfo.getName());

            academy0201ResponseList.add(academy0201Response);
        }

        return academy0201ResponseList;
    }

    @PostMapping("/02")
    public String queryStudentData(@RequestBody Academy0202Request academy0202Request){

        if("I".equals(academy0202Request.getInsertOrDelete())){
            courseFeeService.insertCourseFee(academy0202Request.getCourseFeeName());
        }else if("D".equals(academy0202Request.getInsertOrDelete())){
            courseFeeService.deleteCourseFee(Integer.parseInt(academy0202Request.getCourseFeeId()));
        }

        return "success";
    }

    @PostMapping("/03")
    public List<Academy0203Response> queryStdntSighUpCourseFeeList(@RequestBody Academy0203Request academy0203Request){

        List<Academy0203Response> academy0203ResponseList = new ArrayList<>();

        List<StdntSignUpRecord> stdntSignUpRecordList = signUpRecordService.queryStdntSignUpRecord(
                Integer.parseInt(academy0203Request.getStdntId()));

        for(StdntSignUpRecord stdntSignUpRecord : stdntSignUpRecordList){
            Academy0203Response academy0203Response = new Academy0203Response();

            academy0203Response.setCourseFeeId(String.valueOf(stdntSignUpRecord.getRefCourseFeeId()));

            academy0203ResponseList.add(academy0203Response);
        }

        return academy0203ResponseList;
    }
}
