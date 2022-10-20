package com.hy.producer.controller;

import com.hy.commonapi.service.IStudentService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("student")
@RestController
public class StudentController {

    @DubboReference
    IStudentService iStudentService;

    @GetMapping("/{id}")
    public Object getStudent(@PathVariable("id")Long id){
        return iStudentService.getStudentByPk(id);
    }

}
