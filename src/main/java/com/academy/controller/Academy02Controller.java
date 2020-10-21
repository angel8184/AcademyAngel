package com.academy.controller;

import com.academy.model.Academy0201Response;
import com.academy.model.Academy0202Request;
import com.academy.service.CourseFeeService;
import com.academy.vo.CourseFeeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/academy02")
public class Academy02Controller {

    @Autowired
    CourseFeeService courseFeeService;

    @PostMapping("/01")
    public List<Academy0201Response> queryStudentData(){
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
}
