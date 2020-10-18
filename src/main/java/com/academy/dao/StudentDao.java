package com.academy.dao;

import com.academy.vo.StudentInfo;
import org.springframework.data.repository.CrudRepository;

public interface StudentDao extends CrudRepository<StudentInfo, Integer> {
}
