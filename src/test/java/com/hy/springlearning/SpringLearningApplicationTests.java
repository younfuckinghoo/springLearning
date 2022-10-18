package com.hy.springlearning;

import com.hy.springlearning.controller.IOController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringLearningApplicationTests {

    @Autowired
    IOController ioController;

    @Test
    void contextLoads() {
        System.out.println(ioController);
    }

}
