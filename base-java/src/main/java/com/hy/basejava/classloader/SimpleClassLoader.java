package com.hy.basejava.classloader;

import com.hy.basejava.util.ClassExtendsUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

public class SimpleClassLoader extends ClassLoader {

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String resourcePath = "C:\\Users\\Administrstor\\Downloads\\luban\\crm\\modules\\crm-common\\crm-common-core\\target\\classes\\"+name.replace(".","\\")+".class";
        File file = new File(resourcePath);
        try (FileInputStream fis = new FileInputStream(file); ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int b = 0;
            while ((b = fis.read(buffer)) != -1) {
                bos.write(buffer,0, b);
            }
            byte[] bytes = bos.toByteArray();
            return defineClass(name, bytes, 0, bytes.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.findClass(name);

    }

    public static void main(String[] args) {
        SimpleClassLoader simpleClassLoader = new SimpleClassLoader();
        // 要加载的类要加上完整包路径
        try {
//            Class<?> aClass = simpleClassLoader.findClass("com.hy.basejava.classloader.SimpleClassLoader");
            Class<?> aClass = simpleClassLoader.findClass("cn.evun.crm.common.core.config.MybatisPlusConfig");
            System.out.println(aClass);
            System.out.println(aClass.getClassLoader());
            System.out.println(SimpleClassLoader.class.getClassLoader());
            System.out.println("---------------------------------------------");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        ClassLoader parent = simpleClassLoader.getParent();
        ClassExtendsUtil.getParentsClass(parent.getClass());
        System.out.println("-------------");
        ClassExtendsUtil.getParentsClass(SimpleClassLoader.class);
        System.out.println("-------------");
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        ClassExtendsUtil.getParentsClass(systemClassLoader.getClass());


    }


}
