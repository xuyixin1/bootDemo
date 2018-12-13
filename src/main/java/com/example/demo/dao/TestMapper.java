package com.example.demo.dao;

import com.example.demo.domain.Info;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

public interface TestMapper {
   @Cacheable(cacheNames = "Activity",key="1")
   Info find();

   void insert();
}
