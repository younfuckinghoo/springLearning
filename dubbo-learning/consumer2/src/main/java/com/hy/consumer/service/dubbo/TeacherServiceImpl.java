package com.hy.consumer.service.dubbo;

import com.hy.commonapi.entity.Student;
import com.hy.commonapi.entity.Teacher;
import com.hy.commonapi.service.ITeacherService;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class TeacherServiceImpl implements ITeacherService {
    @Override
    public Teacher getTeacherByPk(Long id) {
        return new Teacher(1l,"zhangsan",29,false,"青岛");
    }
}
