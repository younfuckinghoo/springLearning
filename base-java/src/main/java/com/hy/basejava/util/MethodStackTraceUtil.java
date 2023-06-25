package com.hy.basejava.util;

public class MethodStackTraceUtil {

    public static StackTraceElement[] getMethodStackTrace(){
        RuntimeException runtimeException = new RuntimeException();
        return runtimeException.getStackTrace();
    }

    public static void main(String[] args) {
        StackTraceElement[] methodStackTrace = getMethodStackTrace();
        for (StackTraceElement traceElement : methodStackTrace) {

//            System.out.println(traceElement.getClassLoaderName() + ":" + traceElement.getClassName() + "#" + traceElement.getMethodName());
        }

    }

}
