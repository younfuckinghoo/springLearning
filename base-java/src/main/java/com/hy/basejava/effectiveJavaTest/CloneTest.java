package com.hy.basejava.effectiveJavaTest;

import com.hy.basejava.entity.Student;

public class CloneTest {

    public static void main(String[] args) {
        Student student = new Student();
        student.setName("zhangsan");
        student.setAge(12);
        student.setGender(true);
        student.setAddress("qingdao");
        Student clone = student.clone();
        System.out.println(clone);
        System.out.println(clone == student);
    }

}
