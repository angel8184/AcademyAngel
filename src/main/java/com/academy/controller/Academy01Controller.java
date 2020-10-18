package com.academy.controller;

import com.academy.model.Academy0101Request;
import com.academy.model.Academy0101Response;
import com.academy.model.Academy0102Request;
import com.academy.service.SignUpRecordService;
import com.academy.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/academy01")
public class Academy01Controller {

    @Autowired
    StudentService studentService;

    @Autowired
    SignUpRecordService signUpRecordService;

//    @PostMapping("/01")
//    public Academy0101Response queryStudentData(@RequestBody Academy0101Request academy0101Request){
//
//
//
//        return "success";
//    }

    @PostMapping("/02")
    public String insertStudentData(@RequestBody Academy0102Request academy0102Request){

        int stdntId = studentService.insertStudentInfo(academy0102Request);

        signUpRecordService.insertSignUpRecord(stdntId, academy0102Request.getCourseList());

        return "success";
    }



}
