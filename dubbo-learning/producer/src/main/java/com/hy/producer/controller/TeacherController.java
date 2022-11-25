package com.hy.producer.controller;

import com.hy.commonapi.service.IStudentService;
import com.hy.commonapi.service.ITeacherService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("teacher")
@RestController
public class TeacherController {

    @DubboReference
    ITeacherService iTeacherService;

    @GetMapping("/{id}")
    public Object getStudent(@PathVariable("id")Long id){
        return iTeacherService.getTeacherByPk(id);
    }

}
