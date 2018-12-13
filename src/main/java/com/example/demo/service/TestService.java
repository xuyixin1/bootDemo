package com.example.demo.service;

import com.example.demo.dao.TestMapper;
import com.example.demo.domain.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestService {
    @Autowired
    private TestMapper testMapper;
    public Info find(){
       return  testMapper.find();
    }

    @Transactional(rollbackFor = Exception.class)
    public void  insert() throws Exception {
        testMapper.insert();
        throw new Exception("sqlException");
    }
}
