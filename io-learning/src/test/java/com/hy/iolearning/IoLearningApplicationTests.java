package com.hy.iolearning;

import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@SpringBootTest
class IoLearningApplicationTests {

    @Test
    void contextLoads() {
        System.out.println(this.getClass().getClassLoader());

    }

    public static void main(String[] args) {
        try {
            method1("1");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Deprecated
    public static String method1(String str) throws ClassNotFoundException, NoSuchMethodException {
        method1();
        return null;
    }

    @Bean
    public static void method1() throws ClassNotFoundException, NoSuchMethodException {


        RuntimeException runtimeException = new RuntimeException();
        StackTraceElement[] stackTrace = runtimeException.getStackTrace();
        for (StackTraceElement stackTraceElement : stackTrace) {

            System.out.println(stackTraceElement.getClassLoaderName()+":"+stackTraceElement.getClassName() +"#"+ stackTraceElement.getMethodName());
            System.out.println(stackTraceElement.getFileName());
            Class<?> aClass = Class.forName(stackTraceElement.getClassName());
           /* Method method = aClass.getDeclaredMethod(stackTraceElement.getMethodName());

            Annotation[] declaredAnnotations = method.getDeclaredAnnotations();
            for (Annotation declaredAnnotation : declaredAnnotations) {
                Class<? extends Annotation> aClass1 = declaredAnnotation.annotationType();
                System.out.println(aClass1.getName());
            }*/
        }
        throw runtimeException;
    }

}
