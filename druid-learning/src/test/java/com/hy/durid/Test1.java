package com.hy.durid;

import com.hy.druid.DruidLearningApplication;
import com.hy.druid.test.JdbcUsingDruid;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest(classes = {DruidLearningApplication.class})
public class Test1 {

    @Resource
    private JdbcUsingDruid jdbcUsingDruid;

    @Test
    public void test1(){
        jdbcUsingDruid.testDataSourceConnection();
    }

}
