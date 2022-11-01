package com.hy.validation;

import com.hy.validation.entity.Student;
import com.hy.validation.group.AddGroup;
import com.hy.validation.group.UpdateGroup;
import com.hy.validation.util.FormValidUtils;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Map;

@SpringBootTest
public class Test {

    @org.junit.jupiter.api.Test
    public void test(){
        Student zhangsan = new Student(null, "zhangsan", new Date());
        Map<String, ?> valid = FormValidUtils.valid(zhangsan, UpdateGroup.class);
        System.out.println(valid);
        Student lisi = new Student(null, "lisi", null);
        Map<String, ?> valid1 = FormValidUtils.valid(lisi, AddGroup.class);
        System.out.println(valid1);
    }

}
