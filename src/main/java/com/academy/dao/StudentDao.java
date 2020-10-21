package com.academy.dao;

import com.academy.vo.StudentInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentDao extends CrudRepository<StudentInfo, Integer> {

    public StudentInfo findByGradeAndName(int grade, String name);
}
