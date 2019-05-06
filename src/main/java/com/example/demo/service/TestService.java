package com.example.demo.service;

import com.example.demo.dao.TestMapper;
import com.example.demo.domain.Info;
import com.github.rholder.retry.*;
import com.google.common.base.Predicates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class TestService {
    private static final Logger log = LoggerFactory.getLogger(TestService.class);
    @Autowired
    private TestMapper testMapper;

    private RestTemplate restTemplate;

    public Info find() {
        return testMapper.find();
    }

    @Transactional(rollbackFor = Exception.class)
    public void insert() throws Exception {
        testMapper.insert();
        throw new Exception("sqlException");
    }

    public void retry() {
        Retryer<Boolean> retry = RetryerBuilder.<Boolean>newBuilder()
                //抛出runtime异常、checked异常时都会重试，但是抛出error不会重试。
                .retryIfException()
                //返回false也需要重试
                .retryIfResult(Predicates.equalTo(Boolean.TRUE))
                //重调策略
                .withWaitStrategy(WaitStrategies.fixedWait(1, TimeUnit.SECONDS))
                //尝试次数
                .withStopStrategy(StopStrategies.stopAfterAttempt(3))
                .build();

        Callable<Boolean> task = new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                log.info("重试");
                return Boolean.TRUE;
            }
        };
        try {
            retry.call(task);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (RetryException e) {
            log.error(e.getMessage(),e);
        }
    }

}
