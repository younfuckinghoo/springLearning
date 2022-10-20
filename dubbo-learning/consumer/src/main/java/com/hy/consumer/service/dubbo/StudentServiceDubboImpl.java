package com.hy.consumer.service.dubbo;

import com.hy.commonapi.entity.Student;
import com.hy.commonapi.service.IStudentService;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class StudentServiceDubboImpl implements IStudentService {
    @Override
    public Student getStudentByPk(Long id) {
        return new Student(1L,"zhangsan",18,true);
    }
}
