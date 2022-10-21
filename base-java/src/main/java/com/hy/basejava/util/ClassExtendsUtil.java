package com.hy.basejava.util;

import java.util.Arrays;

public class ClassExtendsUtil {

    public static <T> void getParentsClass(Class<T> clazz){
        Class<? super T> superclass = clazz.getSuperclass();
        if (superclass!=null){
            System.out.println(superclass);
            getParentsClass(superclass);
        }

    }

}
