package com.hy.demo.entity;

import com.hy.basejava.lombok.Data;

@Data
public class Student {
    private String name;
    private Integer age;

    public static void main(String[] args) {
        for (String arg : args) {
            System.out.println("参数----------》"+arg);
        }
        //打包后执行以下命令
//        D:\projects\hy\SpringLearning\demo\target>java -jar demo.jar 123 456 789
//        参数----------》123
//        参数----------》456
//        参数----------》789
    }

}
