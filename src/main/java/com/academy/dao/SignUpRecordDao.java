package com.academy.dao;

import com.academy.vo.StdntSignUpRecord;
import org.springframework.data.repository.CrudRepository;

public interface SignUpRecordDao extends CrudRepository<StdntSignUpRecord, Integer> {
}
