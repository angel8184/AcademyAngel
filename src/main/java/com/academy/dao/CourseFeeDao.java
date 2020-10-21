package com.academy.dao;

import com.academy.vo.CourseFeeInfo;
import org.springframework.data.repository.CrudRepository;

public interface CourseFeeDao extends CrudRepository<CourseFeeInfo, Integer> {
}
